package act.mybatis.helper;

import act.mybatis.code.IdentityDialect;
import act.mybatis.core.MapperException;
import act.mybatis.entity.Config;
import act.mybatis.entity.EntityColumn;
import act.mybatis.entity.EntityTable;
import act.mybatis.util.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

/**
 * Created by will on 2018/9/21.
 */
public class EntityHelper {
    /**
     * 实体类 => 表对象
     */
    private static final Map<Class<?>, EntityTable> ENTITY_TABLE_MAP = Maps.newHashMap();

    public static EntityTable getEntityTable(Class<?> entityClass) {
        EntityTable entityTable = ENTITY_TABLE_MAP.get(entityClass);
        if (entityTable == null) {
            throw new MapperException("无法获取实体类" + entityClass.getCanonicalName() + "对应的表名!");
        }
        return entityTable;
    }

    /**
     * 初始化实体类
     * @param entityClass
     * @param config
     */
    public static synchronized void initEntity(Class<?> entityClass, Config config) {
        if (ENTITY_TABLE_MAP.get(entityClass) != null) {
            return;
        }

        EntityTable entityTable = new EntityTable(entityClass);
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            if (StringUtil.isNotEmpty(table.name())) {
                entityTable.setTable(table);
            }
        }
        if (StringUtil.isEmpty(entityTable.getName())) {
            entityTable.setName(StringUtil.convertByStyle(entityClass.getSimpleName(), config.getTableStyle()));
        }

        for (Field field: getFields(entityClass)) {
            processField(entityTable, config, field);
        }
        ENTITY_TABLE_MAP.put(entityClass, entityTable);
    }

    /**
     * 把Field 转化为 EntityColumn
     * @param entityTable
     * @param config
     * @param field
     */
    private static void processField(EntityTable entityTable, Config config, Field field) {
        // Transient
        if (field.isAnnotationPresent(Transient.class)) {
            return;
        }
        // Id
        EntityColumn entityColumn = new EntityColumn(entityTable);
        if (field.isAnnotationPresent(Id.class)) {
            entityColumn.setId(true);
        }
        // Column
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            entityColumn.setColumn(column.name());
            entityColumn.setInsertable(column.insertable());
            entityColumn.setUpdatable(column.updatable());
        } else {
            entityColumn.setColumn(StringUtil.convertByStyle(field.getName(), config.getColumnStyle()));
        }
        entityColumn.setProperty(field.getName());
        entityColumn.setJavaType(field.getType());
        // 主键策略
        if (field.isAnnotationPresent(SequenceGenerator.class)) {
            SequenceGenerator sequenceGenerator = field.getAnnotation(SequenceGenerator.class);
            if (sequenceGenerator.sequenceName().equals("")) {
                throw new MapperException(entityTable.getEntityClass() + "字段" + field.getName() + "的注解@SequenceGenerator未指定sequenceName!");
            }
            entityColumn.setSequenceName(sequenceGenerator.sequenceName());
        } else if (field.isAnnotationPresent(GeneratedValue.class)) {
            GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            if (generatedValue.generator().equals("UUID")) {
                entityColumn.setUuid(true);
            } else if (generatedValue.generator().equals("JDBC")) {
                entityColumn.setIdentity(true);
                entityColumn.setGenerator("JDBC");
                entityTable.addKeyProperty(entityColumn.getProperty());
                entityTable.addKeyColumn(entityColumn.getColumn());
            } else {
                //允许通过generator来设置获取id的sql,例如mysql=CALL IDENTITY(),hsqldb=SELECT SCOPE_IDENTITY()
                //允许通过拦截器参数设置公共的generator
                if (generatedValue.strategy() == GenerationType.IDENTITY) {
                    //mysql的自动增长
                    entityColumn.setIdentity(true);
                    if (!generatedValue.generator().equals("")) {
                        String generator = null;
                        IdentityDialect identityDialect = IdentityDialect.getDatabaseDialect(generatedValue.generator());
                        if (identityDialect != null) {
                            generator = identityDialect.getIdentityRetrievalStatement();
                        } else {
                            generator = generatedValue.generator();
                        }
                        entityColumn.setGenerator(generator);
                    }
                } else {
                    throw new MapperException(field.getName()
                            + " - 该字段@GeneratedValue配置只允许以下几种形式:" +
                            "\n1.全部数据库通用的@GeneratedValue(generator=\"UUID\")" +
                            "\n2.useGeneratedKeys的@GeneratedValue(generator=\\\"JDBC\\\")  " +
                            "\n3.类似mysql数据库的@GeneratedValue(strategy=GenerationType.IDENTITY[,generator=\"Mysql\"])");
                }
            }
        }
        entityTable.getEntityColumnMap().put(entityColumn.getProperty(), entityColumn);
    }

    private static List<Field> getFields(Class<?> entityClass) {
        List<Field> fieldList = Lists.newArrayList();
        Class<?> tempClass = entityClass;
        while (tempClass != null && tempClass.equals(Object.class)) {
            Field[] fields = tempClass.getDeclaredFields();
            int index = 0;
            for (Field field: fields) {
                // 排除静态字段
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if (tempClass != entityClass) {
                    fieldList.add(index, field);
                    index++;
                } else {
                    fieldList.add(field);
                }
            }
            tempClass = entityClass.getSuperclass();
        }
        return fieldList;
    }
}

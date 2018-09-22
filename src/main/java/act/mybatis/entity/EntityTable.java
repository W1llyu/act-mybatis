package act.mybatis.entity;

import act.mybatis.core.MapperException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author will
 * @date 2018/9/21
 */
@Getter
@Setter
public class EntityTable {
    private String name;
    private Class<?> entityClass;
    protected Map<String, EntityColumn> entityColumnMap;
    private ResultMap resultMap;
    private List<String> keyProperties;
    private List<String> keyColumns;

    public EntityTable(Class<?> entityClass) {
        this.entityClass = entityClass;
        this.entityColumnMap = Maps.newLinkedHashMap();
    }

    public void addKeyProperty(String keyProperty) {
        if (this.keyProperties == null) {
            this.keyProperties = Lists.newArrayList();
        }
        this.keyProperties.add(keyProperty);
    }

    public void addKeyColumn(String keyColumn) {
        if (this.keyColumns == null) {
            this.keyColumns = new ArrayList<String>();
        }
        this.keyColumns.add(keyColumn);
    }

    public void setTable(Table table) {
        this.name = table.name();
    }

    public ResultMap getResultMap(Configuration configuration) {
        if (this.resultMap != null) {
            return this.resultMap;
        }
        if (entityColumnMap == null || entityColumnMap.size() == 0) {
            return null;
        }
        List<ResultMapping> resultMappings = new ArrayList<ResultMapping>();
        for (EntityColumn entityColumn: entityColumnMap.values()) {
            ResultMapping.Builder builder = new ResultMapping.Builder(configuration, entityColumn.getProperty(), entityColumn.getColumn(), entityColumn.getJavaType());
            if (entityColumn.getJdbcType() != null) {
                builder.jdbcType(entityColumn.getJdbcType());
            }
            if (entityColumn.getTypeHandler() != null) {
                try {
                    builder.typeHandler(entityColumn.getTypeHandler().newInstance());
                } catch (Exception e) {
                    throw new MapperException(e);
                }
            }
            List<ResultFlag> flags = new ArrayList<ResultFlag>();
            if (entityColumn.isId()) {
                flags.add(ResultFlag.ID);
            }
            builder.flags(flags);
            resultMappings.add(builder.build());
        }
        ResultMap.Builder builder = new ResultMap.Builder(configuration, "BaseMapperResultMap", this.entityClass, resultMappings, true);
        this.resultMap = builder.build();
        return this.resultMap;
    }
}

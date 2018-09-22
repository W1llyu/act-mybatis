package act.mybatis.entity;

import act.mybatis.helper.EntityHelper;
import lombok.Getter;

/**
 *
 * @author will
 * @date 2018/9/14
 */
@Getter
public class AliasEntityTable {
    private String alias;
    private EntityTable entityTable;
    private Class<?> entityClass;

    public AliasEntityTable(Class<?> entityClass) {
        this(EntityHelper.getEntityTable(entityClass));
    }

    public AliasEntityTable(Class<?> entityClass, String alias) {
        this(EntityHelper.getEntityTable(entityClass), alias);
    }

    public AliasEntityTable(EntityTable entityTable) {
        this(entityTable, entityTable.getName());
    }

    public AliasEntityTable(EntityTable entityTable, String alias) {
        this.entityClass = entityTable.getEntityClass();
        this.entityTable = entityTable;
        this.alias = alias;
    }

    public String getFullTableName() {
        return String.format("`%s` AS `%s`", entityTable.getName(), alias);
    }

    public String getFullColumnName(String property) {
        EntityColumn entityColumn = entityTable.getEntityColumnMap().get(property);
        return String.format("`%s`.`%s`", alias, entityColumn.getColumn());
    }
}

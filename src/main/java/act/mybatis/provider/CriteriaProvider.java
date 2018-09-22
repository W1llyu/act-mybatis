package act.mybatis.provider;

import act.mybatis.helper.MapperHelper;
import org.apache.ibatis.mapping.MappedStatement;

/**
 *
 * @author will
 * @date 2018/9/17
 */
public class CriteriaProvider extends MapperTemplate {
    public CriteriaProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String selectByCriteria(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        this.setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append("<if test=\"_parameter != null\">");
        sql.append(selectColumnsClause());
        sql.append(tableClause());
        sql.append(whereClause());
        sql.append(groupByClause());
        sql.append(havingClause());
        sql.append(orderByClause());
        sql.append(limitClause());
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * SELECT columns 语句
     * @return
     */
    private String selectColumnsClause() {
        StringBuilder sql = new StringBuilder();
        sql.append("<choose>");
        sql.append("<when test=\"selectColumns == null\">");
        sql.append("<foreach collection=\"selectColumns\" item=\"selectColumn\" separator=\",\">");
        sql.append("${selectColumn}");
        sql.append("</foreach>");
        sql.append("</when>");
        sql.append("<otherwise>");
        sql.append("<foreach collection=\"aliasEntityTableMap\" index=\"alias\" item=\"aliasEntityTable\" separator=\",\">");
        sql.append("`${alias}`.*");
        sql.append("</foreach>");
        sql.append("</otherwise>");
        sql.append("</choose>");
        return sql.toString();
    }

    /**
     * FROM table INNER JOIN / LEFT OUTER JOIN 语句
     * @return
     */
    private String tableClause() {
        StringBuilder sql = new StringBuilder(" FROM ");
        sql.append("`${entityAliasEntityTable.entityTable.name}` AS `${entityAliasEntityTable.alias}` ");
        sql.append(innerJoinClause());
        sql.append(leftJoinClause());
        return sql.toString();
    }

    /**
     * INNER JOIN table ON 语句
     * @return
     */
    private String innerJoinClause() {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"innerJoinAssociations != null\">");
        sql.append("<foreach collection=\"innerJoinAssociations\" item=\"association\" separator=\" \">");
        sql.append("INNER JOIN ");
        sql.append("<choose>");
        sql.append("<when test=\"association.primaryAliasEntityTable.entityTable.name == entityAliasEntityTable.entityTable.name\">");
        sql.append("`${association.foreignAliasEntityTable.entityTable.name}` AS `${association.foreignAliasEntityTable.alias}` ");
        sql.append("</when>");
        sql.append("<otherwise>");
        sql.append("`${association.primaryAliasEntityTable.entityTable.name}` AS `${association.primaryAliasEntityTable.alias}` ");
        sql.append("</otherwise>");
        sql.append("</choose>");
        sql.append("ON `${association.primaryAliasEntityTable.alias}`.`${association.primaryColumn}` = `${association.foreignAliasEntityTable.alias}`.`${association.foreignColumn}` ");
        sql.append("<if test=\"association.onCondition.andQueries.queries.size > 0 or association.onCondition.orQueries.size > 0\">");
        sql.append("AND ");
        sql.append("<trim prefix=\"(\" suffix=\")\">");
        sql.append(conditionClause("association.onCondition"));
        sql.append("</trim>");
        sql.append("</if>");
        sql.append("</foreach>");
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * LEFT OUTER JOIN table ON 语句
     * @return
     */
    private String leftJoinClause() {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"leftJoinAssociations != null\">");
        sql.append("<foreach collection=\"innerJoinAssociations\" item=\"association\" separator=\" \">");
        sql.append("LEFT OUTER JOIN ");
        sql.append("<choose>");
        sql.append("<when test=\"association.primaryAliasEntityTable.entityTable.name == entityAliasEntityTable.entityTable.name\">");
        sql.append("`${association.foreignAliasEntityTable.entityTable.name}` AS `${association.foreignAliasEntityTable.alias}` ");
        sql.append("</when>");
        sql.append("<otherwise>");
        sql.append("`${association.primaryAliasEntityTable.entityTable.name}` AS `${association.primaryAliasEntityTable.alias}` ");
        sql.append("</otherwise>");
        sql.append("</choose>");
        sql.append("ON `${association.primaryAliasEntityTable.alias}`.`${association.primaryColumn}` = `${association.foreignAliasEntityTable.alias}`.`${association.foreignColumn}` ");
        sql.append(conditionClause("association.onCondition"));
        sql.append("</foreach>");
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * WHERE 语句
     * @return
     */
    private String whereClause() {
        StringBuilder sql = new StringBuilder("");
        sql.append("<if test=\"whereCondition != null\">");
        sql.append("<where>");
        sql.append("<trim prefixOverrides=\"AND |OR\">");
        sql.append(conditionClause("whereCondition"));
        sql.append("</trim>");
        sql.append("</where>");
        sql.append("</if>");
        return sql.toString();
    }

    private String conditionClause(String conditionProperty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"" + conditionProperty + ".andQueries.queries.size > 0\">");
        sql.append(queriesClause(conditionProperty + ".andQueries"));
        sql.append("</if>");
        sql.append("<foreach collection=\"" + conditionProperty + ".orQueries\" item=\"orQuery\">");
        sql.append(" OR ");
        sql.append(queriesClause("orQuery"));
        sql.append("</foreach>");
        return sql.toString();
    }

    private String queriesClause(String queriesProperty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<trim prefix=\"(\" prefixOverrides=\"AND\" suffix=\")\">");
        sql.append("<foreach collection=\""+ queriesProperty + ".queries\" item=\"query\">");
        sql.append("<choose>");
        sql.append("<when test=\"query.secondValue == null and query.isParamVal\">");
        sql.append("AND ${query.condition} #{query.value} ");
        sql.append("</when>");
        sql.append("<when test=\"query.secondValue == null and !query.isParamVal\">");
        sql.append("AND ${query.condition} ${query.value} ");
        sql.append("</when>");
        sql.append("<when test=\"query.secondValue != null and query.isParamVal\">");
        sql.append("AND ${query.condition} #{query.value} #{query.secondValue} ");
        sql.append("</when>");
        sql.append("<when test=\"query.secondValue != null and !query.isParamVal\">");
        sql.append("AND ${query.condition} ${query.value} ${query.secondValue} ");
        sql.append("</when>");
        sql.append("</choose>");
        sql.append("</foreach>");
        sql.append("</trim>");
        return sql.toString();
    }

    /**
     * HAVING 语句
     * @return
     */
    private String havingClause() {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"whereCondition != null\">");
        sql.append("HAVING ");
        sql.append("<trim prefixOverrides=\"AND |OR\">");
        sql.append(conditionClause("havingCondition"));
        sql.append("</trim>");
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * GROUP BY 语句
     * @return
     */
    private String groupByClause() {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"groupByConditions != null and groupByConditions.size > 0\">");
        sql.append("GROUP BY ");
        sql.append("<foreach collection=\"groupByConditions\" item=\"groupByCondition\" separator=\",\">");
        sql.append("${groupByCondition}");
        sql.append("</foreach>");
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * ORDER BY 语句
     * @return
     */
    private String orderByClause() {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"orderByConditions != null and orderByConditions.size > 0\">");
        sql.append("ORDER BY ");
        sql.append("<foreach collection=\"orderByConditions\" item=\"orderByCondition\" separator=\",\">");
        sql.append("${orderByCondition.column} ${orderByCondition.sort}");
        sql.append("</foreach>");
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * LIMIT OFFSET 语句
     * @return
     */
    private String limitClause() {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"limit != null\">");
        sql.append("LIMIT #{limit}");
        sql.append("</if>");
        sql.append("<if test=\"offset != null\">");
        sql.append("OFFSET #{offset}");
        sql.append("</if>");
        return sql.toString();
    }
}

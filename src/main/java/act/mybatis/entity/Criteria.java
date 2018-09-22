package act.mybatis.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author will
 * @date 2018/9/11
 */
@Getter
public class Criteria {
    private AliasEntityTable entityAliasEntityTable;
    private Map<String, AliasEntityTable> aliasEntityTableMap;
    private Set<Association> innerJoinAssociations;
    private Set<Association> leftJoinAssociations;
    private Condition whereCondition;
    private Condition havingCondition;
    private List<String> groupByConditions;
    private List<OrderBy> orderByConditions;
    private List<String> selectColumns;
    private boolean isDefaultColumn;
    private Long limit;
    private Long offset;

    public Criteria(Class<?> entityClass) {
        isDefaultColumn = true;
        selectColumns = Lists.newArrayList();
        this.aliasEntityTableMap = Maps.newLinkedHashMap();
        this.entityAliasEntityTable = new AliasEntityTable(entityClass);
        addAliasEntityTable(entityAliasEntityTable);
    }

    /**
     * INNER JOIN
     * @param association
     * @return
     */
    public Criteria innerJoin(Association association) {
        if (innerJoinAssociations == null) {
            innerJoinAssociations = Sets.newHashSet();
        }
        addAliasEntityTable(association);
        innerJoinAssociations.add(association);
        return this;
    }

    /**
     * LEFT JOIN
     * @param association
     * @return
     */
    public Criteria leftJoin(Association association) {
        if (leftJoinAssociations == null) {
            leftJoinAssociations = Sets.newHashSet();
        }
        addAliasEntityTable(association);
        leftJoinAssociations.add(association);
        return this;
    }

    /**
     * WHERE AND 多条件
     * @param queries
     * @return
     */
    public Criteria where(Queries queries) {
        if (whereCondition == null) {
            whereCondition = new Condition();
        }
        whereCondition.addAndQueries(queries);
        return this;
    }

    /**
     * WHERE AND 单条件
     * @param query
     * @return
     */
    public Criteria where(Query query) {
        if (whereCondition == null) {
            whereCondition = new Condition();
        }
        whereCondition.addAndQuery(query);
        return this;
    }

    /**
     * WHERE OR 多条件
     * @param queries
     * @return
     */
    public Criteria orWhere(Queries queries) {
        if (whereCondition == null) {
            whereCondition = new Condition();
        }
        whereCondition.addOrQueries(queries);
        return this;
    }

    /**
     * WHERE OR 单条件
     * @param query
     * @return
     */
    public Criteria orWhere(Query query) {
        if (whereCondition == null) {
            whereCondition = new Condition();
        }
        whereCondition.addOrQuery(query);
        return this;
    }

    /**
     * HAVING 多条件
     * @param queries
     * @return
     */
    public Criteria having(Queries queries) {
        if (havingCondition == null) {
            havingCondition = new Condition();
        }
        havingCondition.addAndQueries(queries);
        return this;
    }

    /**
     * HAVING 单条件
     * @param query
     * @return
     */
    public Criteria having(Query query) {
        if (havingCondition == null) {
            havingCondition = new Condition();
        }
        havingCondition.addAndQuery(query);
        return this;
    }

    /**
     * GROUP BY
     * @param column
     * @return
     */
    public Criteria group(String column) {
        if (groupByConditions == null) {
            groupByConditions = Lists.newArrayList();
        }
        groupByConditions.add(column);
        return this;
    }

    /**
     * ORDER BY
     * @param orderByCause
     * @return
     */
    public Criteria order(OrderBy orderByCause) {
        if (orderByConditions == null) {
            orderByConditions = Lists.newArrayList();
        }
        orderByConditions.add(orderByCause);
        return this;
    }

    /**
     * LIMIT
     * @param limit
     * @return
     */
    public Criteria limit(long limit) {
        this.limit = limit;
        return this;
    }

    /**
     * OFFSET
     * @param offset
     * @return
     */
    public Criteria offset(long offset) {
        this.offset = offset;
        return this;
    }

    /**
     * SELECT COLUMNS
     * @param column
     * @return
     */
    public Criteria select(String column) {
        if (isDefaultColumn) {
            selectColumns = Lists.newArrayList();
            isDefaultColumn = false;
        }
        selectColumns.add(column);
        return this;
    }

    private void addAliasEntityTable(Association association) {
        addAliasEntityTable(association.getPrimaryAliasEntityTable());
        addAliasEntityTable(association.getForeignAliasEntityTable());
    }

    private void addAliasEntityTable(AliasEntityTable aliasEntityTable) {
        if (!aliasEntityTableMap.containsKey(aliasEntityTable.getAlias())) {
            aliasEntityTableMap.put(aliasEntityTable.getAlias(), aliasEntityTable);
            if (isDefaultColumn) {
                selectColumns.add(String.format("`%s`.*", aliasEntityTable.getAlias()));
            }
        }
    }
}
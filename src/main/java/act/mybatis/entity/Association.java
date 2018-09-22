package act.mybatis.entity;

import act.mybatis.core.MapperException;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

/**
 *
 * @author will
 * @date 2018/9/13
 */
@Getter
public class Association implements Cloneable {
    @NonNull
    private AliasEntityTable primaryAliasEntityTable;
    @NonNull
    private AliasEntityTable foreignAliasEntityTable;

    private String primaryColumn;
    private String foreignColumn;

    private Condition onCondition;

    private Association throughAssociation;

    private Association(Builder builder) {
        this.primaryAliasEntityTable = builder.primaryAliasEntityTable;
        this.foreignAliasEntityTable = builder.foreignAliasEntityTable;
        setPrimaryProperty(builder.primaryProperty);
        setForeignProperty(builder.foreignProperty);
        this.onCondition = builder.onCondition == null ? new Condition() : builder.onCondition;
        this.throughAssociation = builder.throughAssociation;
    }

    private void setPrimaryProperty(String primaryProperty) {
        if (!primaryAliasEntityTable.getEntityTable().getEntityColumnMap().containsKey(primaryProperty)) {
            throw new MapperException("实体类" + primaryAliasEntityTable.getEntityTable().getEntityClass().getCanonicalName() + "不存在属性" + primaryProperty);
        }
        this.primaryColumn = primaryAliasEntityTable.getEntityTable().getEntityColumnMap().get(primaryProperty).getColumn();
    }

    private void setForeignProperty(String foreignProperty) {
        if (!foreignAliasEntityTable.getEntityTable().getEntityColumnMap().containsKey(foreignProperty)) {
            throw new MapperException("实体类" + foreignAliasEntityTable.getEntityTable().getEntityClass().getCanonicalName() + "不存在属性" + foreignProperty);
        }
        this.foreignColumn = foreignAliasEntityTable.getEntityTable().getEntityColumnMap().get(foreignProperty).getColumn();
    }

    public Association on(Condition condition) {
        onCondition = condition;
        return this;
    }

    /**
     * ON AND 多条件
     * @param queries
     * @return
     */
    public Association on(Queries queries) {
        onCondition.addAndQueries(queries);
        return this;
    }

    /**
     * ON AND 单条件
     * @param query
     * @return
     */
    public Association on(Query query) {
        onCondition.addAndQuery(query);
        return this;
    }

    /**
     * ON OR 多条件
     * @param queries
     * @return
     */
    public Association orOn(Queries queries) {
        onCondition.addOrQueries(queries);
        return this;
    }

    /**
     * ON OR 单条件
     * @param query
     * @return
     */
    public Association orOn(Query query) {
        onCondition.addOrQuery(query);
        return this;
    }

    public List<Association> relatedAssociations() {
        List<Association> associations = Lists.newArrayList();
        Association temp = throughAssociation;
        while (temp != null) {
            associations.add(temp);
            temp = temp.getThroughAssociation();
        }
        return associations;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private AliasEntityTable primaryAliasEntityTable;
        private AliasEntityTable foreignAliasEntityTable;
        private String primaryProperty = "id";
        private String foreignProperty = "id";
        private Condition onCondition;
        private Association throughAssociation;

        public Builder() {}

        public Builder primaryAliasEntityTable(AliasEntityTable primaryAliasEntityTable) {
            this.primaryAliasEntityTable = primaryAliasEntityTable;
            return this;
        }

        public Builder foreignAliasEntityTable(AliasEntityTable foreignAliasEntityTable) {
            this.foreignAliasEntityTable = foreignAliasEntityTable;
            return this;
        }

        public Builder primaryProperty(String primaryProperty) {
            this.primaryProperty = primaryProperty;
            return this;
        }

        public Builder foreignProperty(String foreignProperty) {
            this.foreignProperty = foreignProperty;
            return this;
        }

        public Builder onCondition(Condition onCondition) {
            this.onCondition = onCondition;
            return this;
        }

        public Builder through(Association association) {
            this.throughAssociation = association;
            return this;
        }

        public Association build() {
            return new Association(this);
        }
    }
}

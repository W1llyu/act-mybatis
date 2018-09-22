package act.mybatis.entity;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

/**
 *
 * @author will
 * @date 2018/9/14
 */
@Getter
public class Condition {
    private Queries andQueries;
    private List<Queries> orQueries;

    public Condition() {
        this.andQueries = new Queries();
        this.orQueries = Lists.newArrayList();
    }

    public Condition addAndQueries(Queries queries) {
        andQueries.getQueries().addAll(queries.getQueries());
        return this;
    }

    public Condition addAndQuery(Query query) {
        andQueries.getQueries().add(query);
        return this;
    }

    public Condition addOrQuery(final Query query) {
        this.orQueries.add(new Queries() {{addQuery(query);}});
        return this;
    }

    public Condition addOrQueries(Queries queries) {
        this.orQueries.add(queries);
        return this;
    }
}

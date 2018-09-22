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
public class Queries {
    private List<Query> queries;

    public Queries() {
        this.queries = Lists.newArrayList();
    }

    public Queries addQuery(Query query) {
        this.queries.add(query);
        return this;
    }
}

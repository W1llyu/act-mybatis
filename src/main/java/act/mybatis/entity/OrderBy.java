package act.mybatis.entity;

import lombok.Getter;

/**
 *
 * @author will
 * @date 2018/9/14
 */
@Getter
public class OrderBy {
    private String column;
    private String sort;

    public OrderBy(String column, Sort sort) {
        this.column = column;
        this.sort = sort.getValue();
    }
}

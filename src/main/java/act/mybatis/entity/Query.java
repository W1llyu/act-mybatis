package act.mybatis.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 *
 * @author will
 * @date 2018/9/14
 */
@Getter
@Builder
public class Query {
    @NonNull
    private boolean isParamVal;
    @NonNull
    private String condition;
    @NonNull
    private Object value;
    private Object secondValue;

    private Query(boolean isParamVal, String condition, Object value, Object secondValue) {
        this.isParamVal = isParamVal;
        this.condition = condition;
        this.value = value == null ? "NULL" : value;
        this.secondValue = secondValue;
    }

    public static Query equalValue(String column, Object value) {
        return builder().condition(column + " =").value(value).isParamVal(true).build();
    }

    public static Query equalSql(String column, String value) {
        return builder().condition(column + " =").value(value).isParamVal(false).build();
    }

    public static Query optValue(String column, String opt, Object value) {
        return builder().condition(column + " " + opt).value(value).isParamVal(true).build();
    }

    public static Query optSql(String column, String opt, String value) {
        return builder().condition(column + " " + opt).value(value).isParamVal(false).build();
    }
}

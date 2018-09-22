package act.mybatis.entity;

/**
 *
 * @author will
 * @date 2018/9/14
 */
public enum Sort implements IEnum<String> {
    /**
     * 降序
     */
    DESC("DESC"),
    /**
     * 升序
     */
    ASC("ASC");

    private final String value;

    Sort(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}

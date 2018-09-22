package act.mybatis.provider;

import act.mybatis.helper.MapperHelper;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.MappedStatement;

/**
 *
 * @author will
 * @date 2018/9/21
 */
public class BasicProvider extends MapperTemplate {
    public BasicProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String selectAll(MappedStatement ms) {
        return new SQL() {
            {
                SELECT("*");
                FROM("users");
            }
        }.toString();
    }
}

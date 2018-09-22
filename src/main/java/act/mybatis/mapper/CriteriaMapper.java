package act.mybatis.mapper;

import act.mybatis.provider.CriteriaProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 *
 * @author will
 * @date 2018/9/17
 */
public interface CriteriaMapper<T> {
    @SelectProvider(
            type = CriteriaProvider.class,
            method = "dynamicSQL"
    )
    List<T> selectByCriteria(Object var);
}

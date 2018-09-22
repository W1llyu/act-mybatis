package act.mybatis.mapper;

import act.mybatis.provider.BasicProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by will on 2018/9/21.
 */
public interface BasicMapper<T> {
    @SelectProvider(
            type = BasicProvider.class,
            method = "dynamicSQL"
    )
    List<T> selectAll();
}

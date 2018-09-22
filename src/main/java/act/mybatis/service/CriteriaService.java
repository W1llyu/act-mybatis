package act.mybatis.service;

import act.mybatis.entity.Criteria;

import java.util.List;

/**
 *
 * @author will
 * @date 2018/9/20
 */
public interface CriteriaService<T> {
    List<T> findByCriteria(Criteria criteria);

    T findOneByCriteria(Criteria criteria);
}

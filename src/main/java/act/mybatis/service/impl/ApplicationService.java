package act.mybatis.service.impl;

import act.mybatis.entity.Association;
import act.mybatis.entity.Criteria;
import act.mybatis.service.Service;

import java.util.List;

/**
 *
 * @author will
 * @date 2018/9/20
 */
public class ApplicationService<T> extends AbstractTimestampService<T> implements Service<T> {
    @Override
    public List<T> findByCriteria(Criteria criteria) {
        return mapper.selectByCriteria(criteria);
    }

    @Override
    public T findOneByCriteria(Criteria criteria) {
        return mapper.selectByCriteria(criteria).get(0);
    }

    @Override
    public List<T> findByAssociation(Association association) {
        return mapper.selectByCriteria(new Criteria(entityClass).innerJoin(association));
    }

    @Override
    public T findOneByAssociation(Association association) {
        return mapper.selectByCriteria(new Criteria(entityClass).innerJoin(association)).get(0);
    }

    protected static void addAssociation(String primaryProperty, String foreignProperty, Class<?> foreignClass) {

    }
}

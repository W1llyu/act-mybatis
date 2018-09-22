package act.mybatis.service;

import act.mybatis.entity.Association;

import java.util.List;

/**
 * Created by will on 2018/9/20.
 */
public interface AssociationService<T> {
    List<T> findByAssociation(Association association);

    T findOneByAssociation(Association association);
}

package act.mybatis.app.service.impl;

import act.mybatis.entity.AliasEntityTable;
import act.mybatis.entity.Association;
import act.mybatis.service.impl.ApplicationService;
import act.mybatis.app.entity.Order;
import act.mybatis.app.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 2018/9/20.
 */
@Service
@Transactional
public class OrderServiceImpl extends ApplicationService<Order> implements OrderService {
}

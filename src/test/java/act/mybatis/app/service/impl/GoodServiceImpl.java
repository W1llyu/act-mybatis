package act.mybatis.app.service.impl;

import act.mybatis.entity.AliasEntityTable;
import act.mybatis.service.impl.ApplicationService;
import act.mybatis.app.entity.Good;
import act.mybatis.app.service.GoodService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 2018/9/20.
 */
@Service
@Transactional
public class GoodServiceImpl extends ApplicationService<Good> implements GoodService {
}

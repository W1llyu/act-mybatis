package act.mybatis.app.service.impl;

import act.mybatis.app.entity.Account;
import act.mybatis.service.impl.ApplicationService;
import act.mybatis.app.entity.User;
import act.mybatis.app.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 2018/9/20.
 */
@Service
@Transactional
public class UserServiceImpl extends ApplicationService<User> implements UserService {
    {
        addAssociation("id", "userId", Account.class);
    }
}

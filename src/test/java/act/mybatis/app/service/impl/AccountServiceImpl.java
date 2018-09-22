package act.mybatis.app.service.impl;

import act.mybatis.service.impl.ApplicationService;
import act.mybatis.app.entity.Account;
import act.mybatis.app.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 2018/9/20.
 */
@Service
@Transactional
public class AccountServiceImpl extends ApplicationService<Account> implements AccountService {
}

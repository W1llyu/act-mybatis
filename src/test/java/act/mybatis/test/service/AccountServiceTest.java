package act.mybatis.test.service;

import act.mybatis.app.service.AccountService;
import act.mybatis.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by will on 2018/9/20.
 */
public class AccountServiceTest extends BaseTest {
    @Resource
    private AccountService accountService;

    @Test
    public void testOrdersAssociation() {
    }
}

package act.mybatis.test.mapper;

import act.mybatis.app.mapper.UserMapper;
import act.mybatis.entity.Criteria;
import act.mybatis.app.entity.User;
import act.mybatis.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by will on 2018/9/20.
 */
public class UserMapperTest extends BaseTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void testSelectAll() {
        List<User> users = userMapper.selectAll();
    }
}

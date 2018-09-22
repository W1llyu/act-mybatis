package act.mybatis.test;

/**
 * Created by will on 13/11/2017.
 */

import act.mybatis.app.TestApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器 spring-test,junit
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= TestApplication.class)
@ConfigurationProperties
public class BaseTest {

}

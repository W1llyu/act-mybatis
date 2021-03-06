package act.mybatis.core;

import act.mybatis.helper.MapperHelper;
import act.mybatis.util.StringUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.util.Properties;

/**
 *
 * @author will
 * @date 2018/9/21
 */
public class MapperScannerConfigurer extends org.mybatis.spring.mapper.MapperScannerConfigurer {
    private MapperHelper mapperHelper = new MapperHelper();

    public MapperScannerConfigurer() {
        Properties properties = new Properties();
        properties.setProperty("mappers", "act.mybatis.mapper.Mapper");
        mapperHelper.setProperties(properties);
    }

    public void setProperties(Properties properties) {
        mapperHelper.setProperties(properties);
    }

    /**
     * 注册完成后，对MapperFactoryBean的类进行特殊处理
     *
     * @param registry
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        super.postProcessBeanDefinitionRegistry(registry);
        //如果没有注册过接口，就注册默认的Mapper接口
        this.mapperHelper.ifEmptyRegisterDefaultInterface();
        String[] names = registry.getBeanDefinitionNames();
        GenericBeanDefinition definition;
        for (String name : names) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(name);
            if (beanDefinition instanceof GenericBeanDefinition) {
                definition = (GenericBeanDefinition) beanDefinition;
                if (StringUtil.isNotEmpty(definition.getBeanClassName())
                        && "org.mybatis.spring.mapper.MapperFactoryBean".equals(definition.getBeanClassName())) {
                    definition.setBeanClass(MapperFactoryBean.class);
                    definition.getPropertyValues().add("mapperHelper", this.mapperHelper);
                    registry.removeBeanDefinition(name);
                    registry.registerBeanDefinition(name, definition);
                }
            }
        }
    }
}

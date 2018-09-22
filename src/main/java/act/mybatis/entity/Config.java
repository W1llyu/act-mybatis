package act.mybatis.entity;

import act.mybatis.code.Style;
import act.mybatis.core.MapperException;
import act.mybatis.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

import static act.mybatis.code.Style.camelhumpAndLowercase;

/**
 *
 * @author will
 * @date 2018/9/21
 */
@Getter
@Setter
public class Config {
    /**
     * 是否自动记录创建时间
     */
    private boolean autoCreatedAt;
    /**
     * 是否自动记录更新时间
     */
    private boolean autoUpdatedAt;

    /**
     * 表名转换风格，默认驼峰转下划线
     */
    private Style tableStyle;

    /**
     * 列名转换风格，默认驼峰转下划线
     */
    private Style columnStyle;

    public Config() {
        this.autoCreatedAt = true;
        this.autoUpdatedAt = true;
        this.tableStyle = camelhumpAndLowercase;
        this.columnStyle = camelhumpAndLowercase;
    }

    /**
     * 配置属性
     * @param properties
     */
    public void setProperties(Properties properties) {
        String autoCreatedAt = properties.getProperty("autoCreatedAt");
        if (StringUtil.isNotEmpty(autoCreatedAt)) {
            this.autoCreatedAt = "true".equalsIgnoreCase(autoCreatedAt);
        }
        String autoUpdatedAt = properties.getProperty("autoUpdatedAt");
        if (StringUtil.isNotEmpty(autoUpdatedAt)) {
            this.autoUpdatedAt = "true".equalsIgnoreCase(autoUpdatedAt);
        }
        String tableStyle = properties.getProperty("tableStyle");
        if (StringUtil.isNotEmpty(tableStyle)) {
            try {
                this.tableStyle = Style.valueOf(tableStyle);
            } catch (IllegalArgumentException e) {
                throw new MapperException(tableStyle + "不是合法的tableStyle值！");
            }
        }
        String columnStyle = properties.getProperty("columnStyle");
        if (StringUtil.isNotEmpty(columnStyle)) {
            try {
                this.columnStyle = Style.valueOf(columnStyle);
            } catch (IllegalArgumentException e) {
                throw new MapperException(columnStyle + "不是合法的columnStyle值！");
            }
        }
    }
}

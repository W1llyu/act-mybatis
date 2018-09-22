package act.mybatis.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 *
 * @author will
 * @date 2018/9/21
 */
@Getter
@Setter
public class EntityColumn {
    private EntityTable entityTable;
    private String property;
    private String column;
    private Class<?> javaType;
    private JdbcType jdbcType;
    private Class<? extends TypeHandler<?>> typeHandler;
    private String sequenceName;
    private boolean id = false;
    private boolean uuid = false;
    private boolean identity = false;
    private String generator;

    private boolean insertable = true;
    private boolean updatable = true;

    public EntityColumn(EntityTable entityTable) {
        this.entityTable = entityTable;
    }
}

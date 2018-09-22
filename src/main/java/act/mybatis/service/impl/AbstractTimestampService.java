package act.mybatis.service.impl;

import act.mybatis.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.Date;

/**
 * Created by will on 2018/9/20.
 */
public abstract class AbstractTimestampService<T> {
    @Autowired
    protected Mapper<T> mapper;

    protected Class<T> entityClass;

    protected boolean autoCreatedAt = false;
    protected boolean autoUpdatedAt = false;

    public AbstractTimestampService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        entityClass = (Class<T>) pt.getActualTypeArguments()[0];
        initResouceAutoTimestamp();
    }

    protected void initResouceAutoTimestamp() {
//        EntityColumn createdAtColumn = EntityHelper.getEntityTable(entityClass).getPropertyMap().get("createdAt");
//        if (createdAtColumn != null && createdAtColumn.getJavaType() == Date.class) {
//            this.autoCreatedAt = true;
//        }
//
//        EntityColumn updateAtColumn = EntityHelper.getEntityTable(entityClass).getPropertyMap().get("updatedAt");
//        if (updateAtColumn != null && updateAtColumn.getJavaType() == Date.class) {
//            this.autoUpdatedAt = true;
//        }
    }
}

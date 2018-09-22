package act.mybatis.app.entity;

import act.mybatis.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by will on 2018/9/20.
 */
@Table(name="users")
@Getter
@Setter
public class User extends BaseEntity {
    @Column(name="name")
    private String name;

    @Column(name="avatar")
    private String avatar;
}

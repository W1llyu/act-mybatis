package act.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 *
 * @author will
 * @date 2018/7/23
 */
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected Long id;

    @Column(name="created_at")
    protected Date createdAt;

    @Column(name="updated_at")
    protected Date updatedAt;
}

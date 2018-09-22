package act.mybatis.app.entity;

import act.mybatis.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by will on 2018/9/20.
 */
@Table(name="goods")
@Getter
@Setter
public class Good extends BaseEntity {
    @Column(name="name")
    private String name;

    @Column(name="price")
    private BigDecimal price;
}

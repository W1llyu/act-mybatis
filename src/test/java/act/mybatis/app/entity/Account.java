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
@Table(name="accounts")
@Getter
@Setter
public class Account extends BaseEntity{
    @Column(name="user_id")
    private Long userId;

    @Column(name="balance")
    private BigDecimal balance;
}

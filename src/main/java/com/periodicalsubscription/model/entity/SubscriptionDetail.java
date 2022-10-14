package com.periodicalsubscription.model.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "subscription_details")
@SQLDelete(sql = "UPDATE subscription_details SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class SubscriptionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "periodical_id")
    private Periodical periodical;
    @Column(name = "subscription_duration_in_years")
    private Integer subscriptionDurationInYears;
    @Column(name = "periodical_current_price")
    private BigDecimal periodicalCurrentPrice;

}

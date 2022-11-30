package com.periodicalsubscription.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Class describing the object SubscriptionDetail
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "subscription_details")
@SQLDelete(sql = "UPDATE subscription_details SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class SubscriptionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id")
    @ToString.Exclude
    private Subscription subscription;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "periodical_id")
    private Periodical periodical;
    @Column(name = "subscription_duration_in_years")
    private Integer subscriptionDurationInYears;
    @Column(name = "subscription_start_date")
    private LocalDate subscriptionStartDate;
    @Column(name = "subscription_end_date")
    private LocalDate subscriptionEndDate;
    @Column(name = "periodical_current_price")
    private BigDecimal periodicalCurrentPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SubscriptionDetail detail = (SubscriptionDetail) o;
        return id != null && Objects.equals(id, detail.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class describing the object Subscription
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "subscriptions")
@SQLDelete(sql = "UPDATE subscriptions SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "total_cost")
    private BigDecimal totalCost;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @ToString.Exclude
    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL)
    private List<SubscriptionDetail> subscriptionDetails = new ArrayList<>();

    public void addSubscriptionDetail(SubscriptionDetail detail) {
        subscriptionDetails.add(detail);
        detail.setSubscription(this);
    }

    public enum Status {
        PENDING,
        AWAITING_PAYMENT,
        PAYED,
        CANCELED,
        COMPLETED
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Subscription that = (Subscription) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

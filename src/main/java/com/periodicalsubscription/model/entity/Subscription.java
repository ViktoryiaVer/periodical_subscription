package com.periodicalsubscription.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "subscriptions")
@SQLDelete(sql = "UPDATE subscriptions SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "total_cost")
    private BigDecimal totalCost;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SubscriptionDetail> subscriptionDetails;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "subscription", cascade = CascadeType.REFRESH)
    private List<Payment> payments;

    public enum Status {
        PENDING,
        AWAITING_PAYMENT,
        PAYED,
        CANCELED,
        COMPLETED
    }
}

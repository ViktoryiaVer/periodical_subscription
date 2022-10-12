package com.periodical_subscription.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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
    @Column(name = "status_id")
    private Status status;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SubscriptionDetail> subscriptionDetails;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "subscription", cascade = CascadeType.REFRESH)
    private List<Payment> payments;

    @Getter
    public enum Status {
        PENDING(1),
        AWAITING_PAYMENT(2),
        PAYED(3),
        CANCELED(4),
        COMPLETED(5);

        private final Integer id;

        Status(Integer id) {
            this.id = id;
        }
    }
}

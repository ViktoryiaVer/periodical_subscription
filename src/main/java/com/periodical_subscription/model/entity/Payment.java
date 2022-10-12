package com.periodical_subscription.model.entity;

import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payments")
@SQLDelete(sql = "UPDATE payments SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    private Subscription subscription;
    @Column(name = "payment_time")
    private LocalDateTime paymentTime;
    @Column(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @Getter
    public enum PaymentMethod {
        CASH(1),
        CHECK(2),
        CREDIT_OR_DEBIT_CARD(3),
        ONLINE_PAYMENT_SERVICE(4);

        private final Integer id;

        PaymentMethod(Integer id) {
            this.id = id;
        }
    }

}

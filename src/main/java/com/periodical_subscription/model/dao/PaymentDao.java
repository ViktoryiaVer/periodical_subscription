package com.periodical_subscription.model.dao;

import com.periodical_subscription.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Long> {
}

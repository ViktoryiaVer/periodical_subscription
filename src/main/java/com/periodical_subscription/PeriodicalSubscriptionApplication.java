package com.periodical_subscription;

import com.periodical_subscription.model.dao.UserDao;
import com.periodical_subscription.service.api.UserService;
import com.periodical_subscription.service.impl.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PeriodicalSubscriptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeriodicalSubscriptionApplication.class, args);
	}

}

package com.quickticket.quickticket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(order = TransactionConfig.TRANSACTION_ORDER)
public class TransactionConfig {
    public static final int TRANSACTION_ORDER = Ordered.LOWEST_PRECEDENCE;
}

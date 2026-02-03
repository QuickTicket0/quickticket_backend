package com.quickticket.quickticket.config;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        String lookupKey = isReadOnly ? "replica" : "master";
        log.info("<Routing> Transaction Read-Only: {} -> Selected DB: {}", isReadOnly, lookupKey);

        return lookupKey;
    }
}


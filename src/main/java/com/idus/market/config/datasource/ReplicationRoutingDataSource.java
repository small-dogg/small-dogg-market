package com.idus.market.config.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

  @Override
  protected Object determineCurrentLookupKey() {
    String dataSourceType =
        TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? "read" : "write";
    log.info("current dataSourceType : {}", dataSourceType);
    return dataSourceType;
  }
}
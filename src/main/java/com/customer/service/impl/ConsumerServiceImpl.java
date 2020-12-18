package com.customer.service.impl;

import com.customer.converters.CustomerDataMaskConverter;
import com.customer.dao.AuditLogRepository;
import com.customer.entity.AuditLog;
import com.customer.model.kafkaModel.CustomerRequestKafka;
import com.customer.service.ConsumerService;
import com.customer.utils.ObjectMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    private  static  final Logger LOGGER = LoggerFactory.getLogger(ConsumerServiceImpl.class);

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private CustomerDataMaskConverter customerDataMaskConverter;


    @Override
    public void publishCustomerData(CustomerRequestKafka customerRequestKafka) {
        AuditLog auditLog = createAuditLog(customerRequestKafka);
        auditLogRepository.save(auditLog);
        LOGGER.info(
                "Request successfully persisted in database, transaction-id:{} and activity-id:{}",
                customerRequestKafka.getTransactionId(),
                customerRequestKafka.getActivityId());
    }

    private AuditLog createAuditLog(CustomerRequestKafka payload) {
        AuditLog auditLog = new AuditLog();
        auditLog.setCustomerNumber(payload.getCustomerNumber());
        auditLog.setPayload(
                ObjectMapperUtil.getJsonFromObj(customerDataMaskConverter.convert(payload)));
        return auditLog;
    }
}

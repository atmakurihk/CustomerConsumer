package com.customer.service.impl;

import com.customer.converters.CustomerDataMaskConverter;
import com.customer.model.kafkaModel.CustomerRequestKafka;
import com.customer.service.ConsumerService;
import com.customer.service.KafkaListner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListnerImpl implements KafkaListner {

  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListnerImpl.class);

  @Autowired private CustomerDataMaskConverter customerDataMaskConverter;

  @Autowired
  private ConsumerService consumerService;

  @Override
  @KafkaListener(topics = "${cloudkarafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
  public void subscribe(CustomerRequestKafka customerRequestKafka) {
    CustomerRequestKafka maskedRequest = customerDataMaskConverter.convert(customerRequestKafka);
    LOGGER.info("consumed data{}", maskedRequest);
    consumerService.publishCustomerData(customerRequestKafka);
  }
}

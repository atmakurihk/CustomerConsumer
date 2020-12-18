package com.customer.service;

import com.customer.model.kafkaModel.CustomerRequestKafka;

public interface ConsumerService {
    public void publishCustomerData(CustomerRequestKafka customerRequestKafka);
}

package com.customer.service;

import com.customer.model.kafkaModel.CustomerRequestKafka;

public interface KafkaListner {

    public void subscribe(CustomerRequestKafka customerRequestKafka);
}

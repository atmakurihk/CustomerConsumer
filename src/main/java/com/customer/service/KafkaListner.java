package com.customer.service;

import com.customer.model.kafkaModel.CustomerRequestKafka;
import org.springframework.stereotype.Component;

public interface KafkaListner {

    public void listen(CustomerRequestKafka customerRequestKafka);
}

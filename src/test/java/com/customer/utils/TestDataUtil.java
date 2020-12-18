package com.customer.utils;

import com.customer.model.kafkaModel.AddressPublisherKafka;
import com.customer.model.kafkaModel.CustomerRequestKafka;

public class TestDataUtil {

    public static CustomerRequestKafka createValidCustomerObject() {
        CustomerRequestKafka customerRequest = new CustomerRequestKafka();
        customerRequest.setCustomerNumber("C000000001");
        customerRequest.setBirthdate("10-12-2020");
        customerRequest.setCountry("India");
        customerRequest.setCountryCode("IN");
        customerRequest.setCustomerStatus(CustomerRequestKafka.CustomerStatusEnum.OPEN);
        customerRequest.setEmail("test@gmail.com");
        customerRequest.setFirstName("FirstnameValid");
        customerRequest.setLastName("LastnameValid");
        customerRequest.setMobileNumber("9696969696");

        AddressPublisherKafka address = new AddressPublisherKafka();
        address.setAddressLine1("addressLine1 address");
        address.setAddressLine2("customer_address_l2");
        address.setStreet("customer_address_street");
        address.setPostalCode("12345");

        customerRequest.setAddress(address);

        return customerRequest;
    }
}

package com.customer.converters;


import com.customer.model.kafkaModel.AddressPublisherKafka;
import com.customer.model.kafkaModel.CustomerRequestKafka;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerDataMaskConverterTest {

  private CustomerDataMaskConverter customerDataMaskConverter;

  @BeforeEach
  public void setup() {
    customerDataMaskConverter = new CustomerDataMaskConverter();
  }

  @Test
  public void maskingLogicTestWithValidCustomer() {
    CustomerRequestKafka maskedCustomer = customerDataMaskConverter.convert(createValidCustomerObject());
    assertNotNull(maskedCustomer);
    assertEquals("C00000**", maskedCustomer.getCustomerNumber());
    assertEquals("*-2020", maskedCustomer.getBirthdate());
    assertEquals("*@gmail.com", maskedCustomer.getEmail());
  }

  private CustomerRequestKafka createValidCustomerObject() {
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
    customerRequest.setAddress(createValidAddressObject());
    return customerRequest;
  }

  private AddressPublisherKafka createValidAddressObject() {

    AddressPublisherKafka address = new AddressPublisherKafka();
    address.setAddressLine1("addressLine1 address");
    address.setAddressLine2("customer_address_l2");
    address.setStreet("customer_address_street");
    address.setPostalCode("12345");

    return address;
  }
}

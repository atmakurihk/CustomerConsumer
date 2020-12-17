package com.customer.utils;

import com.customer.model.kafkaModel.AddressPublisherKafka;
import com.customer.model.kafkaModel.CustomerRequestKafka;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ObjectMapperUtilsTest {

    @Test
    public void testGetJsonFromObj() {
        String strObj = ObjectMapperUtil.getJsonFromObj(getCustomerData());
    assertEquals(
        "{\"customerNumber\":\"C000000004\",\"firstName\":\"hemanth112244\",\"lastName\":\"Hemanth12234\",\"birthdate\":\"26-12-2010\",\"country\":\"IN\",\"countryCode\":null,\"mobileNumber\":\"8179822721\",\"email\":\"string@email.com\",\"transactionId\":null,\"activityId\":null,\"customerStatus\":\"Restored\",\"address\":{\"addressLine1\":\"Ayyapa society\",\"addressLine2\":\"madhapur\",\"street\":\"100ft road\",\"postalCode\":\"702215\"}}",
        strObj);
    }

    public static CustomerRequestKafka getCustomerData() {
        CustomerRequestKafka customer = new CustomerRequestKafka();
        customer.setCustomerNumber("C000000004");
        customer.setFirstName("hemanth112244");
        customer.setLastName("Hemanth12234");
        customer.setBirthdate("26-12-2010");
        customer.setCountry("India");
        customer.setCountry("IN");
        customer.setMobileNumber("8179822721");
        customer.setEmail("string@email.com");
        customer.setCustomerStatus(CustomerRequestKafka.CustomerStatusEnum.RESTORED);

        AddressPublisherKafka address = new AddressPublisherKafka();
        address.setAddressLine1("Ayyapa society");
        address.setAddressLine2("madhapur");
        address.setStreet("100ft road");
        address.setPostalCode("702215");

        customer.setAddress(address);

        return customer;
    }
}

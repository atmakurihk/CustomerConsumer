package com.customer.service;

import com.customer.aop.ConsumerServiceAspectTest;
import com.customer.converters.CustomerDataMaskConverter;
import com.customer.model.kafkaModel.CustomerRequestKafka;
import com.customer.service.impl.KafkaListnerImpl;
import com.customer.utils.ObjectMapperUtilsTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KafkaListnerImplTest {

    @InjectMocks
    private KafkaListnerImpl kafkaListner;

    @Mock
    private CustomerDataMaskConverter customerDataMaskConverter;

    @Mock
    private ConsumerService consumerService;

    @Test
    public void testSubscribe() {
        CustomerRequestKafka publisherRequest =
                ConsumerServiceAspectTest.createPublisherRequest(
                        ObjectMapperUtilsTest.getCustomerData(), "transaction-id", "activity-id");
        kafkaListner.listen(publisherRequest);

        verify(consumerService, times(1)).publishCustomerData(Mockito.any());
    }

    @Test
    public void testSubscribeFailure() {
        CustomerRequestKafka publisherRequest =
                ConsumerServiceAspectTest.createPublisherRequest(
                        ObjectMapperUtilsTest.getCustomerData(), "transaction-id", "activity-id");
        Mockito.when(customerDataMaskConverter.convert(Mockito.any()))
                .thenThrow(new RuntimeException("Unable to convert"));

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(
                        () -> {
                            kafkaListner.listen(publisherRequest);
                        })
                .withMessage("Unable to convert");

        verify(consumerService, never()).publishCustomerData(Mockito.any());
    }
}

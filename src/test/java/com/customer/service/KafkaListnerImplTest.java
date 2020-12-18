package com.customer.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

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
                        ObjectMapperUtilsTest.getCustomerData();
        kafkaListner.subscribe(publisherRequest);

        verify(consumerService, times(1)).publishCustomerData(Mockito.any());
    }

    @Test
    public void testSubscribeFailure() {
        CustomerRequestKafka publisherRequest = ObjectMapperUtilsTest.getCustomerData();
        Mockito.when(customerDataMaskConverter.convert(Mockito.any()))
                .thenThrow(new RuntimeException("Unable to convert"));

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(
                        () -> {
                            kafkaListner.subscribe(publisherRequest);
                        })
                .withMessage("Unable to convert");

        verify(consumerService, never()).publishCustomerData(Mockito.any());
    }
}

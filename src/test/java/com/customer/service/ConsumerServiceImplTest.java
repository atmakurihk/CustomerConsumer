package com.customer.service;


import com.customer.aop.ConsumerServiceAspectTest;
import com.customer.converters.CustomerDataMaskConverter;
import com.customer.dao.AuditLogRepository;
import com.customer.model.kafkaModel.CustomerRequestKafka;
import com.customer.service.impl.ConsumerServiceImpl;
import com.customer.utils.ObjectMapperUtilsTest;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ConsumerServiceImplTest {

  @InjectMocks
  ConsumerServiceImpl consumerService;

  @Mock private AuditLogRepository auditLogRepository;

  @Mock private CustomerDataMaskConverter customerDataMaskConverter;

  @Test
  public void testPublishCustomerData() {
    CustomerRequestKafka publisherRequest =
        ConsumerServiceAspectTest.createPublisherRequest(
                ObjectMapperUtilsTest.getCustomerData(), "transaction-id", "activity-id");
    consumerService.publishCustomerData(publisherRequest);

    verify(auditLogRepository, times(1)).save(Mockito.any());
  }

  @Test
  public void testPublishCustomerDataFailure() {
    CustomerRequestKafka publisherRequest =
        ConsumerServiceAspectTest.createPublisherRequest(
                ObjectMapperUtilsTest.getCustomerData(), "transaction-id", "activity-id");
    Mockito.when(auditLogRepository.save(Mockito.any()))
        .thenThrow(new ServiceException("Unable to persist"));

    assertThatExceptionOfType(ServiceException.class)
        .isThrownBy(
            () -> {
              consumerService.publishCustomerData(publisherRequest);
            })
        .withMessage("Unable to persist");
  }
}

package com.customer.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.customer.converters.CustomerDataMaskConverter;
import com.customer.dao.AuditLogRepository;
import com.customer.exception.GenericException;
import com.customer.model.kafkaModel.CustomerRequestKafka;
import com.customer.service.impl.ConsumerServiceImpl;
import com.customer.utils.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ConsumerServiceImplTest {

  @InjectMocks
  private ConsumerServiceImpl consumerService;

  @Mock private AuditLogRepository auditLogRepository;

  @Mock private CustomerDataMaskConverter customerDataMaskConverter;

  @Test
  public void testPublishCustomerData() {
    CustomerRequestKafka publisherRequest =
               TestDataUtil.createValidCustomerObject();
    consumerService.publishCustomerData(publisherRequest);

    verify(auditLogRepository, times(1)).save(Mockito.any());
  }

  @Test
  public void testPublishCustomerDataFailure() {
    CustomerRequestKafka publisherRequest =
            TestDataUtil.createValidCustomerObject();
    Mockito.when(auditLogRepository.save(Mockito.any()))
        .thenThrow(new GenericException("Unable to persist"));

    assertThatExceptionOfType(GenericException.class)
        .isThrownBy(
            () -> consumerService.publishCustomerData(publisherRequest))
        .withMessage("Unable to persist");
  }
}

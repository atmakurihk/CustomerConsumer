package com.customer.aop;

import com.customer.converters.CustomerDataMaskConverter;
import com.customer.dao.ErrorLogRepository;
import com.customer.model.kafkaModel.CustomerRequestKafka;
import com.customer.utils.ObjectMapperUtilsTest;
import org.aspectj.lang.JoinPoint;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ConsumerServiceAspectTest {

  @InjectMocks ConsumerServiceAspect consumerServiceAspect;

  @Mock private ErrorLogRepository errorLogRepository;

  @Mock private CustomerDataMaskConverter customerConsumerDataMasker;

  @Mock(answer = Answers.RETURNS_MOCKS)
  private JoinPoint joinPoint;

  @Test
  public void testHandleThrownException() {
    CustomerRequestKafka publisherRequest =
        createPublisherRequest(
            ObjectMapperUtilsTest.getCustomerData(), "transaction-id", "activity-id");
    Mockito.when(joinPoint.getArgs()).thenReturn(new Object[] {publisherRequest});
    consumerServiceAspect.handleThrownException(
        joinPoint, new ServiceException("Unable to persist"));

    verify(errorLogRepository, times(1)).save(Mockito.any());
  }

  public static CustomerRequestKafka createPublisherRequest(
          CustomerRequestKafka customerData, String transactionId, String activityId) {


    customerData.setActivityId(activityId);
    customerData.setTransactionId(transactionId);


    return customerData;
  }
}

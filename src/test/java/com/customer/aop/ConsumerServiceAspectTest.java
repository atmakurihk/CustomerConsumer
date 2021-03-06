package com.customer.aop;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.customer.converters.CustomerDataMaskConverter;
import com.customer.dao.ErrorLogRepository;
import com.customer.exception.GenericException;
import com.customer.model.kafkaModel.CustomerRequestKafka;
import com.customer.utils.TestDataUtil;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;




@ExtendWith(MockitoExtension.class)
public class ConsumerServiceAspectTest {

  @InjectMocks private ConsumerServiceAspect consumerServiceAspect;

  @Mock private ErrorLogRepository errorLogRepository;

  @Mock private CustomerDataMaskConverter customerDataMaskConverter;

  @Mock(answer = Answers.RETURNS_MOCKS)
  private JoinPoint joinPoint;


  @Test
  public void testHandleThrownException() {
    CustomerRequestKafka publisherRequest = TestDataUtil.createValidCustomerObject();
    Mockito.when(joinPoint.getArgs()).thenReturn(new Object[] {publisherRequest});
    consumerServiceAspect.handleThrownException(
            joinPoint, new GenericException("Unable to persist"));
    verify(errorLogRepository, times(1)).save(Mockito.any());
  }

}

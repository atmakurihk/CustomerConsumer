package com.customer.aop;

import com.customer.converters.CustomerDataMaskConverter;
import com.customer.dao.ErrorLogRepository;
import com.customer.entity.ErrorLog;
import com.customer.model.kafkaModel.CustomerRequestKafka;
import com.customer.utils.ObjectMapperUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ConsumerServiceAspect {
  private static final Logger logger = LoggerFactory.getLogger(ConsumerServiceAspect.class);

  @Autowired private ErrorLogRepository errorLogRepository;

  @Autowired private CustomerDataMaskConverter customerDataMaskConverter;

  @AfterThrowing(
      pointcut =
          "execution(* com.customer.service.impl.ConsumerServiceImpl.publishCustomerData(..))",
      throwing = "ex")
  public void handleThrownException(JoinPoint joinPoint, Throwable ex) {
    CustomerRequestKafka request = (CustomerRequestKafka) joinPoint.getArgs()[0];
    logger.error(
        "Error caught for, transaction-id:{}, and activity-id:{} ",
        request.getTransactionId(),
        request.getActivityId());

    ErrorLog errorLog = getErrorLogFromRequest(request, ex);
    errorLogRepository.save(errorLog);
    logger.info("Error logged successfully:{}", errorLog);
  }

  private ErrorLog getErrorLogFromRequest(CustomerRequestKafka customerRequestKafka, Throwable ex) {
    ErrorLog errorLog = new ErrorLog();
    errorLog.setErrorDescription(ex.getMessage());
    errorLog.setErrorType(ex.getClass().getName());
    errorLog.setPayload(
        ObjectMapperUtil.getJsonFromObj(customerDataMaskConverter.convert(customerRequestKafka)));
    return errorLog;
  }
}

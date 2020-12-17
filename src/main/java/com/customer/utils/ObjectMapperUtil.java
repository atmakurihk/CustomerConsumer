package com.customer.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.service.spi.ServiceException;

import java.io.IOException;

public class ObjectMapperUtil {

  private ObjectMapperUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static String getJsonFromObj(Object obj) {
    ObjectMapper objMapper = new ObjectMapper();
    try {
      return objMapper.writeValueAsString(obj);
    } catch (IOException e) {
      throw new ServiceException(
          "Exception encountered while converting to JSON string:" + e.getMessage());
    }
  }
}

package com.customer.utils;

import com.customer.exception.GenericException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class ObjectMapperUtil {

  public static String getJsonFromObj(Object obj) {
    ObjectMapper objMapper = new ObjectMapper();
    try {
      return objMapper.writeValueAsString(obj);
    } catch (IOException e) {
      throw new GenericException(
          "Exception encountered while converting to JSON string:" + e.getMessage());
    }
  }
}

package com.customer.model.kafkaModel;

public class PublisherRequest {
  private CustomerRequestKafka customerData;
  private String transactionId;
  private String activityId;

  public CustomerRequestKafka getCustomerData() {
    return customerData;
  }

  public void setCustomerData(CustomerRequestKafka customerData) {
    this.customerData = customerData;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getActivityId() {
    return activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }
}

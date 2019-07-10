package com.example.subscriber.domain;

import wsdl_objects.Subscriber;

import java.util.List;

/**
 * @author mali.sahin
 * @since 7/10/19.
 */
public class SubscriberWrapper {

  public SubscriberWrapper(List<Subscriber> subscribers) {
    this.subscribers = subscribers;
  }

  private List<Subscriber> subscribers;

  public List<Subscriber> getSubscribers() {
    return subscribers;
  }

  public void setSubscribers(List<Subscriber> subscribers) {
    this.subscribers = subscribers;
  }
}

package com.example.subscriber.util;

import org.json.simple.JSONObject;
import wsdl_objects.Subscriber;

/**
 * @author mali.sahin
 * @since 7/10/19.
 */
public class SubscriberParser {

  public static Subscriber parseJsonToSubscriber(JSONObject jsonObject) {
    //Get employee object within list
    Subscriber subscriber = new Subscriber();
    //Get employee first name
    subscriber.setId(Long.parseLong((String) jsonObject.get("id")));
    subscriber.setName((String) jsonObject.get("name"));
    subscriber.setMsisdn(Long.parseLong((String) jsonObject.get("msisdn")));
    return subscriber;
  }
}

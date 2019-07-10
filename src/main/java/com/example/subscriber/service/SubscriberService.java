package com.example.subscriber.service;

import com.example.subscriber.wsdl.Subscriber;

import java.util.List;
import java.util.Optional;

/**
 * @author mali.sahin
 * @since 2019-07-09.
 */
public interface SubscriberService {

    Subscriber createSubscriber(Subscriber subscriber);

    Subscriber updateSubscriber(Subscriber subscriber);

    void deleteSubscriber(Long subscriberId);

    List<Subscriber> getAllSubscribers();

    Optional<Subscriber> getSubscriberById(Long subscriberId);
}

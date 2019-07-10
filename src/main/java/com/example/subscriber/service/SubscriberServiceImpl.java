package com.example.subscriber.service;

import org.springframework.stereotype.Service;
import wsdl_objects.Subscriber;

import java.util.List;
import java.util.Optional;

/**
 * @author mali.sahin
 * @since 2019-07-09.
 */
@Service
public class SubscriberServiceImpl implements  SubscriberService {


    @Override
    public Subscriber createSubscriber(Subscriber subscriber) {
        return null;
    }

    @Override
    public Subscriber updateSubscriber(Subscriber subscriber) {
        return null;
    }

    @Override
    public void deleteSubscriber(Long subscriberId) {

    }

    @Override
    public List<Subscriber> getAllSubscribers() {
        return null;
    }

    @Override
    public Optional<Subscriber> getSubscriberById(Long subscriberId) {
        return Optional.empty();
    }
}

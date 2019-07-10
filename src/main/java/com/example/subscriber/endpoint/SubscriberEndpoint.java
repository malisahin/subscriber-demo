package com.example.subscriber.endpoint;

import com.example.subscriber.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import wsdl_objects.Subscriber;
import wsdl_objects.SubscriberRequest;
import wsdl_objects.SubscriberResponse;

import java.util.Optional;

/**
 * @author mali.sahin
 * @since 2019-07-09.
 */
@Endpoint
public class SubscriberEndpoint {

    @Autowired
    private SubscriberService subscriberService;

    @PayloadRoot(namespace = "wsdl_objects", localPart = "SubscriberResponse")
    @ResponsePayload
    public SubscriberResponse getSubscriberByIdRequest(@RequestPayload SubscriberRequest request) {
        final Optional<Subscriber> optSubscriber = subscriberService.getSubscriberById(request.getId());
        final SubscriberResponse response = new SubscriberResponse();
        optSubscriber.ifPresent(response::setSubscriber);
        return response;
    }

}

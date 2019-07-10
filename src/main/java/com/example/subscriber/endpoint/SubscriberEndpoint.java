package com.example.subscriber.endpoint;

import com.example.subscriber.service.SubscriberService;
import com.example.subscriber.wsdl.AllSubscribersResponse;
import com.example.subscriber.wsdl.Subscriber;
import com.example.subscriber.wsdl.SubscriberRequest;
import com.example.subscriber.wsdl.SubscriberResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.Optional;

/**
 * @author mali.sahin
 * @since 2019-07-09.
 */
@Endpoint
public class SubscriberEndpoint {

    private static final String NAMESPACE_URI = "http://www.example.com/subscriber/wsdl";

    @Autowired
    private SubscriberService subscriberService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SubscriberRequest")
    @ResponsePayload
    public SubscriberResponse getSubscriberByIdRequest(@RequestPayload SubscriberRequest request) {
        final Optional<Subscriber> optSubscriber = subscriberService.getSubscriberById(request.getId());
        final SubscriberResponse response = new SubscriberResponse();
        optSubscriber.ifPresent(response::setSubscriber);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AllSubscribersRequest")
    @ResponsePayload
    public AllSubscribersResponse getAllSubscribers() {
        final List<Subscriber> subscriberList = subscriberService.getAllSubscribers();
        final AllSubscribersResponse response = new AllSubscribersResponse();
        response.getSubscriberList().addAll(subscriberList);
        return response;
    }
}

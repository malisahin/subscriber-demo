package com.example.subscriber.controller;

import com.example.subscriber.base.AbstractBaseComponent;
import com.example.subscriber.service.SubscriberService;
import com.example.subscriber.wsdl.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mali.sahin
 * @since 7/10/19.
 */
@RestController
@RequestMapping("/subscriber")
public class SubscriberController extends AbstractBaseComponent {

  @Autowired
  private SubscriberService subscriberService;

  @PostMapping
  public ResponseEntity<Subscriber> createSubscriber(@RequestBody Subscriber subscriber, HttpServletRequest request) {
    return new ResponseEntity<>(subscriberService.createSubscriber(subscriber), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<Subscriber> updateSubscriber(@RequestBody Subscriber subscriber) {
    return new ResponseEntity<>(subscriberService.updateSubscriber(subscriber), HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity deleteSubscriber(@RequestBody Subscriber subscriber, HttpServletRequest request) {
    subscriberService.deleteSubscriber(subscriber.getId());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}

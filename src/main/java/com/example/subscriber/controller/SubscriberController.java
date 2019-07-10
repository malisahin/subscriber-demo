package com.example.subscriber.controller;

import com.example.subscriber.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wsdl_objects.Subscriber;

/**
 * @author mali.sahin
 * @since 7/10/19.
 */
@RestController
@RequestMapping("/subscriber")
public class SubscriberController {

  @Autowired
  private SubscriberService subscriberService;

  @PostMapping
  public ResponseEntity<Subscriber> createSubscriber(@RequestBody Subscriber subscriber) {
    return new ResponseEntity<>(subscriberService.createSubscriber(subscriber), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<Subscriber> updateSubscriber(@RequestBody Subscriber subscriber) {
    return new ResponseEntity<>(subscriberService.updateSubscriber(subscriber), HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity deleteSubscriber(@RequestBody Subscriber subscriber) {
    subscriberService.deleteSubscriber(subscriber.getId());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}

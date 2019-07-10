package com.example.subscriber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import wsdl_objects.Subscriber;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * @author mali.sahin
 * @since 2019-07-09.
 */
@Service
public class SubscriberServiceImpl implements SubscriberService {


  @Autowired
  CacheManager cacheManager;


  @Override
  public Subscriber createSubscriber(Subscriber subscriber) {

    final Cache cache = cacheManager.getCache("store");
    cache.put(subscriber.getId(), subscriber);
    getAllSubscribers();
    return subscriber;
  }

  @Override
  public Subscriber updateSubscriber(Subscriber subscriber) {
    final Cache cache = cacheManager.getCache("store");
    final Subscriber old = (Subscriber) cache.get(subscriber.getId());
    old.setName(subscriber.getName());
    old.setMsisdn(subscriber.getMsisdn());
    cache.put(old.getId(), old);
    return old;
  }

  @Override
  public void deleteSubscriber(Long subscriberId) {
    final Cache cache = cacheManager.getCache("store");
    cache.evict(subscriberId);
  }

  @Override
  public List<Subscriber> getAllSubscribers() {
    final Cache cache = cacheManager.getCache("store");
    ConcurrentMap<Long, Subscriber> mapCache = (ConcurrentMap<Long, Subscriber>) cache.getNativeCache();
    return (List<Subscriber>) mapCache.values();
  }

  @Override
  public Optional<Subscriber> getSubscriberById(Long subscriberId) {
    final Cache cache = cacheManager.getCache("store");
    ConcurrentMap<Long, Subscriber> mapCache = (ConcurrentMap<Long, Subscriber>) cache.getNativeCache();
    return mapCache.keySet()
        .stream()
        .filter(key -> key.equals(subscriberId))
        .map((Function<Long, Object>) mapCache::get)
        .map(cacheObject -> (Subscriber) cacheObject)
        .findFirst();
  }
}

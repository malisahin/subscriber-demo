package com.example.subscriber.service;

import com.example.subscriber.base.AbstractBaseComponent;
import com.example.subscriber.base.Constants;
import com.example.subscriber.base.exceptions.BusinessValidationException;
import com.example.subscriber.base.exceptions.ResourceNotFoundException;
import com.example.subscriber.wsdl.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * @author mali.sahin
 * @since 2019-07-09.
 */
@Service
public class SubscriberServiceImpl extends AbstractBaseComponent implements SubscriberService {


    @Autowired
    private CacheManager cacheManager;


    @Override
    public Subscriber createSubscriber(Subscriber subscriber) {
        this.validateSubscriberRequiredFields(subscriber);
        final Cache cache = cacheManager.getCache(Constants.STORE_PATH);
        Optional.ofNullable(cache)
                .ifPresent(store -> store.put(subscriber.getId(), subscriber));
        return subscriber;
    }

    @Override
    public Subscriber updateSubscriber(Subscriber subscriber) {
        this.validateSubscriberIfPresent(subscriber.getId());
        final Cache cache = cacheManager.getCache(Constants.STORE_PATH);
        return Optional.ofNullable(cache)
                .map(store -> store.get(subscriber.getId()))
                .map(old -> (Subscriber) old.get())
                .map(old -> {
                    old.setName(subscriber.getName());
                    old.setMsisdn(subscriber.getMsisdn());
                    cache.put(old.getId(), old);
                    return old;
                }).orElse(null);
    }

    private void validateSubscriberRequiredFields(Subscriber subscriber) {
        Optional.of(subscriber)
                .map(sub -> {
                    this.validateSubscriberId(sub.getId());
                    return sub;
                }).orElseThrow(() -> new BusinessValidationException("Subscriber cannot be null !!"));
    }

    private void validateSubscriberId(Long subscriberId) {
        Optional.of(subscriberId)
                .filter(id -> id > 0)
                .orElseThrow(() -> new BusinessValidationException("Id is compulsory field for Subscriber!!!"));
    }

    private void validateSubscriberIfPresent(final Long subscriberId) {
        this.validateSubscriberId(subscriberId);
        this.getSubscriberById(subscriberId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscriber Not Found"));
    }

    @Override
    public void deleteSubscriber(Long subscriberId) {
        this.validateSubscriberIfPresent(subscriberId);
        final Cache cache = cacheManager.getCache(Constants.STORE_PATH);
        Optional.ofNullable(cache).ifPresent((store -> store.evict(subscriberId)));
    }

    @Override
    public List<Subscriber> getAllSubscribers() {
        final Cache cache = cacheManager.getCache(Constants.STORE_PATH);
        ConcurrentMap<Long, Subscriber> mapCache = (ConcurrentMap<Long, Subscriber>) cache.getNativeCache();
        return new ArrayList<>(mapCache.values());
    }

    @Override
    public Optional<Subscriber> getSubscriberById(Long subscriberId) {
        this.validateSubscriberId(subscriberId);
        final Cache cache = cacheManager.getCache(Constants.STORE_PATH);
        ConcurrentMap<Long, Subscriber> mapCache = (ConcurrentMap<Long, Subscriber>) cache.getNativeCache();
        return mapCache.keySet()
                .stream()
                .filter(key -> key.equals(subscriberId))
                .map((Function<Long, Object>) mapCache::get)
                .map(cacheObject -> (Subscriber) cacheObject)
                .findFirst();
    }
}

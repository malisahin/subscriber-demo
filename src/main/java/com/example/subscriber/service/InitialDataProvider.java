package com.example.subscriber.service;

import com.example.subscriber.base.AbstractBaseComponent;
import com.example.subscriber.domain.SubscriberWrapper;
import com.example.subscriber.util.FileHelper;
import com.example.subscriber.wsdl.Subscriber;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author mali.sahin
 * @since 7/10/19.
 */
@Component
public class InitialDataProvider extends AbstractBaseComponent implements InitializingBean {

    private static List<Subscriber> subscriberList;

    static {
        subscriberList = new ArrayList<>();
        final Subscriber subscriber = new Subscriber();
        subscriber.setId(1);
        subscriber.setName("Stephan King");
        subscriber.setMsisdn(905552551122L);
        subscriberList.add(subscriber);
        final Subscriber subscriber1 = new Subscriber();
        subscriber1.setId(2);
        subscriber1.setName("Alice Gray");
        subscriber1.setMsisdn(905552551133L);
        subscriberList.add(subscriber1);
        final Subscriber subscriber2 = new Subscriber();
        subscriber2.setId(3);
        subscriber2.setName("Glory Wisdom");
        subscriber2.setMsisdn(905552551144L);
        subscriberList.add(subscriber2);
    }

    @Value("${initial.data.path}")
    String initialDataPath;

    @Autowired
    private SubscriberService subscriberService;

    @Override
    public void afterPropertiesSet() {
        this.createDataFileIfNotExist();
        this.initData();
    }

    private void createDataFileIfNotExist() {
        new File(initialDataPath);
    }

    private void initData() {
        final List<Subscriber> subscriberList = InitialDataProvider.subscriberList; // readFromJsonFile();
        subscriberList.forEach(subscriber -> subscriberService.createSubscriber(subscriber));


    }

    private List<Subscriber> readFromJsonFile() throws IOException {
        final SubscriberWrapper wrapper = FileHelper.readFromJsonFile(initialDataPath, SubscriberWrapper.class);
        return wrapper.getSubscribers();
    }


}

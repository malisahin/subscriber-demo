package com.example.subscriber.service;

import com.example.subscriber.base.AbstractBaseComponent;
import com.example.subscriber.domain.SubscriberWrapper;
import com.example.subscriber.util.FileHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wsdl_objects.Subscriber;

import java.io.IOException;
import java.util.List;


/**
 * @author mali.sahin
 * @since 7/10/19.
 */
@Component
public class InitialDataProvider extends AbstractBaseComponent implements InitializingBean {

  private static final Logger LOG
      = Logger.getLogger(InitialDataProvider.class);

  @Value("${initial.data.path}")
  String initialDataPath;

  @Autowired
  private SubscriberService subscriberService;

  @Override
  public void afterPropertiesSet() throws Exception {

    final List<Subscriber> subscriberList = readFromJsonFile();
    subscriberList.forEach(subscriber -> subscriberService.createSubscriber(subscriber));
  }

  private List<Subscriber> readFromJsonFile() throws IOException {
    final SubscriberWrapper wrapper = FileHelper.readFromJsonFile(initialDataPath, SubscriberWrapper.class);
    return wrapper.getSubscribers();
  }


}

package com.example.subscriber.service;

import com.example.subscriber.base.AbstractBaseComponent;
import com.example.subscriber.domain.SubscriberWrapper;
import com.example.subscriber.util.FileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import wsdl_objects.Subscriber;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author mali.sahin
 * @since 7/10/19.
 */
@Component
public class SubscriberScheduleTasks extends AbstractBaseComponent implements DataSyncScheduleTasks {

  @Value("${initial.data.path}")
  String initialDataPath;

  @Autowired
  private SubscriberService subscriberService;


  @Override
  public void scheduleTaskWithFixedRate() {

  }

  @Override
  public void scheduleTaskWithFixedDelay() {

  }

  @Override
  public void scheduleTaskWithInitialDelay() {

  }

  @Override
  @Scheduled(cron = "${subscribeSyncSchedule}")
  public void scheduleTaskWithCronExpression() {
    logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
    final List<Subscriber> subscriberList = subscriberService.getAllSubscribers();
    SubscriberWrapper wrapper = new SubscriberWrapper(subscriberList);
    FileHelper.writeToJsonFile(wrapper, initialDataPath);
  }
}

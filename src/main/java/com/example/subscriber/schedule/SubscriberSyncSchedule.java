package com.example.subscriber.schedule;

import com.example.subscriber.base.AbstractBaseComponent;
import com.example.subscriber.domain.SubscriberWrapper;
import com.example.subscriber.service.SubscriberService;
import com.example.subscriber.util.FileHelper;
import com.example.subscriber.wsdl.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author mali.sahin
 * @since 7/10/19.
 */
@Component
public class SubscriberSyncSchedule extends AbstractBaseComponent {

    @Value("${initial.data.path}")
    String initialDataPath;

    @Autowired
    private SubscriberService subscriberService;

    @Scheduled(cron = "${subscribeSyncSchedule}")
    public void runSyncJob() {
        logger.info("Subscriber sync task :: Execution Time - {} ", dateTimeFormatter.format(LocalDateTime.now()));
        final List<Subscriber> subscriberList = subscriberService.getAllSubscribers();
        SubscriberWrapper wrapper = new SubscriberWrapper(subscriberList);
        FileHelper.writeToJsonFile(wrapper, initialDataPath);
    }
}

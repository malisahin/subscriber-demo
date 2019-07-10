package com.example.subscriber.service;

import com.example.subscriber.util.SubscriberParser;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wsdl_objects.Subscriber;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author mali.sahin
 * @since 7/10/19.
 */
@Component
public class InitialDataProvider implements InitializingBean {

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

  private List<Subscriber> readFromJsonFile() throws IOException, ParseException {
    final JSONParser parser = new JSONParser();
    Object object = parser.parse(new FileReader(initialDataPath));
    JSONObject jsonObject = (JSONObject) object;
    JSONArray subscriberJsonObject = (JSONArray) jsonObject.get("subscribers");

    return (List<Subscriber>) subscriberJsonObject.stream()
        .map(json -> SubscriberParser.parseJsonToSubscriber((JSONObject) json))
        .collect(Collectors.toList());
  }


}

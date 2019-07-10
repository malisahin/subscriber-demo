package com.example.subscriber.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

/**
 * @author mali.sahin
 * @since 7/10/19.
 */
public abstract class AbstractBaseComponent implements Serializable {

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());
  protected final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

  protected final ObjectMapper mapper = new ObjectMapper();
}

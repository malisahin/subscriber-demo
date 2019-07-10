package com.example.subscriber.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author mali.sahin
 * @since 7/10/19.
 */
public class FileHelper {

  public static <T> T readFromJsonFile(String filePath, Class<T> valueType) throws IOException {
    //Read from JSON file
    final ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(filePath, valueType);
  }

  public static void writeToJsonFile(Object object, String filePath) {
    //Write JSON file
    final ObjectMapper mapper = new ObjectMapper();
    try (FileWriter file = new FileWriter(filePath)) {
      final String jsonData = mapper.writeValueAsString(object);
      file.write(jsonData);
      file.flush();

    } catch (IOException e) {
      
      e.printStackTrace(); // fixme logger
    }
  }
}

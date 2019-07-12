package com.example.subscriber.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author mali.sahin
 * @since 7/10/19.
 */
public class FileHelper {

    private static final Logger logger = LoggerFactory.getLogger(FileHelper.class);

    private FileHelper() {
    }

    public static <T> T readFromJsonFile(String filePath, Class<T> valueType) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        return mapper.readValue(new File(filePath), valueType);
    }

    public static void writeToJsonFile(Object object, String filePath) {
        final ObjectMapper mapper = new ObjectMapper();
        try (FileWriter file = new FileWriter(filePath)) {
            final String jsonData = mapper.writeValueAsString(object);
            file.write(jsonData);
            file.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e.getCause());
        }
    }
}

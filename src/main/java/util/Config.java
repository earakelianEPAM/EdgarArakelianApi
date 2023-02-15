package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private final Properties propertyFile;

    public Config(String pathToProperty) {
        propertyFile = new Properties();
        String path = new File(pathToProperty).getAbsolutePath();
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            propertyFile.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getApiKey() {
        return propertyFile.getProperty("key");
    }

    public String getApiToken() {
        return propertyFile.getProperty("token");
    }
}

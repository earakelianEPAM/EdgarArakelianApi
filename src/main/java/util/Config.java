package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static final String TEST_DATA = "src/test/resources/testData";

    public static Properties getPropertyObject() {
        Properties properties = new Properties();
        String path = new File(TEST_DATA).getAbsolutePath();
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static String getApiKey() {
        return getPropertyObject().getProperty("key");
    }

    public static String getApiToken() {
        return getPropertyObject().getProperty("token");
    }
}

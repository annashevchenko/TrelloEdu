package fw.pages;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RunResourcesByTest {

    public static void main(String[] args) throws IOException {
        new RunResourcesByTest().getProperties();


    }

    String RES_NAME = "testSettings.properties";

    Properties getProperties() throws IOException {

        Properties properties = new Properties();
        try (InputStream stream = this.getClass().getResourceAsStream(RES_NAME)) {
            properties.load(stream);
            return properties;
        }
    }

    }

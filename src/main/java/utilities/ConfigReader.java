package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private final Properties properties;
    private final String fileName;

    public ConfigReader(String fileName) {
        this.fileName = fileName;
        File file1 = new File(fileName);
        properties = new Properties();
        try {
            if (file1.exists()) {
                FileInputStream file = new FileInputStream(this.fileName);
                properties.load(file);
                file.close();

            } else {
                InputStream file = this.getClass().getResourceAsStream("/" + this.fileName);
                properties.load(file);
                file.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public String getProperty(String key) {

        return properties.getProperty(key);
    }


}






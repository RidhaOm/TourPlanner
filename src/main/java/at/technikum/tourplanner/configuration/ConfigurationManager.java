package at.technikum.tourplanner.configuration;

import at.technikum.tourplanner.repository.TourLogRepository;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {
    private Properties properties;
    private static final Logger logger = Logger.getLogger(TourLogRepository.class);

    public ConfigurationManager(){
        loadProperties();
    }
    private void loadProperties(){
        try {
            properties = new Properties();
            InputStream inputStream = new FileInputStream("src/main/resources/configuration.properties");
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            logger.error("[CM-001] Error loading config.properties");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String getApiKey(){
        return properties.getProperty("API_KEY");
    }
    public String getImagePath(){
        return properties.getProperty("IMAGE_PATH");
    }
    public String getPdfPath(){
        return properties.getProperty("PDF_PATH");
    }
    public String getTourReportsPath(){
        return properties.getProperty("TOUR_REPORTS_PATH");
    }
}

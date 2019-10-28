package com.omnius.commonutilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;


public class GetConfig {

    private static String FilePath = System.getProperty("user.dir") + "/src/main/resources/setup.properties";
    static File file = new File(FilePath);

    /**
     * Method to get property from properties file.
     *
     * @param property - Key name whose value is to be fetched
     *
     * @return - Returns the value for the key given
     */
    public static String getProperties(String property) throws ConfigurationException {
        PropertiesConfiguration config = new PropertiesConfiguration(file);
        Iterator<String> keys = config.getKeys();
        return config.getProperty(property).toString();
    }


    /**
     * Method to update a value of a  property in properties file.
     *
     * @param property     Property name whose value to be updated
     * @param proertyValue Value to be updated for the property name
     *
     * @throws IOException
     */
    public static void updateProperties(String property, String proertyValue) throws IOException, ConfigurationException {

        PropertiesConfiguration config = new PropertiesConfiguration();
        PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout(config);
        layout.load(new InputStreamReader(new FileInputStream(file)));

        config.setProperty(property, proertyValue);
        layout.save(new FileWriter(System.getProperty("user.dir") + "/src/main/resources/setup.properties", false));
    }

    public static String getReportConfigPath() throws ConfigurationException {
        String reportConfigPath = System.getProperty("user.dir") + getProperties("reportConfigPath");
        if (reportConfigPath != null)
            return reportConfigPath;
        else
            throw new RuntimeException("Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");
    }

}

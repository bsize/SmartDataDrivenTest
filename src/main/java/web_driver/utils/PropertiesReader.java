package web_driver.utils;

import web_driver.logger.Logger;
import java.io.*;
import java.net.URL;
import java.util.Properties;

public class PropertiesReader {
    private static Properties props;
    private static URL  url;
    private static InputStream reader;
    private static String outputText;
    private static Logger logger = Logger.getInstance();

    public static String getTestProps(String prop){
        props = new Properties();
        try {
            reader = new BufferedInputStream(url.openStream());
            props.load(reader);
            outputText = props.getProperty(prop);
            reader.close();
        } catch (IOException e) {
            logger.error("Failed to retrieve property");
        }
        logger.info(String.format("Get property == %s == for %s", outputText,  prop));
        return outputText;
    }

    public static String getPropsFromXML(String name){
        props = new Properties();
        try {
            reader = new BufferedInputStream(url.openStream());
            props.loadFromXML(reader);
            outputText = props.getProperty(name);
            reader.close();
        } catch (IOException e) {
            logger.error("Failed to retrieve property");
        }
        logger.info(String.format("Get property == %s == for %s", outputText,  name));
        return outputText;
    }

    public void setUrl(URL confProp) {
        logger.info(String.format("Set URL path == %s == for read dates", confProp));
        this.url = confProp;
    }
}
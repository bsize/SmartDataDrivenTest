package web_driver.utils;

import web_driver.logger.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpression {

    private static Logger logger = Logger.getInstance();

    public static String searchRegex(String regex, String text) {
        logger.info(String.format("Search regex = '%s' in text", regex));
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.toString();
    }
}

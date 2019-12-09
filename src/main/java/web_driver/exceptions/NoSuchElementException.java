package web_driver.exceptions;
import web_driver.logger.Logger;

public class NoSuchElementException extends Exception{
    private static Logger logger = Logger.getInstance();
    public NoSuchElementException() {
        logger.info(logger.getLoc("loc.no.element"));
    }

}

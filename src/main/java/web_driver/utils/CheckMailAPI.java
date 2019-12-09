package web_driver.utils;


import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import web_driver.Browser;
import web_driver.logger.Logger;
import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class CheckMailAPI {

    private static Logger logger = Logger.getInstance();
    private static URL confProp = PropertiesReader.class.getClassLoader().getResource("configuration.properties");
    private static final int MAX_TIME_WAIT_UNTIL_MESSAGE_COME = 10;
    private static final String MIME_TYPE_PLANE = "text/plain";
    private static final String MIME_TYPE_HTML = "text/html";

    public static String getMessage() throws MessagingException, IOException{
        logger.info("Get text from message");
        waitUntilComeMessage();
        Store store = createEmailSession();
        Folder emailFolder = createInbox(store);
        Message[] messages = emailFolder.getMessages();
        if (messages.length > 1) {
            throw new IndexOutOfBoundsException();
        }
        Message message = emailFolder.getMessage(messages.length);
        MimeMultipart content = (MimeMultipart) message.getContent();
        String messageText = getTextFromMimeMultipart(content);
        emailFolder.close(true);
        store.close();
        return messageText;
    }

    private static Store createEmailSession() {
        logger.info("Create Mail session");
        PropertiesReader propertiesReader = new PropertiesReader();
        propertiesReader.setUrl(confProp);
        String host = propertiesReader.getTestProps("host");
        String email = propertiesReader.getTestProps("email");
        String password = propertiesReader.getTestProps("password");
        Properties properties = new Properties();
        properties.put(propertiesReader.getTestProps("pop3host"), host);
        properties.put(propertiesReader.getTestProps("pop3port"), propertiesReader.getTestProps("numberOfPort"));
        properties.put(propertiesReader.getTestProps("pop3starttls"), "true");
        Session emailSession = Session.getDefaultInstance(properties);
        Store store = null;
        try {
            store = emailSession.getStore("pop3s");
            store.connect(host, email, password);
        } catch (MessagingException e) {
            logger.error("Connect session failed");
        }
        return store;
    }

    private static Folder createInbox(Store store) {
        logger.info("Create Mail Box");
        Folder folder = null;
        try {
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
        } catch (MessagingException e) {
            logger.error("Create a folder Inbox failed ");
        }
        return folder;
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
        logger.info("Convert text from message");
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType(MIME_TYPE_PLANE)) {
                result.append("\n").append(bodyPart.getContent());
                break;
            } else if (bodyPart.isMimeType(MIME_TYPE_HTML)) {
                String html = (String) bodyPart.getContent();
                result.append("\n").append(org.jsoup.Jsoup.parse(html).text());
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        return result.toString();
    }

    public static void deleteMessageBeforeTest() {
        Store store = createEmailSession();
        try {
            Folder emailFolder = createInbox(store);
            Message[] messages = emailFolder.getMessages();
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                message.setFlag(Flags.Flag.DELETED, true);
            }
            emailFolder.close(true);
            store.close();
            logger.info("Delete messages in Mail Box");
        } catch (MessagingException e) {
            logger.error("Delete message failed");
        }
    }

    private static void waitUntilComeMessage(){
        logger.info("Wait until come message");
        WebDriverWait wait = new WebDriverWait(Browser.getInstance().getDriver(), MAX_TIME_WAIT_UNTIL_MESSAGE_COME);
        wait.until((ExpectedCondition<Boolean>) driver -> {
            Message[] messages;
            Store store = createEmailSession();
            Folder emailFolder = createInbox(store);
            try {
                messages = emailFolder.getMessages();
            } catch (MessagingException e) {
                logger.error("Get message in folder failed");
                return false;
            }
            return messages.length == 1;
        });
    }
}

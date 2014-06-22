package name.sassi.fix.ecn.initiator;

import org.slf4j.Logger;
import quickfix.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by tsassi on 22/6/14.
 */
public class InitiatorMain {
    private static final Logger log = getLogger(InitiatorMain.class);

    public static void main(String[] args) {
        try {
            InputStream inputStream = null;
            if (args.length == 0) {
                inputStream = InitiatorMain.class.getResourceAsStream("/initiator.cfg");
            }
            if (args.length == 1) {
                inputStream = new FileInputStream(args[0]);
            }
            if (inputStream == null) {
                System.out.println("usage: " + InitiatorMain.class.getName() + " [configFile].");
                return;
            }
            ExtendedSessionSettings settings = new ExtendedSessionSettings(inputStream);
            Application application = new InitiatorApplication(settings);
            MessageStoreFactory storeFactory = new FileStoreFactory(settings);
            MessageFactory messageFactory = new DefaultMessageFactory();
            Initiator initiator = new SocketInitiator(application, storeFactory, settings, messageFactory);
            initiator.start();
            logon(initiator);
            System.out.println("press <enter> to quit");
            System.in.read();
            initiator.stop();
        } catch (ConfigError configError) {
            log.error("Configuration error.", configError);
        } catch (FileNotFoundException e) {
            log.error("File not found.", e);
        } catch (FieldConvertError e) {
            log.error("Field conversion error.", e);
        } catch (IOException e) {
           log.error("IO error.", e);
        }
    }

    public static synchronized void logon(Initiator initiator) {
        for (SessionID sessionId : initiator.getSessions()) {
            Session.lookupSession(sessionId).logon();
        }
    }
}

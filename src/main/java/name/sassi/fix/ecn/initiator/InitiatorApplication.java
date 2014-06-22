package name.sassi.fix.ecn.initiator;

import org.slf4j.Logger;
import quickfix.*;
import quickfix.field.MsgType;
import quickfix.field.Password;
import quickfix.field.Username;

import static org.slf4j.LoggerFactory.getLogger;
import static quickfix.field.MsgType.LOGON;

/**
 * Created by tsassi on 22/6/14.
 */
public class InitiatorApplication implements Application {
    private static final Logger log = getLogger(InitiatorMain.class);
    private ExtendedSessionSettings settings;

    public InitiatorApplication(ExtendedSessionSettings settings) {
        this.settings = settings;
    }

    @Override
    public void onCreate(SessionID sessionID) {
        log.info("Created session: {}", sessionID);
    }

    @Override
    public void onLogon(SessionID sessionID) {
        log.info("Logging on session: {}", sessionID);
    }

    @Override
    public void onLogout(SessionID sessionID) {
        log.info("Logging out session: {}", sessionID);

    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {
        try {
            final String msgType = message.getHeader().getString(MsgType.FIELD);
            if (LOGON.compareTo(msgType) == 0 && settings.hasCredentials(sessionID)) {
                message.setString(Username.FIELD, settings.getUserName(sessionID));
                message.setString(Password.FIELD, settings.getPassword(sessionID));
            } else {
                log.info("Session {} has no credentials.", sessionID);
            }
        } catch (FieldNotFound fieldNotFound) {
            fieldNotFound.printStackTrace();
        } catch (FieldConvertError fieldConvertError) {
            fieldConvertError.printStackTrace();
        } catch (ConfigError configError) {
            configError.printStackTrace();
        }
        log.info("To Admin message: {} for session: ", message, sessionID);
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        log.info("From Admin message: {} for session: ", message, sessionID);
    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        log.info("To App message: {} for session: ", message, sessionID);
    }

    @Override
    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        log.info("From App message: {} for session: ", message, sessionID);
    }
}

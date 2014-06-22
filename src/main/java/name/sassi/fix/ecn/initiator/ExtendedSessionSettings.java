package name.sassi.fix.ecn.initiator;

import quickfix.ConfigError;
import quickfix.FieldConvertError;
import quickfix.SessionID;
import quickfix.SessionSettings;

import java.io.InputStream;

/**
 * Created by tsassi on 22/6/14.
 */
public class ExtendedSessionSettings extends SessionSettings {
    public final static String USERNAME = "Username";
    public final static String PASSWORD = "Password";

    public ExtendedSessionSettings(InputStream stream) throws ConfigError, FieldConvertError {
        super(stream);
    }

    public String getUserName(SessionID sessionID) throws FieldConvertError, ConfigError {
        return getString(sessionID, USERNAME);
    }

    public String getPassword(SessionID sessionID) throws FieldConvertError, ConfigError {
        return getString(sessionID, PASSWORD);
    }

    public boolean hasCredentials(SessionID sessionID) {
        try {
            return getUserName(sessionID) != null && getPassword(sessionID) != null;
        } catch (FieldConvertError fieldConvertError) {
            fieldConvertError.printStackTrace();
        } catch (ConfigError configError) {
            configError.printStackTrace();
        }
        return false;
    }
}

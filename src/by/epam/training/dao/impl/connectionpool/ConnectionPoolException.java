package by.epam.training.dao.impl.connectionpool;

/**
 * Created by Настенька on 11/11/2015.
 */
public class ConnectionPoolException extends Exception {
    public ConnectionPoolException(){}
    public ConnectionPoolException(String message, Throwable exception) {
        super(message, exception);
    }
    public ConnectionPoolException(String message) {
        super(message);
    }
    public ConnectionPoolException(Throwable exception) {
        super(exception);
    }
}

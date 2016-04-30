package by.epam.training.filter;

/**
 * Created by Настенька on 11/12/2015.
 */
public class FilterException extends Exception {
    public FilterException(){}
    public FilterException(String message, Throwable exception) {
        super(message, exception);
    }
    public FilterException(String message) {
        super(message);
    }
    public FilterException(Throwable exception) {
        super(exception);
    }
}

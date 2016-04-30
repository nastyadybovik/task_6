package by.epam.training.controller.command;

public class CommandException extends Exception {
//constructors
    public CommandException(){}
    public CommandException(String message, Throwable exception) {
        super(message, exception);
    }
    public CommandException(String message) {
        super(message);
    }
    public CommandException(Throwable exception) {
        super(exception);
    }
}

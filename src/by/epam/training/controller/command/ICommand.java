package by.epam.training.controller.command;

import javax.servlet.http.HttpServletRequest;

public interface  ICommand {
	String execute(HttpServletRequest request) throws CommandException;
}

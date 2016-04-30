package by.epam.training.controller.command.impl;


import by.epam.training.controller.command.CommandException;
import by.epam.training.controller.command.ICommand;
import by.epam.training.service.ServiceException;
import by.epam.training.service.impl.LoginService;

import javax.servlet.http.HttpServletRequest;


public class LoginCommand implements ICommand {
	private static final String PARAMETR_LOGIN="login";
	private static final String PARAMETR_PASSWORD="password";
	private static final String TO_GO="/WEB-INF/jsp/main.jsp";
	private static final String ERROR_MESSAGE = "Login or(and) password is(are) empty.";

	@Override
	public String execute(HttpServletRequest request) throws CommandException {
		// validation request's parameters
		String login = request.getParameter(PARAMETR_LOGIN);
		String password = request.getParameter(PARAMETR_PASSWORD);
		String goTo = request.getParameter("page");
		System.out.println("Page: "+request.getParameter("page"));

		if( validateParameters(login, password) ){
			//invoke service method
			try {
				LoginService.getInstance().doService(request);
			} catch (ServiceException e) {
				throw new CommandException(e);
			}
			return TO_GO;
		} else {
			throw new CommandException(ERROR_MESSAGE);
		}
		

	}

	private static boolean validateParameters(String login, String password){
		if(!login.isEmpty() || !password.isEmpty()){
			return true;
		} else {
			return false;
		}
	}

}

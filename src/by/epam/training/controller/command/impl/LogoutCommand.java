package by.epam.training.controller.command.impl;

import by.epam.training.controller.command.CommandException;
import by.epam.training.controller.command.ICommand;
import by.epam.training.service.ServiceException;
import by.epam.training.service.impl.LoginService;
import by.epam.training.service.impl.LogoutService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Настенька on 11/6/2015.
 */
public class LogoutCommand implements ICommand {

    private static final String TO_GO="/index.jsp";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            LogoutService.getInstance().doService(request);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return TO_GO;
    }

}

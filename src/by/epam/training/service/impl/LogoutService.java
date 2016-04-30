package by.epam.training.service.impl;

import by.epam.training.service.IService;
import by.epam.training.service.ServiceException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Настенька on 11/6/2015.
 */
public class LogoutService implements IService{

    private static final LogoutService instance = new LogoutService();

    public static LogoutService getInstance(){
        return instance;
    }

    @Override
    public void doService(HttpServletRequest request) throws ServiceException {
        System.out.println("====================[ LOGOUT ]=========================");
        System.out.println("You call logout.");
        request.getSession(false).invalidate();
    }
}

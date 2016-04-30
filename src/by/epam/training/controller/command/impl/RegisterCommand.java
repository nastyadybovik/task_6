package by.epam.training.controller.command.impl;

import by.epam.training.controller.command.CommandException;
import by.epam.training.controller.command.ICommand;
import by.epam.training.service.ServiceException;
import by.epam.training.service.impl.LoginService;
import by.epam.training.service.impl.LogoutService;
import by.epam.training.service.impl.RegisterService;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

/**
 * Created by Настенька on 11/7/2015.
 */
public class RegisterCommand implements ICommand {

    private static final String PARAMETR_LOGIN="login";
    private static final String PARAMETR_PASSWORD="password";
    private static final String TO_GO="/WEB-INF/jsp/main.jsp";
    private static final String ERROR_MESSAGE = "Login or(and) password is(are) empty.";

    /*private static final String PARAMETR_FIRST_NAME = "first_name";
    private static final String PARAMETR_LAST_NAME = "last_name";
    private static final String PARAMETR_PATRONYMIC = "patronymic";
    private static final String PARAMETR_DATE_OF_BIRTH = "date_of_birth";
    private static final String PARAMETR_PASSSPORT_DATA = "passport_data";
    private static final String PARAMETR_ADRESS= "adress";
    private static final String PARAMETR_SCHOOL_SCORE= "school_score";
    private static final String PARAMETR_TEST1 = "tes1";
    private static final String PARAMETR_TEST2 = "test2";
    private static final String PARAMETR_TEST3 = "test3";*/


    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        // validation request's parameters

        //может лучше через Enumeration, но загонит в валидацию и page, action (то есть hidden поля)
        /*String login = request.getParameter(PARAMETR_LOGIN);
        String password = request.getParameter(PARAMETR_PASSWORD);*/

        boolean status = true;
        Enumeration<String> parameters = request.getParameterNames();
        String param = null;
        while(parameters.hasMoreElements()){
            param = parameters.nextElement();
            System.out.println("Parameters: "+param+" = "+request.getParameter(param));
            if (! validateParameters(param) ){
                status = false;
            }
        }


        if(status) {
            try {
                RegisterService.getInstance().doService(request);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            throw new CommandException(ERROR_MESSAGE);
        }

        return TO_GO;
    }

    private static boolean validateParameters(String string){
        if(!string.isEmpty()){
            return true;
        } else {
            return false;
        }
    }

}

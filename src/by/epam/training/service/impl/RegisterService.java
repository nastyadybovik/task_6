package by.epam.training.service.impl;

import by.epam.training.dao.DAOException;
import by.epam.training.dao.DAOFactory;
import by.epam.training.dao.UserDAO;
import by.epam.training.domain.User;
import by.epam.training.service.IService;
import by.epam.training.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Настенька on 11/7/2015.
 */
public class RegisterService implements IService {

    private static final RegisterService instance = new RegisterService();

    private static final String PARAMETR_LOGIN="login";
    private static final String PARAMETR_PASSWORD="password";
    private static final String ATTR_USER="user";
    private static final String COD="SHA-1";
    private static final String ERROR_MESSAGE="We have such user. Login is not free.";

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

    public static RegisterService getInstance(){
        return instance;
    }

    @Override
    public void doService(HttpServletRequest request) throws ServiceException {
        try {
            System.out.println("====================[ REGISTER ]=========================");
            String login = request.getParameter(PARAMETR_LOGIN);
            String password = getHash(request.getParameter(PARAMETR_PASSWORD));

            /*String first_name = request.getParameter(PARAMETR_FIRST_NAME);
            String last_name = request.getParameter(PARAMETR_LAST_NAME);
            String patronymic = request.getParameter(PARAMETR_PATRONYMIC);
            String date_of_birth = request.getParameter(PARAMETR_DATE_OF_BIRTH);
            String passport_data = request.getParameter(PARAMETR_PASSSPORT_DATA);
            String adress = request.getParameter(PARAMETR_ADRESS);
            String school_score = request.getParameter(PARAMETR_SCHOOL_SCORE);
            String test1 = request.getParameter(PARAMETR_TEST1);
            String test2 = request.getParameter(PARAMETR_TEST2);
            String test3 = request.getParameter(PARAMETR_TEST3);*/

            Map<String, String> parameters = new HashMap<>();

            Enumeration<String> parametersEnum = request.getParameterNames();
            String param = null;
            while(parametersEnum.hasMoreElements()){
                param = parametersEnum.nextElement();
                parameters.put(param, request.getParameter(param));
            }

            DAOFactory daoFactory = DAOFactory.getDAOFactory();
            UserDAO userDAO = daoFactory.getUserDAO();

            boolean status = userDAO.checkUser(login, password);

            if (!status) {
                System.out.println("Login is free.");
                User user = null;
                try {
                    userDAO.createUser(parameters);
                    user = userDAO.getUser(login, password);
                    request.getSession(true).setAttribute(ATTR_USER, user);
                } catch (DAOException e) {
                    throw new ServiceException(e);
                }

            } else {
                System.out.println("We have such user. Login is not free.");
                throw new ServiceException(ERROR_MESSAGE);
            }

        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private static String getHash( String password) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance(COD);
        StringBuffer  hexString = new StringBuffer();

        sha.reset();
        sha.update(password.getBytes());
        byte[] array = sha.digest();

        for (int i = 0; i < array.length; i++) {
            hexString.append(Integer.toHexString(0xFF & array[i]));
        }

        return hexString.toString();
    }

}

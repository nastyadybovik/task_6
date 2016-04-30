package by.epam.training.dao;

import by.epam.training.dao.impl.SQLUserDAO;

/**
 * Created by Настенька on 11/6/2015.
 */
public class DAOFactory {

    private static final DAOFactory daoFactory = new DAOFactory();

    public UserDAO getUserDAO(){
        return SQLUserDAO.getInstance();
    };

    public static DAOFactory getDAOFactory(){
        return daoFactory;
    }
}

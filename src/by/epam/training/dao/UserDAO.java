package by.epam.training.dao;


import by.epam.training.domain.User;

import java.util.Map;

public interface UserDAO {
	boolean checkUser(String login, String password) throws DAOException;
	User getUser(String login, String password) throws DAOException;
	boolean createUser(Map<String, String> parameters) throws DAOException;
	Long getID(String login, String password) throws DAOException;
}

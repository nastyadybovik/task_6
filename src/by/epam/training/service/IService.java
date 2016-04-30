package by.epam.training.service;

import javax.servlet.http.HttpServletRequest;

public interface IService {
	void doService(HttpServletRequest request) throws ServiceException;
}

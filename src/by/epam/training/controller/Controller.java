package by.epam.training.controller;

import by.epam.training.controller.command.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.plugin.com.Dispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LogManager.getLogger(Controller.class);

	private static final String PARAMETER_ACTION = "action";
	private static final String PARAMETER_PAGE = "page";
	private static final String PARAMETER_ERROR = "error";

    private ControllerHelper helper = new ControllerHelper();
	
	
    public Controller() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String goTo = null;
		try {
			goTo = helper.getCommand(request.getParameter(PARAMETER_ACTION)).execute(request);
			request.getRequestDispatcher(goTo).forward(request, response);
		} catch (CommandException e) {
			LOG.error( e.getMessage());
			System.out.println("Error in controller: "+e.getMessage());
			request.setAttribute(PARAMETER_ERROR, e.getMessage());
			System.out.println("Error page: "+request.getParameter(PARAMETER_PAGE));
			String errorPage = request.getParameter(PARAMETER_PAGE);
			request.getRequestDispatcher(errorPage).forward( request, response);
		}
	}

}

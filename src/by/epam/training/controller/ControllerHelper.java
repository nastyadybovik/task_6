package by.epam.training.controller;


import by.epam.training.controller.command.ICommand;
import by.epam.training.controller.command.impl.ChangeLocaleCommand;
import by.epam.training.controller.command.impl.LoginCommand;
import by.epam.training.controller.command.impl.LogoutCommand;
import by.epam.training.controller.command.impl.RegisterCommand;

import java.util.HashMap;
import java.util.Map;

public class ControllerHelper {
	private Map<CommandName, ICommand> commands = new HashMap<>();
	
	public ControllerHelper(){
		commands.put(CommandName.LOGIN, new LoginCommand());
		commands.put(CommandName.LOGOUT, new LogoutCommand());
		commands.put(CommandName.REGISTER, new RegisterCommand());
		commands.put(CommandName.EN, new ChangeLocaleCommand());
		commands.put(CommandName.RU, new ChangeLocaleCommand());
	}
	
	
	public ICommand getCommand(String commandName){
		CommandName name = CommandName.valueOf(commandName.toUpperCase());
		return commands.get(name);
	}
	
	
	enum CommandName{
		LOGIN, LOGOUT, REGISTER, EN, RU;
	}
}

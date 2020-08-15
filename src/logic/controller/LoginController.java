package logic.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import logic.bean.LoginBean;
import logic.entity.dao.LoginDAO;

public class LoginController {
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	public boolean checkAuthentication(LoginBean bean) {
		logger.log(Level.INFO, "Connecting...");
		return LoginDAO.getInstance().authetication(bean);
	}

}

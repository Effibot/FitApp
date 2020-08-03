package logic.controller;

import logic.bean.LoginBean;
import logic.entity.dao.LoginDAO;


import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	public boolean checkAuthentication(LoginBean bean) {
		logger.log(Level.INFO, "Connecting...");
		return LoginDAO.getInstance().authetication(bean);
	}

}

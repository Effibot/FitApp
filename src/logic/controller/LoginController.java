package logic.controller;

import logic.bean.LoginBean;
import logic.model.dao.LoginDAO;
import logic.model.dao.UserDAO;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {
    private final Logger logger = Logger.getLogger(getClass().getName());

    public LoginBean getBean() {
        return bean;
    }

    public void setBean(LoginBean bean) {
        this.bean = bean;
    }

    private LoginBean bean;

    public LoginController(LoginBean bean) {
        this.bean = bean;
    }

    public boolean checkAuthentication() {
        logger.log(Level.INFO, "Connecting...");
        return LoginDAO.getInstance().authetication(bean);
    }

    // Generates a random int with n digits
    public int generateRandomDigits(int n) {
        int m = (int) Math.pow(10, (double) n - 1);
        return m + new Random().nextInt(9 * m);
    }

    public void signUp() {
        UserDAO.getInstance().signUp(bean.getUsername(), bean.getPassword());
    }

    public boolean isSigningUp() {
        return bean.getUsername().toLowerCase().contentEquals("guest");
    }
}

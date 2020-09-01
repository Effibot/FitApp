package logic.controller;


import logic.bean.EmailBean;
import logic.emailutil.EmailSender;
import logic.factory.alertfactory.AlertFactory;
import logic.model.dao.GymDAO;
import logic.model.dao.ManagerDAO;
import logic.model.dao.UserDAO;
import logic.model.entity.Manager;
import logic.model.entity.User;

import javax.mail.MessagingException;

public class EmailController {
    private static EmailController instance = null;
    private EmailBean emailBean;
    private ManagerDAO gymManager = ManagerDAO.getInstance();
    private GymDAO gymDao = GymDAO.getInstance();
    private static UserDAO userDao = UserDAO.getInstance();
    private MainController ctrl = MainController.getInstance();
    private static final String EMAILVIEWCONTROLLER = "logic.viewcontroller.EmailViewController";
    private static final String LOGINVIEWCONTROLLER = "logic.viewcontroller.LoginViewController";
    private static final String FROMFITAPP = "From FitApp:";

    protected EmailController() {

        this.emailBean = new EmailBean();
    }

    public EmailBean getEmailBean() {
        return emailBean;
    }


    public static synchronized EmailController getSingletoneInstance() {
        if (EmailController.instance == null)
            EmailController.instance = new EmailController();
        return EmailController.instance;
    }

    public void sendEmail() {
        try {
            String object;
            String subject;
            EmailSender emailSender = new EmailSender();
            String className = new Exception().getStackTrace()[1].getClassName();

            if (className.equals(EMAILVIEWCONTROLLER)) {
                String gym = emailBean.getGym();
                String manager = gymDao.getManagerNameGymIdByName(gym);
                Manager managerUser = gymManager.getManagerEntity(Integer.parseInt(manager));
                String managerEmail = managerUser.getEmail();
                int userId = ctrl.getId();
                User user = userDao.getUserEntity(userId);
                String userEmail = user.getEmail();
                subject = FROMFITAPP + emailBean.getSubject();
                object = "Email sent from: " + user.getName() + " for event: " + emailBean.getEvent() + " object:\n" + emailBean.getMsg() + "\nTo reply write to:" + userEmail;
                emailSender.sendEmails(subject, object, managerEmail);
            } else if (className.equals(LOGINVIEWCONTROLLER)) {
                System.out.println(emailBean.getEmail());
                subject = FROMFITAPP + "REGISTRATION";
                object = "Hi, guest! In order to enjoy FitApp experience log in with:\nUser: guest \nPassword: " + emailBean.getPwd();
                emailSender.sendEmails(subject, object, emailBean.getEmail());
            }

        } catch (MessagingException e) {
            AlertFactory.getInstance().createAlert(e);
        }


    }
}

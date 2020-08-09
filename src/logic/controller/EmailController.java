package logic.controller;

import javax.mail.MessagingException;

import logic.bean.EmailBean;
import logic.emailutil.EmailSender;
import logic.entity.Manager;
import logic.entity.User;
import logic.entity.dao.GymDAO;
import logic.entity.dao.ManagerDAO;
import logic.entity.dao.UserDAO;

public class EmailController {
	private static EmailController instance = null;
	private EmailBean emailBean;
	private ManagerDAO gymManager = ManagerDAO.getInstance();
	private GymDAO gymDao = GymDAO.getInstance();
	private static UserDAO userDao = UserDAO.getInstance();
	private  MainController ctrl = MainController.getInstance(); 


	protected EmailController() {
	
		this.emailBean = new EmailBean();
	}
	
	public EmailBean getEmailBean() {
		return emailBean;
	}


	public static synchronized EmailController getSingletoneInstance() {
		if(EmailController.instance == null)
			EmailController.instance = new EmailController();
		return EmailController.instance;
	}

	public void sendEmail() {
		String txtToSend = emailBean.getMsg();
		String sbjToSend = emailBean.getSubject();
		String event = emailBean.getEvent();
		String gym = emailBean.getGym();
		String manager = gymDao.getManagerNameGymIdByName(gym);
		Manager managerUser = gymManager.getManagerEntity(Integer.parseInt(manager));
		String managerEmail = managerUser.getEmail();
		int userId = ctrl.getId();
		User user = userDao.getUserEntity(userId);
		String userName = user.getName();
		String emailUser = user.getEmail();
		EmailSender emailSender = EmailSender.getSingletonInstance(sbjToSend, txtToSend, emailUser,userName,event,managerEmail);
		try {
			emailSender.sendEmails();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
	}
}

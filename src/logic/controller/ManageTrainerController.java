package logic.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import logic.bean.GymPageBean;
import logic.entity.Trainer;
import logic.entity.dao.TrainerDAO;

public class ManageTrainerController {
	
	private TrainerDAO trainerDAO;
	private int gymId;
	private ObservableList<Trainer> trainerList;
	 
	public ManageTrainerController(GymPageBean bean) {
		trainerDAO = TrainerDAO.getInstance();
		setGymId(bean.getGymId());
		trainerList = adaptList();
	}
	
	
	private ObservableList<Trainer> adaptList() {
		ObservableList<Trainer> products = FXCollections.observableArrayList();
		for(Trainer t : trainerDAO.getTrainerList(gymId)) 
			products.add(t);
		return products;
	}

	public int getGymId() {
		return gymId;
	}


	public void setGymId(int gymId) {
		this.gymId = gymId;
	}


	public ObservableList<Trainer> getTrainerList() {
		return trainerList;
	}


	public void setTrainerList(ObservableList<Trainer> trainerList) {
		this.trainerList = trainerList;
	}
	
	public static boolean isAlpha(String s) {
		return (!s.trim().equals("")) && s.chars().allMatch(Character::isLetter);
	}
	
}

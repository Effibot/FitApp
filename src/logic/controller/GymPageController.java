package logic.controller;

import logic.model.dao.GymDAO;
import logic.model.dao.ManagerDAO;
import logic.model.dao.TrainerDAO;
import logic.model.entity.Gym;
import logic.model.entity.Manager;

public class GymPageController {
	
	private Manager manager;
	private Gym gym;
	
	public GymPageController(int id) {
		System.out.println(id);
		this.gym = GymDAO.getInstance().getGymEntityById(id);
		this.manager = ManagerDAO.getInstance().getManagerEntity(gym.getManagerId());
		this.gym.setTrainerList(TrainerDAO.getInstance().getTrainerList(gym.getGymId()));
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public Gym getGym() {
		return gym;
	}

	public void setGym(Gym gym) {
		this.gym = gym;
	}
	
}

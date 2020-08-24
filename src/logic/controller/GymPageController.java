package logic.controller;

import logic.entity.Gym;
import logic.entity.Manager;
import logic.entity.dao.GymDAO;
import logic.entity.dao.ManagerDAO;
import logic.entity.dao.TrainerDAO;

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

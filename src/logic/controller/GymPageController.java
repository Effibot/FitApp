package logic.controller;

import logic.entity.Gym;
import logic.entity.Manager;
import logic.entity.dao.GymDAO;
import logic.entity.dao.ManagerDAO;

public class GymPageController {
	
	private Manager manager;
	private Gym gym;
	
	public GymPageController(int id) {
		
		this.gym = GymDAO.getInstance().getGymEntity(id);
		this.manager = ManagerDAO.getInstance().getManagerEntity(gym.getManagerId());
		
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

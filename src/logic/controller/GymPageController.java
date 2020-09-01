package logic.controller;

import logic.bean.GymPageBean;
import logic.model.dao.GymDAO;
import logic.model.dao.ManagerDAO;
import logic.model.dao.TrainerDAO;
import logic.model.entity.Gym;
import logic.model.entity.Manager;

public class GymPageController {

    private Manager manager;
    private Gym gym;
    private GymPageBean bean;
    public GymPageController(GymPageBean bean) {
       // System.out.println(bean.getGymId());
        this.gym = GymDAO.getInstance().getGymEntity(bean.getUserId());
        this.manager = ManagerDAO.getInstance().getManagerEntity(gym.getManagerId());
        this.gym.setTrainerList(TrainerDAO.getInstance().getTrainerList(gym.getGymId()));
        bean.setGymId(this.gym.getGymId());
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

    public void setBean(GymPageBean bean) {
        this.bean = bean;
    }

    public GymPageBean getBean() {
        return bean;
    }
}

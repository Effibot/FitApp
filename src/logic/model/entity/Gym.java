package logic.model.entity;

import java.util.List;
import java.util.Map;

public class Gym {

    private int gymId;
    private String gymName;
    private String street;
    private int managerId;
    private String managerName;
    private Map<Integer, String> trainers;
    private Map<Integer, String> courses;

    private List<Trainer> trainerList;

    public Gym() {
    }

    public Gym(int gymId, String gymName, String street, int managerId, String managerName) {
        setGymId(gymId);
        setGymName(gymName);
        setManagerId(managerId);
        setManagerName(managerName);
        setStreet(street);
    }

    public int getGymId() {
        return gymId;
    }

    public void setGymId(int gymId) {
        this.gymId = gymId;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Map<Integer, String> getTrainers() {
        return trainers;
    }

    public void setTrainers(Map<Integer, String> trainers) {
        this.trainers = trainers;
    }

    public Map<Integer, String> getCourses() {
        return courses;
    }

    public void setCourses(Map<Integer, String> courses) {
        this.courses = courses;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public List<Trainer> getTrainerList() {
        return trainerList;
    }

    public void setTrainerList(List<Trainer> trainerList) {
        this.trainerList = trainerList;
    }
}
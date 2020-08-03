package logic.entity;

import java.util.Map;

public class Gym {

	private int gymId;
	private Manager manager;
	private String gymName;
	private String street;
	private Map<Integer, String> trainers;
	private Map<Integer, String> courses;

	public Gym() {
	}

	public Gym(int gymId, String gymName, Manager manager, String street) {
		setGymId(gymId);
		setGymName(gymName);
		setManager(manager);
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

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
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
}
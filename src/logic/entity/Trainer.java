package logic.entity;

import java.util.Map;

public class Trainer {
	private String name;
	private int trainerId;
	private int gymId;
	private Map<Course, Boolean> course;
	
	public Trainer(String name, int trainerId, int gymId, Map<Course, Boolean> course) {
		setName(name);
		setTrainerId(trainerId);
		setGymId(gymId);
		setCourse(course);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(int trainerId) {
		this.trainerId = trainerId;
	}

	public int getGymId() {
		return gymId;
	}

	public void setGymId(int gymId) {
		this.gymId = gymId;
	}

	public Map<Course, Boolean> getCourse() {
		return course;
	}

	public void setCourse(Map<Course, Boolean> course) {
		this.course = course;
	}
}

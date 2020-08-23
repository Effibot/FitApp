package logic.entity;

import java.sql.Date;
import java.sql.Time;

public class Session {
	private int sessionId;
	private String trainername;
	private int courseId;
	private boolean isPrivate;
	private Time timeStart;
	private Time timeEnd;
	private Date date;
	private String description;
	private String gym;
	private String courseName;
	private String street;

	private Time[] duration;
	private boolean individual;
	private String recurrence;

	public Session(String trainerName, String gym, Time[] duration, Date date, String description, int courseI,
			String street, boolean individual, String recurrence) {

		this.trainername = trainerName;
		this.gym = gym;
		this.duration = duration;
		this.timeEnd = duration[1];
		this.timeStart = duration[0];
		this.date = date;
		this.description = description;
		this.courseId = courseI;
		this.street = street;
		this.individual = individual;
		this.recurrence = recurrence;
	}

	public String getGym() {
		return gym;
	}

	public void setGym(String gym2) {
		this.gym = gym2;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public String getTrainername() {
		return trainername;
	}

	public void setTrainername(String trainername) {
		this.trainername = trainername;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public Time getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Time timeStart) {
		this.timeStart = timeStart;
	}

	public Time getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Time timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Time[] getDuration() {
		return duration;
	}

	public void setDuration(Time[] duration) {
		this.duration = duration;
	}

	public String printDuration(Time[] duration) {
		return duration[0].toString() + " - " + duration[1].toString();
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public boolean isIndividual() {
		return individual;
	}

	public void setIndividual(boolean individual) {
		this.individual = individual;
	}

	public String getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(String recurrence) {
		this.recurrence = recurrence;
	}
}

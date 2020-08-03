package logic.bean;

public class BookingOnMapBean {
	private String date;
	private String time;
	private String event;
	private double radius;
	
	public BookingOnMapBean() {
		//This is a bean class. Only getter and setter method are accepted
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
}

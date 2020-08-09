package logic.bean;


public class EmailBean {
	private String subject;
	private String msg;
	private String event;
	private String gym;
	
	

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public void setGym(String gymName) {
		this.gym = gymName;
	}
	public String getGym() {
		return gym;
	}
	

}

package logic.bean;


public class EmailBean {
    private String subject;
    private String msg;
    private String event;
    private String gym;
    private String email;
    private String pwd;


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

    public void setEmail(String email) {
        this.email = email;
        System.out.println(this.email);

    }

    public String getEmail() {
        System.out.println(email);

        return email;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }

}

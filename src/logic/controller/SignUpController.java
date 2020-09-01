package logic.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;
import logic.bean.SignUpBean;
import logic.model.dao.GymDAO;
import logic.model.dao.UserDAO;

public class SignUpController {

    public StringProperty setUpListener(TextField field) {
        StringProperty sp = new SimpleStringProperty(this, "sp", "");
        field.textProperty().bindBidirectional(sp);
        return sp;
    }

    public String retrieveEmail(int id) {
        return UserDAO.getInstance().getEmailById(id);
    }

    public boolean findNode(TextField field, String s) {
        return field.getId().equals(s);
    }

    public void registerUser(SignUpBean bean) {
        UserDAO.getInstance().registerUser(bean.getUsername(), bean.getPwd(), bean.getEmail(), bean.getIsManager(),
                bean.getUserStreet(), bean.getUserId());
        if (bean.getIsManager()) {
            GymDAO.getInstance().registerGym(bean.getGymName(), bean.getGymStreet(), bean.getUserId(),
                    bean.getUsername());
        }
    }
}

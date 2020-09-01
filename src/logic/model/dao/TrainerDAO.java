package logic.model.dao;

import logic.exception.DeleteException;
import logic.exception.InsertException;
import logic.factory.alertfactory.AlertFactory;
import logic.model.entity.Course;
import logic.model.entity.Trainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TrainerDAO extends ConnectionManager {
    private static TrainerDAO instance;

    private TrainerDAO() {
        super();
    }

    public static synchronized TrainerDAO getInstance() {
        if (TrainerDAO.instance == null) {
            TrainerDAO.instance = new TrainerDAO();
        }
        return TrainerDAO.instance;
    }

    public List<Trainer> getTrainerList(int gymId) {
        try {
            ResultSet rs = Query.getGymTrainers(this.st, gymId);
            if (checkMinRow(1, rs)) {
                String name;
                int trainerId;
                Map<Course, Boolean> course;
                List<Trainer> trainerList = new ArrayList<>();
                do {
                    name = rs.getString("trainer_name");
                    trainerId = rs.getInt("trainer_id");
                    course = new EnumMap<>(Course.class);
                    for (int i = 0; i < Course.values().length; i++)
                        course.put(Course.getCourse(i), rs.getBoolean(Course.getCourse(i).getCourseName()));
                    Trainer t = new Trainer(name, trainerId, gymId, course);
                    trainerList.add(t);
                } while (rs.next());
                return trainerList;
            }
        } catch (SQLException e) {
            AlertFactory.getInstance().createAlert(e);
        }
        return Collections.emptyList();
    }

    public void addTrainer(Trainer t) {
        try {
            int count = Query.addTrainer(this.st, t);
            if (count < 1) {
                throw new InsertException();
            }
        } catch (SQLException | InsertException e) {
            AlertFactory.getInstance().createAlert(e);
        }
    }

    public int getTrainerId(Trainer t) {
        try {
            ResultSet rs = Query.getTrainerId(this.st, t);
            if (checkResultValidity(1, 1, rs)) {
                return rs.getInt("trainer_id");
            }
        } catch (SQLException e) {
            AlertFactory.getInstance().createAlert(e);
        }
        return 0;
    }

    public void deleteTrainer(int trainerId) {
        try {
            int count = Query.deleteTrainer(this.st, trainerId);
            if (count < 1) {
                throw new DeleteException();
            }
        } catch (SQLException | DeleteException e) {
            AlertFactory.getInstance().createAlert(e);
        }
    }
}

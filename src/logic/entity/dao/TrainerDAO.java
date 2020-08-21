package logic.entity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import logic.entity.Course;
import logic.entity.Trainer;
import logic.factory.alertfactory.AlertFactory;

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
}

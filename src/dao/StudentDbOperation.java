package dao;
import java.util.List;

import models.Students;

public interface StudentDbOperation {
		public List<Students> getAllStudents();
		public Students getStudent(int id);
		public boolean insertStudent(Students student);
		public boolean updateStudent(Students student);
		public boolean deleteStudent(int id);
	}


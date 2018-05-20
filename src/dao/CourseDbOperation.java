package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Course;

public class CourseDbOperation {
	private final static String CON_STR = "jdbc:mysql://localhost:3306/demodb";
	private final static String USER = "user";
	private final static String PASSWORD = "pass";
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	private Connection con = null;
	CallableStatement cstmt = null;
	private ResultSet rs = null;

	
	private boolean createConnection() {
		try {
			Class.forName(CourseDbOperation.DRIVER);
			con = DriverManager.getConnection(CourseDbOperation.CON_STR, CourseDbOperation.USER,
					CourseDbOperation.PASSWORD);
			if (con == null)
				return false;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	private void closeConnection() {
		try {
			if (con != null)
				con.close();
			if (cstmt != null)
				cstmt.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public int countCourses() {
		int count = 0;
		createConnection();

		String SQL = "{call count_all_courses(?)}";
		try {
			cstmt = con.prepareCall(SQL);
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			cstmt.execute();
			count = cstmt.getInt("count");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return count;
	}

	
	public List<Course> getAllCourses() {
		createConnection();
		List<Course> courses = new ArrayList<>();
		try {
			cstmt = con.prepareCall("{call get_all_courses(1)}");
			boolean result = cstmt.execute();
			if (!result) {
				courses.add(new Course(999, "Error", "Data not found"));
			}
			rs = cstmt.getResultSet();
			while (rs.next()) {
				courses.add(new Course(rs.getInt("id"), rs.getInt("studentid"), rs.getInt("courseid"), rs.getString("name")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return courses;
	}

	
	public Course getCourse(int id) {
		createConnection();
		Course course = new Course();
		String SQL = "{call select_course_by_id(?,?,?)}";
		try {
			cstmt = con.prepareCall(SQL);
			cstmt.setInt(1, id);
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			cstmt.executeUpdate();
			course.setId(cstmt.getInt("var_id"));
			course.setStudentId(cstmt.getInt("var_studentid"));
			course.setCourseId(cstmt.getInt("var_courseid"));
            course.setName(cstmt.getString("var_name"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return course;
	}

	
	public boolean insertCourse(Course course) {
		createConnection();
		String SQL = "{call insert_course_record(?,?,?)}";
		try {
			cstmt = con.prepareCall(SQL);
			cstmt.setInt(1, course.getId());
			cstmt.setInt(2,  course.getStudentId());
			cstmt.setInt(3,  course.getCourseId());
			cstmt.setString(4,  course.getName());
			if (cstmt.executeUpdate() > 0)
				return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return false;
	}

	
	public boolean updateCourse(Course course) {
		createConnection();
		String SQL = "{call update_course_record(?,?,?,?)}";
		try {
			cstmt = con.prepareCall(SQL);
			cstmt.setInt(1, course.getId());
			cstmt.setInt(2,  course.getStudentId());
			cstmt.setInt(3,  course.getCourseId());
			cstmt.setString(4,  course.getName());
			if (cstmt.executeUpdate() > 0)
				return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return false;
	}


	public boolean deleteCourse(int id) {
		createConnection();
		String SQL = "{call delete_course(?)}";
		try {
			cstmt = con.prepareCall(SQL);
			cstmt.setInt(1, id);

			if (cstmt.executeUpdate() > 0)
				return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return false;
	}

}

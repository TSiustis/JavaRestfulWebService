package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Course;

public class CourseDb {
	private final static String CON_STR = "jdbc:mysql://localhost:3306/demodb";
	private final static String USER = "user";
	private final static String PASSWORD = "pass";
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	
	private final static String SQL_QRY_SELECT_ALL = "select * from COURSE order by id";
	private final static String SQL_QRY_SELECT_BY_ID = "select * from COURSE where id=?";
	private final static String SQL_QRY_INSERT = "INSERT INTO COURSE(id,studentid,courseid,name) values(?,?,?,?)";
	private static final String SQL_QRY_UPDATE = "UPDATE COURSE SET studentid = ?, courseid =?, name =? WHERE id=?";
	private static final String SQL_QRY_DELETE = "DELETE FROM COURSE WHERE id =?";
	private Connection con = null;
	private PreparedStatement pSt = null;
	private ResultSet rs = null;
	private Statement st = null;

	
	private boolean createConnection() {
		try {
			Class.forName(CourseDb.DRIVER);
			con = DriverManager.getConnection(CourseDb.CON_STR, CourseDb.USER, CourseDb.PASSWORD);
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
			if (pSt != null)
				pSt.close();
			if (st != null)
				st.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	
	public List<Course> getAllCourses() {
		List<Course> courses = new ArrayList<>();
		this.createConnection();
		try {
			st = con.createStatement();
			rs = st.executeQuery(CourseDb.SQL_QRY_SELECT_ALL);
			while (rs.next()) {
				Course course = new Course(rs.getInt("id"), rs.getInt("studentid"), rs.getInt("courseid"), rs.getString("name"));
				courses.add(course);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
		return courses;
	}

	
	public Course getCourse(int id) {
		this.createConnection();
		try {
			pSt = con.prepareStatement(CourseDb.SQL_QRY_SELECT_BY_ID);
			pSt.setInt(1, id);
			rs = pSt.executeQuery();
			while (rs.next()) {
				return  new Course(rs.getInt("id"), rs.getInt("studentid"), rs.getInt("courseid"), rs.getString("name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
		return null;
	}

	
	public boolean insertCourse(Course course) {
		this.createConnection();
		try {
			pSt = con.prepareStatement(CourseDb.SQL_QRY_INSERT);
			pSt.setInt(1, course.getId());
			pSt.setInt(2, course.getStudentId());
			pSt.setInt(3, course.getCourseId());
			pSt.setString(4, course.getName());
			if (pSt.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
		return false;
	}

	
	public boolean updateCourse(Course course) {
		this.createConnection();
		try {
			pSt = con.prepareStatement(CourseDb.SQL_QRY_INSERT);
			pSt.setInt(1, course.getId());
			pSt.setInt(2, course.getStudentId());
			pSt.setInt(3, course.getCourseId());
			pSt.setString(4, course.getName());
			if (pSt.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
		return false;
	}

	
	public boolean deleteCourse(int id) {
		this.createConnection();
		try {
			pSt = con.prepareStatement(CourseDb.SQL_QRY_DELETE);
			pSt.setInt(1, id);
			if (pSt.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
		return false;
	}


}


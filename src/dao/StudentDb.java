package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Student;
import models.Students;

public class StudentDb implements StudentDbOperation {

	private final static String CON_STR = "jdbc:mysql://localhost:3306/student";
	private final static String USER = "user";
	private final static String PASSWORD = "pass";
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	
	private final static String SQL_QRY_SELECT_ALL = "select * from student order by id";
	private final static String SQL_QRY_SELECT_BY_ID = "select * from student where id=?";
	private final static String SQL_QRY_INSERT = "INSERT INTO STUDENT(id,name,addr) values(?,?,?)";
	private static final String SQL_QRY_UPDATE = "UPDATE STUDENT SET addr = ?, name =? WHERE id=?";
	private static final String SQL_QRY_DELETE = "DELETE FROM STUDENT WHERE id =?";
	private Connection con = null;
	private PreparedStatement pSt = null;
	private ResultSet rs = null;
	private Statement st = null;

	
	private boolean createConnection() {
		try {
			Class.forName(StudentDb.DRIVER);
			con = DriverManager.getConnection(StudentDb.CON_STR, StudentDb.USER, StudentDb.PASSWORD);
			if (con == null)
				return false;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	// closing the database connection with statement and result-set
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

	
	@Override
	public List<Students> getAllStudents() {
		// gathering the all student from student table and return as list of
		// students
		List<Students> stuList = new ArrayList<>();
		this.createConnection();
		try {
			st = con.createStatement();
			rs = st.executeQuery(StudentDb.SQL_QRY_SELECT_ALL);
			while (rs.next()) {
				Students stu = new Student(rs.getInt("id"), rs.getString("name"), rs.getString("addr"));
				stuList.add(stu);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
		return stuList;
	}

	
	@Override
	public Students getStudent(int id) {
		this.createConnection();
		try {
			pSt = con.prepareStatement(StudentDb.SQL_QRY_SELECT_BY_ID);
			pSt.setInt(1, id);
			rs = pSt.executeQuery();
			while (rs.next()) {
				return new Student(rs.getInt("id"), rs.getString("name"), rs.getString("addr"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
		return null;
	}

	
	@Override
	public boolean insertStudent(Students student) {
		this.createConnection();
		try {
			pSt = con.prepareStatement(StudentDb.SQL_QRY_INSERT);
			pSt.setInt(1, student.getId());
			pSt.setString(2, student.getName());
			pSt.setString(3, student.getAddr());
			if (pSt.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
		return false;
	}

	
	@Override
	public boolean updateStudent(Students student) {
		this.createConnection();
		try {
			pSt = con.prepareStatement(StudentDb.SQL_QRY_UPDATE);
			pSt.setInt(3, student.getId());
			pSt.setString(2, student.getName());
			pSt.setString(1, student.getAddr());
			if (pSt.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
		return false;
	}

	
	@Override
	public boolean deleteStudent(int id) {
		this.createConnection();
		try {
			pSt = con.prepareStatement(StudentDb.SQL_QRY_DELETE);
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
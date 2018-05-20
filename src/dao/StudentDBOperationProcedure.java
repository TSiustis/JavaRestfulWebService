package dao;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Student;
import models.Students;


public class StudentDBOperationProcedure implements StudentDbOperation {
	private final static String CON_STR = "jdbc:mysql://localhost:3306/demodb";
	private final static String USER = "root";
	private final static String PASSWORD = "root";
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	// has a relationship with java.sql class
	private Connection con = null;
	CallableStatement cstmt = null;
	private ResultSet rs = null;

	/**
	 * This Method will create connection and assign to the Connection con
	 * instance
	 */
	private boolean createConnection() {
		try {
			Class.forName(StudentDBOperationProcedure.DRIVER);
			con = DriverManager.getConnection(StudentDBOperationProcedure.CON_STR, StudentDBOperationProcedure.USER,
					StudentDBOperationProcedure.PASSWORD);
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
			if (cstmt != null)
				cstmt.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public int countStudent() {
		int count = 0;
		createConnection();

		String SQL = "{call count_all_student(?)}";
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

	
	@Override
	public List<Students> getAllStudents() {
		createConnection();
		List<Students> stuList = new ArrayList<>();
		try {
			cstmt = con.prepareCall("{call get_all_student(1)}");
			boolean result = cstmt.execute();
			if (!result) {
				stuList.add(new Student(999, "Error", "Data not found"));
			}
			rs = cstmt.getResultSet();
			while (rs.next()) {
				stuList.add(new Student(rs.getInt("id"), rs.getString("name"), rs.getString("addr")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stuList;
	}

	
	@Override
	public Student getStudent(int id) {
		createConnection();
		Student stu = new Student();
		String SQL = "{call select_student_by_id(?,?,?)}";
		try {
			cstmt = con.prepareCall(SQL);
			cstmt.setInt(1, id);
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			cstmt.executeUpdate();
			stu.setId(cstmt.getInt("var_id"));
			stu.setName(cstmt.getString("var_name"));
			stu.setAddr(cstmt.getString("var_addr"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return stu;
	}

	
	@Override
	public boolean insertStudent(Students student) {
		createConnection();
		String SQL = "{call insert_student_record(?,?,?)}";
		try {
			cstmt = con.prepareCall(SQL);
			cstmt.setInt(1, student.getId());
			cstmt.setString(2, student.getName());
			cstmt.setString(3, student.getAddr());
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

	
	@Override
	public boolean updateStudent(Students student) {
		createConnection();
		String SQL = "{call update_student_record(?,?,?)}";
		try {
			cstmt = con.prepareCall(SQL);
			cstmt.setInt(1, student.getId());
			cstmt.setString(2, student.getName());
			cstmt.setString(3, student.getAddr());
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

	
	@Override
	public boolean deleteStudent(int id) {
		createConnection();
		String SQL = "{call delete_student_record(?)}";
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
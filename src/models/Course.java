package models;

public class Course {
	private int id;
	private int studentId;
	private int courseId;
	private int i;
	private String error, message;
	public Course(int id, int studentId, int courseId, String name) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.courseId = courseId;
		this.name = name;
	}
	public Course(int i, String error, String message) {
		this.i= i;
		this.error=error;
		this.message=message;
		
	}
	public Course() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String name;

}

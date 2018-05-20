package test;

import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.get;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import models.Course;
import models.Student;
//Using Rest Assured for validating Rest service http://rest-assured.io/
public class ResourceTest   {
	private static Student stu = new Student();
	private static Course course = new Course();
	@BeforeClass
	public static void init() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8081;
	}
	
	@Test 
	public void testUserFetchesSuccess() {

		stu.setId(10);
		stu.setName("John Doe");
		stu.setAddr("XYZ Street");
		course.setId(1);
		course.setName("Data Science");
		course.setStudentId(10);
		course.setCourseId(5);
		get("student/10")
		.then()
		.body("id", equalTo("10"))
		.body("name", equalTo("John Doe"))
		.body("addr", equalTo("XYZ Street"));
		get("/courses/5")
		.then()
		.body("id", equalTo("1"))
		.body("name", equalTo("DataScience"))
		.body("studentId", equalTo("10"))
		.body("courseId", equalTo("5"));
	}

}

package service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.CourseDb;
import models.Course;

public class CourseResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> getStudents() {
		CourseDb courseDb = new CourseDb();
		List<Course> courses = courseDb.getAllCourses();
		return courses;
	}

	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course getStudent(@PathParam("id") int id) {
		Course course = new CourseDb().getCourse(id);
		return course;
	}

	
	@GET
	@Path("/size")
	@Produces(MediaType.APPLICATION_JSON)
	public String totalCountStudent() {
		int count = new CourseDb().getAllCourses().size();
		return "{\"TotalCourses\":\"" + count + "\"}";
	}

	
	@GET
	@Path("/size")
	@Produces(MediaType.APPLICATION_XML)
	public String totalCountStudentXML() {
		int count = new CourseDb().getAllCourses().size();
		return "<Course><TotalCourse>" + count + "</TotalCourses></Course>";
	}

	
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String insertCourses(@FormParam("id") int id, @FormParam("studentid") int studentid,
			@FormParam("courseid") int courseid, @FormParam("name") String name) {
		Course course = new Course(id, studentid, courseid, name);
		if (new CourseDb().insertCourse(course)) {
			return "{\"msg\":\"course inserted\"}";
		}
		return "{\"msg\":\"course not inserted\"}";
	}

	
	@Path("/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String updateCourse(@FormParam("id") int id, @FormParam("studentid") int studentid,
			@FormParam("courseid") int courseid, @FormParam("name") String name) {
		Course course = new Course(id, studentid, courseid, name);
		if (new CourseDb().insertCourse(course)) {
			return "{\"msg\":\"course updated\"}";
		}
		return "{\"msg\":\"course not updated\"}";
	}

	
	@Path("/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String deleteStudent(@PathParam("id") int id) {
		if (new CourseDb().deleteCourse(id)) {
			return "{\"msg\":\"course deleted\"}";
		}
		return "{\"msg\":\"course not deleted\"}";
	}

}

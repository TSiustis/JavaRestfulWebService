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
import dao.StudentDb;
import dao.StudentDbOperation;
import models.Student;
import models.Students;

@Path("/students")
public class StudentResource implements StudentService {
	
	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Students> getStudents() {
		StudentDbOperation studentDBOperation = new StudentDb();
		List<Students> stuList = studentDBOperation.getAllStudents();
		return stuList;
	}

	
	@Override
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Students getStudent(@PathParam("id") int id) {
		Students stu = new StudentDb().getStudent(id);
		return stu;
	}

	
	@Override
	@GET
	@Path("/size")
	@Produces(MediaType.APPLICATION_JSON)
	public String totalCountStudent() {
		int count = new StudentDb().getAllStudents().size();
		return "{\"TotalStudent\":\"" + count + "\"}";
	}

	
	@Override
	@GET
	@Path("/size")
	@Produces(MediaType.APPLICATION_XML)
	public String totalCountStudentXML() {
		int count = new StudentDb().getAllStudents().size();
		return "<Student><TotalStudent>" + count + "</TotalStudent></Student>";
	}

	
	@Override
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String insertStudents(@FormParam("id") int id, @FormParam("name") String name,
			@FormParam("addr") String addr) {
		Students stu = new Student(id, name, addr);
		if (new StudentDb().insertStudent(stu)) {
			return "{\"msg\":\"student inserted\"}";
		}
		return "{\"msg\":\"student not inserted\"}";
	}

	
	@Override
	@Path("/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String updateStudent(@PathParam("id") int id, @FormParam("name") String name,
			@FormParam("addr") String addr) {
		Students stu = new Student(id, name, addr);
		if (new StudentDb().updateStudent(stu)) {
			return "{\"msg\":\"student updated\"}";
		}
		return "{\"msg\":\"student not updated\"}";
	}

	
	@Override
	@Path("/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String deleteStudent(@PathParam("id") int id) {
		if (new StudentDb().deleteStudent(id)) {
			return "{\"msg\":\"student deleted\"}";
		}
		return "{\"msg\":\"student not deleted\"}";
	}

}
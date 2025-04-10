package in.ashokit.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import in.ashokit.entity.CourseEntity;
import in.ashokit.entity.StatusEntity;
import in.ashokit.repository.CourseRepo;
import in.ashokit.repository.IStatusRepo;
//@Component
public class LoadDataRunner implements CommandLineRunner {

	@Autowired
	private CourseRepo cRepo;
	@Autowired
	private IStatusRepo sRepo;
	@Override
	public void run(String... args) throws Exception {
		
		CourseEntity c1=new CourseEntity();
		c1.setCourseName("Java");c1.setCourseId(10);
		CourseEntity c2=new CourseEntity();
		c2.setCourseName("Adv Java");c1.setCourseId(20);
		CourseEntity c3=new CourseEntity();
		c3.setCourseName("Spring Boot");c1.setCourseId(30);
		CourseEntity c4=new CourseEntity();
		c4.setCourseName("Micro Services");c1.setCourseId(40);
		cRepo.saveAll(Arrays.asList(c1,c2,c3,c4));
		
		
		StatusEntity s1=new StatusEntity();
		s1.setStatusName("New");s1.setStatusId(100);
		StatusEntity s2=new StatusEntity();
		s2.setStatusName("Enrolled");s2.setStatusId(101);
		StatusEntity s3=new StatusEntity();
		s3.setStatusName("Lost");s3.setStatusId(102);
		sRepo.saveAll(Arrays.asList(s1,s2,s3));

	}

}

package in.ashokit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.ashokit.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer>{

	@Query("SELECT distinct(courseName) FROM CourseEntity")
	public List<String> getCourses();
}

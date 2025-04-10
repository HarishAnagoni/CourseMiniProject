package in.ashokit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="Course_Details")
@NoArgsConstructor
@AllArgsConstructor
public class CourseEntity {

	@Id
	private Integer courseId;
	private String courseName;
}

package in.ashokit.entity;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Student_Details")
@AllArgsConstructor
@NoArgsConstructor
public class StudentEnqEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer stdId;
	private String name;
	private String course;
	private String Status;
	private String phno;
	private String mode;
	private LocalDate createDt;
	private LocalDate updateDt;
	
@ManyToOne(targetEntity = UserEntity.class,cascade = CascadeType.ALL)	
	@JoinColumn(name = "UserId",referencedColumnName = "userId")
	private UserEntity user;
}

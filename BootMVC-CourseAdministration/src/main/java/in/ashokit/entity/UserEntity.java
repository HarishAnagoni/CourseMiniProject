package in.ashokit.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@Entity
@Table(name="User_Details")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
	@Id
	@GeneratedValue(generator = "seq",strategy = GenerationType.AUTO)
	private Integer userId;
	private String name;
	private String email;
	private String pwd;
	private Long phno;
	private String accStatus;
@OneToMany(targetEntity = StudentEnqEntity.class,
fetch = FetchType.EAGER,cascade = CascadeType.ALL, mappedBy ="user")
	private List<StudentEnqEntity> enquires;

}

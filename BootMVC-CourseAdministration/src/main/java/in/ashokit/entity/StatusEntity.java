package in.ashokit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="Status_List")
public class StatusEntity {
	@Id
	private Integer statusId;
	private String statusName;
}

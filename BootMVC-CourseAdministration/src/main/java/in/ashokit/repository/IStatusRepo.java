package in.ashokit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.ashokit.entity.StatusEntity;

public interface IStatusRepo extends JpaRepository<StatusEntity, Integer>{

	@Query("select distinct(statusName) from StatusEntity")
	public List<String> getStatuses();
}

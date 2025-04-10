package in.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.StudentEnqEntity;

public interface IEnquireRepo extends JpaRepository<StudentEnqEntity, Integer>{
}

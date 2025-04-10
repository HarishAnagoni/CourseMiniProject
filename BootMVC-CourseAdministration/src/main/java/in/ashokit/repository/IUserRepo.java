package in.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.UserEntity;

public interface IUserRepo extends JpaRepository<UserEntity, Integer>{
	public UserEntity findByEmail(String email);
	public UserEntity findByEmailAndPwd(String email,String pwd);
}

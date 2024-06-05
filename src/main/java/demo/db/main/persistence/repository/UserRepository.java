package demo.db.main.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.db.main.persistence.domain.UserDAO;

public interface UserRepository extends JpaRepository<UserDAO, Integer> {
	
	
}
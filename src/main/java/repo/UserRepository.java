package repo;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.User;

public interface UserRepository  extends JpaRepository<User, Long> {

}


package fitness.tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fitness.tracker.entity.User;

public interface UserDao extends JpaRepository<User, Long> {

}

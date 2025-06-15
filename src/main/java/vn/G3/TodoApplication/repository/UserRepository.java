package vn.G3.TodoApplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.G3.TodoApplication.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	public boolean existsByUsername(String username);

	Optional<User> findByUsername(String name);

	List<User> findByRole(String role);
}

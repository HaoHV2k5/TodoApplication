package vn.G3.TodoApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.G3.TodoApplication.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

}

package com.app.repository;

import com.app.models.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Kulinenko Roman
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM user WHERE id = ?1", nativeQuery = true)
    public User findById(Integer id);

}

package com.app.repository;

import com.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Class UserRepository
 * Repository that holds all user's requests to the database
 *
 * Created by Kulinenko Roman
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM user WHERE id = ?1", nativeQuery = true)
    public User findById(Integer id);

}

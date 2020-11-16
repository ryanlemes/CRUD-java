package com.crud.crudjava.repository;

import com.crud.crudjava.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Select to find some user by the user Id.
     * Verify if the user is not deleted based on the deleted_at property.
     * @param id Lonfg user id
     * @return if exists, some user object.
     */
    Optional<User> findByIdAndDeletedAtIsNull(Long id);

    /**
     * Select all the active user's on the database
     * @return a list of active users.
     */
    List<User> findByDeletedAtIsNull();

    /**
     * Verify if exists some Name on the database.
     * @param name string with the user's name.
     * @return a boolean with true for exists the name and false otherwise.
     */
    boolean existsByName(String name);
}

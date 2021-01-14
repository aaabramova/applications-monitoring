package com.abramova.applications.monitoring.repositories;

import com.abramova.applications.monitoring.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepo extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);
    User findByUsername(String username);

}

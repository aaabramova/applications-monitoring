package com.abramova.applications.monitoring.repositories;

import com.abramova.applications.monitoring.entities.Application;
import com.abramova.applications.monitoring.entities.User;
import com.abramova.applications.monitoring.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ApplicationRepo extends JpaRepository<Application, Long> {

    Page<Application> findAll(Pageable pageable);
    Page<Application> findAllByAuthor(User user, Pageable pageable);
    Page<Application> findAllByStatus(Status status, Pageable pageable);
}

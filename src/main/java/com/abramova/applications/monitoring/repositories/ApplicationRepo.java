package com.abramova.applications.monitoring.repositories;

import com.abramova.applications.monitoring.entities.Application;
import com.abramova.applications.monitoring.exceptions.AccessApplicationException;
import com.abramova.applications.monitoring.exceptions.ApplicationNotFoundException;
import com.abramova.applications.monitoring.Status;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class ApplicationRepo {

    @PersistenceContext
    private EntityManager entityManager;

    private final int count = 10;

    public List<Application> getAll(int pageNumber) {
        return entityManager
                .createQuery("from Application a order by a.dateOfCreation desc", Application.class)
                .setFirstResult(getFirstValue(pageNumber))
                .setMaxResults(count)
                .getResultList();
    }

    public List<Application> getAllFromUser(UserDetails userDetails, int pageNumber) {
        return entityManager
                .createQuery("from Application a where a.author.username = :username order by a.dateOfCreation desc")
                .setParameter("username", userDetails.getUsername())
                .setFirstResult(getFirstValue(pageNumber))
                .setMaxResults(count)
                .getResultList();
    }

    public List<Application> getAllWithStatus(Status status, int pageNumber) {
        return entityManager
                .createQuery("from Application a where a.status = :status order by a.dateOfCreation desc")
                .setParameter("status", status)
                .setFirstResult(getFirstValue(pageNumber))
                .setMaxResults(count)
                .getResultList();
    }

    private int getFirstValue(int pageNumber) {
        return (pageNumber - 1) * count;
    }

    public Application getById(long id) {
        Application application = entityManager.find(Application.class, id);
        if(application == null) {
            throw new ApplicationNotFoundException("Application with id = " + id + " not found.");
        }
        return application;
    }

    public Application create(Application application) {
        application.setDateOfCreation(LocalDateTime.now());

        entityManager.persist(application);
        return application;
    }

    public Application update(long id, String text) {
        Application application = entityManager.find(Application.class, id);
        if(application == null) {
            throw new ApplicationNotFoundException("Application with id = " + id + " not found.");
        }
        if(application.getStatus() != Status.DRAFT) {
            throw new AccessApplicationException("Application status does not allow updating."); //&&&&&&&&&&&&&&&&&&&&&&
        }
        application.setDateOfCreation(LocalDateTime.now());
        application.setText(text);
        entityManager.merge(application);
        return application;
    }

    public Application draftToSent(long id, String text) {
        Application application = this.update(id, text);

        application.setDateOfCreation(LocalDateTime.now());
        application.setStatus(Status.SENT);
        entityManager.merge(application);

        return application;
    }

    public Application setStatus(long id, Status status) {
        Application application = entityManager.find(Application.class, id);
        if(application == null) {
            throw new ApplicationNotFoundException("Application with id = " + id + " not found.");
        }
        application.setStatus(status);
        entityManager.merge(application);
        return application;
    }

    public void delete(long id) {
        Application application = entityManager.find(Application.class, id);
        if(application == null) {
            throw new ApplicationNotFoundException("Application with id = " + id + " not found.");
        }
        entityManager.remove(application);
    }
}

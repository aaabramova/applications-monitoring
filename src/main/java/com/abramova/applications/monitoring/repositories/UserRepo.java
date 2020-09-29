package com.abramova.applications.monitoring.repositories;

import com.abramova.applications.monitoring.entities.User;
import com.abramova.applications.monitoring.exceptions.UserNotFoundException;
import com.abramova.applications.monitoring.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class UserRepo {

    @PersistenceContext
    private EntityManager entityManager;

    private final int count = 5;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getAll(int pageNumber) {
        return entityManager
                .createQuery("from User u", User.class)
                .setFirstResult((pageNumber - 1) * count)
                .setMaxResults(count)
                .getResultList();
    }

    public User getById(long id) {
        User user = entityManager.find(User.class, id);
        if(user == null) {
            throw new UserNotFoundException("User with id = " + id + " not found.");
        }
        return user;
    }

    public User getByUsername(String username) {
        Query query = entityManager.createQuery("from User u where u.username = :username");
        query.setParameter("username", username);
        User user = (User) query.getSingleResult();
        if(user == null) {
            throw new UserNotFoundException("User with username = " + username + " not found.");
        }
        return user;
    }

    public User updateUserRole(long id, Role role) { //mb proverka if user already is operator
        User user = entityManager.find(User.class, id);

        Set<Role> userRoles = user.getRoles();
        userRoles.add(role);
        user.setRoles(userRoles);

        entityManager.merge(user);

        return user;
    }
}

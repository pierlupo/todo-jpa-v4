package org.example.impl;


import org.example.dao.TodoDAO;
import org.example.entity.Todo;
import org.example.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class TodoDAOImpl implements TodoDAO {

    private EntityManagerFactory entityManagerFactory;

    public TodoDAOImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public boolean addTodo(Todo todo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(todo);
            transaction.commit();
            return true;
        }catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }finally {
            entityManager.close();
        }
    }

    public boolean addTodo(Todo todo, Long userId){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        User user = entityManager.find(User.class, userId);
        todo.setUser(user);
        user.getTodos().add(todo);
        entityManager.persist(todo);
        transaction.commit();
        entityManager.close();
        return true;
    }

    @Override
    public List<Todo> getAllTodos() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Todo> todos = entityManager.createQuery("Select t from Todo t",Todo.class).getResultList();
        entityManager.close();
        return todos;
    }


    @Override
    public boolean deleteTodo(Long todoId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Todo todo = entityManager.find(Todo.class,todoId);
            if(todo != null){
                entityManager.remove(todo);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }finally {
            entityManager.close();
        }
    }

    @Override
    public boolean markTodoAsCompleted(Long todoId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Todo todo = entityManager.find(Todo.class,todoId);
            if(todo != null){
                todo.setCompleted(true);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }finally {
            entityManager.close();
        }
    }

    @Override
    public List<Todo> getTodoByUserId(Long userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Todo> todos = entityManager.createQuery("SELECT t FROM Todo t WHERE t.user.id = :userId ")
                .setParameter("userId", userId)
                .getResultList();
                return todos;
    }
}
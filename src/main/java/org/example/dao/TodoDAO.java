package org.example.dao;




import org.example.entity.Todo;

import java.util.List;

public interface TodoDAO {

        public boolean addTodo(Todo todo);

        public List<Todo> getAllTodos();

        public boolean deleteTodo(Long todoId);

        public boolean markTodoAsCompleted(Long todoId);

        public List<Todo> getTodoByUserId(Long userId);
    }


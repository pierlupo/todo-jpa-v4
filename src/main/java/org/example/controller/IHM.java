package org.example.controller;


import org.example.dao.CategoryDAO;
import org.example.entity.Category;
import org.example.entity.Todo;
import org.example.entity.TodoInfo;
import org.example.entity.User;
import org.example.impl.CategoryDAOImpl;
import org.example.impl.TodoDAOImpl;
import org.example.impl.UserDAOImpl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class IHM {

    static Scanner scanner;

    public IHM() {
        scanner = new Scanner(System.in);
    }

    private static EntityManagerFactory entityManagerFactory;
    private static TodoDAOImpl todoDAO;
    private static UserDAOImpl userDAO;
    private static CategoryDAOImpl categoryDAO;

    public static void start() {
        String choice;
        entityManagerFactory = Persistence.createEntityManagerFactory("todolist");
        todoDAO = new TodoDAOImpl(entityManagerFactory);
        userDAO = new UserDAOImpl(entityManagerFactory);
        categoryDAO = new CategoryDAOImpl(entityManagerFactory);
        do {
            menu();
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addUser();
                    break;
                case "2":
                    deleteUser();
                    break;
                case "3":
                    displayUsers();
                    break;
                case "4":
                    displayUsersTodos();
                    break;
                case "5":
                    addTodo();
                    break;
                case "6":
                   // getTodoByIdAction();
                    break;
                case "7":
                    displayTodos();
                    break;
                case "8":
                    deleteTodo();
                    break;
                case "9":
                    markTodoAsCompleted();
                    break;
                case "10":
                    addCategory();
                    break;
                case "11":
                    deleteCategory();
                    break;
                case "12":
                    addCategoryToTodo();
                    break;
                case "13":
                    deleteCategoryToTodo();
                    break;
                case "14":
                    showAllTodosOfCategory();
                    break;
                case "0":
                    System.out.println("Bye bye");
                    break;
                default:
                    System.out.println("Invalid choice, try again please.");
            }
        }while (!choice.equals("0"));
        entityManagerFactory.close();
    }
    private static void menu() {
        System.out.println("###################################");
        System.out.println("WELCOME TO A NEW TODO APP WITH JPA");
        System.out.println("###################################");
        System.out.println("*********************");
        System.out.println("Choose an option :");
        System.out.println("*********************");
        System.out.println("1 - Add a user ");
        System.out.println("2 - Delete a user ");
        System.out.println("3 - Display all users ");
        System.out.println("4 - Display todos of a user ");
        System.out.println("5 - Add a todo");
        System.out.println("6 - Display one todo ");
        System.out.println("7 - Display all todos ");
        System.out.println("8 - Delete todo");
        System.out.println("9 - Change status of a todo");
        System.out.println("10 - Add a new category");
        System.out.println("11 - Delete a category");
        System.out.println("12 - Add a todo to a category");
        System.out.println("13 - Remove a todo from a category");
        System.out.println("14 - Show all todos of a category");
        System.out.println("0 - Quit");
    }
    private static void addUser(){

        System.out.println("Enter your username : ");
        String username = scanner.nextLine();
        User user = new User(username);
        //user.setUserName(username);
        if(userDAO.addUser(user)){
            System.out.println("User successfully added !");
        }else {
            System.out.println("Error while trying to add a user ");
        }
    }

    private static void deleteUser(){
        System.out.println("Enter the ID of the user you want to delete : ");
        Long userId  = scanner.nextLong();
        scanner.nextLine();
        if (userDAO.deleteUser(userId)){
            System.out.println("User deleted");
        }else {
            System.out.println("Error while trying to delete the user");
        }
    }

    private static void displayUsers() {
        List<User> users = userDAO.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No Users Found.");
        } else {
            System.out.println("### List of Users ###");
            for (User user : users) {
                System.out.println("############");
                System.out.println(user.getId() + ". " + user.getUserName());
                System.out.println("############");
            }
        }
    }
    private static void addCategory(){

        System.out.println("Enter a new category : ");
        String categoryName = scanner.nextLine();
        Category category = new Category(categoryName);
        if(categoryDAO.addCategory(category)){
            System.out.println("Category successfully added !");
        }else {
            System.out.println("Error while trying to add a category ");
        }
    }

    private static void deleteCategory(){
        System.out.println("Enter the ID of the category you want to delete : ");
        Long categoryId  = scanner.nextLong();
        //scanner.nextLine();
        if (categoryDAO.deleteCategory(categoryId)){
            System.out.println("Category deleted");
        }else {
            System.out.println("Error while trying to delete a category");
        }
    }
    private static void addCategoryToTodo(){
        System.out.print("Enter the ID of the todo  : ");
        Long todoId = scanner.nextLong();
        scanner.nextLine(); // Consomme la nouvelle ligne
        System.out.print("Enter the ID of the category  : ");
        Long categoryId = scanner.nextLong();
        scanner.nextLine(); // Consomme la nouvelle ligne
        Category category = categoryDAO.getCategoryById(categoryId);
        System.out.println(category.getCategoryTitle());
        Todo todo = todoDAO.getTodoById(todoId);
        System.out.println(todo.getTitle());
        categoryDAO.addTodoToCategory(todo,category);

    }

    private static void deleteCategoryToTodo(){
        System.out.print("Enter the ID of the todo  : ");
        Long todoId = scanner.nextLong();
        scanner.nextLine(); // Consomme la nouvelle ligne
        System.out.print("Enter the ID of the category  : ");
        Long categoryId = scanner.nextLong();
        scanner.nextLine(); // Consomme la nouvelle ligne
        Category category = categoryDAO.getCategoryById(categoryId);
        Todo todo = todoDAO.getTodoById(todoId);
        categoryDAO.removeTodoFromCategory(todo,category);
    }

    private static void showAllTodosOfCategory(){
        System.out.println("Enter the ID of the category you want to display the todos :");
        Long categoryId = scanner.nextLong();
        String nameCategory = categoryDAO.getCategoryName(categoryId);
        Category category = categoryDAO.getCategoryById(categoryId);
        List<Todo> todos = categoryDAO.getTodosByCategory(category);
        System.out.println("Name of the category : "+ nameCategory +" with the ID " + categoryId + ":");
        for (Todo t : todos) {
            System.out.println("- "+ t.getUser().getUserName()+ " " + t.getTitle()+ " "+ t.getTodoInfo().toString() + (t.isCompleted() ? " (completed)" : "(active)"));
        }

    }
    private static void displayUsersTodos() {
        System.out.println("Enter the ID of the user : ");
        Long userId  = scanner.nextLong();
        scanner.nextLine();
        List<Todo> todos = todoDAO.getTodoByUserId(userId);
        System.out.println("Todo(s) of the user with id : "+ userId + " : ");
        for (Todo t : todos){
            System.out.println(" -"+ t.getId() + ". " + t.getUser() +" , "  + t.getTitle() + " (" + (t.isCompleted() ? "Terminée" : "En cours") + ")");
        }

    }

    private static void addTodo(){

        System.out.println("Enter the title of the todo : ");
        String title = scanner.nextLine();

        System.out.println("Enter the description of the todo : ");
        String desc = scanner.nextLine();

        System.out.println("Enter the duedate of the todo (as such : dd.MM.yyyy) : ");
        String duedateStr = scanner.nextLine();
        LocalDate duedate = LocalDate.parse(duedateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        System.out.println("Enter the priority of the todo : ");
        int priority = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter the ID of the user for this todo : ");
        Long userId  = scanner.nextLong();
        scanner.nextLine();


        Todo todo = new Todo();
        todo.setTitle(title);
        //todo.setCompleted(false);

        //création de la todoinfo:
        TodoInfo todoInfo = new TodoInfo(desc, duedate, priority);


        //mise en relation avec user
        todo.setTodoInfo(todoInfo);
        todoInfo.setTodo(todo);

        if(todoDAO.addTodo(todo, userId)){
            System.out.println("Todo successfully added !");
        }else {
            System.out.println("Error while trying to add a todo ");
        }
    }

    private static void displayTodos() {
        List<Todo> todos = todoDAO.getAllTodos();

        if (todos.isEmpty()) {
            System.out.println("No Todos Found.");
        } else {
            System.out.println("### List of Todos ###");
            for (Todo todo : todos) {
                System.out.println("############");
                System.out.println(todo.getId() + ". " + todo.getTitle() + " (" + (todo.isCompleted() ? "Completed" : "Active") + ")");
//                System.out.println(todo.getTodoInfo().toString());
                System.out.println("############");
            }
        }
    }

    private static void deleteTodo(){
        System.out.println("Enter the ID of the todo you want to delete : ");
        Long todoId  = scanner.nextLong();
        scanner.nextLine();

        if (todoDAO.deleteTodo(todoId)){
            System.out.println("Todo deleted");
        }else {
            System.out.println("Error while trying to delete the todo");
        }
    }

    private static void markTodoAsCompleted(){
        System.out.println("Enter the ID of the todo you want to update : ");
        Long todoId  = scanner.nextLong();
        scanner.nextLine();

        if (todoDAO.markTodoAsCompleted(todoId)){
            System.out.println("Todo updated");
        }else {
            System.out.println("Error while trying to update the todo");
        }
    }

//    private void addTodoAction() {
//        System.out.println("Enter the todo title you want to add : ");
//        String title = scanner.nextLine();
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        Todo todo = new Todo(title);
//        em.persist(todo);
//        em.getTransaction().commit();
//        em.close();
//        emf.close();
//    }

//    private void getTodoByIdAction() {
//        System.out.println("Enter the id of the todo you want to display : ");
//        Long id = scanner.nextLong();
//        scanner.nextLine();
//        EntityManager em = emf.createEntityManager();
//        Todo todo = em.find(Todo.class, id);
//        System.out.println(todo.toString());
//        em.close();
//        emf.close();
//    }

//    private void getAllTodosAction(){
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        List<Todo> todoList = null;
//        todoList = em.createQuery("select t from Todo t", Todo.class).getResultList();
//        for(Todo t:todoList){
//            System.out.println(t);
//        }
//        em.getTransaction().commit();
//        em.close();
//        emf.close();
//    }

//    private void deleteToDoAction(){
//        System.out.println("Enter the id of the todo to delete : ");
//        Long id = scanner.nextLong();
//        scanner.nextLine();
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        Todo todo = em.find(Todo.class, id);
//        em.remove(todo);
//        em.getTransaction().commit();
//        System.out.println("todo deleted");
//        em.close();
//        emf.close();
//    }

//    private void ChangeToDoStatus(){
//        System.out.println("Enter the id of the todo you want to change status : ");
//        Long id = scanner.nextLong();
//        scanner.nextLine();
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        Todo todo = em.find(Todo.class, id);
//        em.setStatus(todo);
//        em.getTransaction().commit();
//        System.out.println("todo status changed");
//        em.close();
//        emf.close();
//    }

}

package com.mycompany.to.dolistapplication;

import java.util.ArrayList;

public class TaskManager {
    private ArrayList<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(String title, String description) {
        tasks.add(new Task(title, description));
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    public void markTaskAsCompleted(String title) throws ToDoException {
        for (Task task : tasks) {
            if (task.getTitle().equalsIgnoreCase(title)) {
                task.markAsCompleted();
                return;
            }
        }
        throw new ToDoException("Task not found!");
    }
}

package com.example.demo.controller;


import com.example.demo.application.TaskService;
import com.example.demo.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/task")
    public String mainTask(@RequestParam(required = false) String filter, Model model) {
        Iterable<Task> tasks = taskService.all();
        if (filter != null && !filter.isEmpty()) {
            tasks = taskService.findByName(filter);
        } else {
            tasks = taskService.all();
        }
        model.addAttribute("tasks", tasks);
        model.addAttribute("filter", filter);
        return "task";
    }
    @PostMapping("/task")
    public String addTask(@RequestParam String name,
                          @RequestParam String phoneNumber,
                          @RequestParam String description,
                          @RequestParam Integer price,
                          Map<String, Object> model) {
        Task task = new Task(name, phoneNumber, description, price);
        taskService.createTask(task);
        Iterable<Task> tasks = taskService.all();
        model.put("tasks", tasks);
        return "task";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/deleteTask")
    public String delTask(@RequestParam(required = false) long id,
                           Map<String, Object> model) {
        Task task = taskService.searchTask(id);
        taskService.removeTask(task.getId());
        Iterable<Task> tasks = taskService.all();
        model.put("tasks", tasks);
        return "taskAdmin";
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/taskSolution")
    public String solTask(@RequestParam(required = false) long id,
                          Map<String, Object> model) {
        taskService.takeTask(id);
        Iterable<Task> tasks = taskService.all();
        model.put("tasks", tasks);
        return "taskAdmin";
    }
    @GetMapping("/taskAdmin")
    public String taskAdmin(@RequestParam(required = false) String filter, Model model) {
        Iterable<Task> tasks = taskService.all();
        if (filter != null && !filter.isEmpty()) {
            tasks = taskService.findByName(filter);
        } else {
            tasks = taskService.all();
        }
        model.addAttribute("tasks", tasks);
        model.addAttribute("filter", filter);
        return "taskAdmin";
    }


}

package com.example.task.management.system.controller;

import com.example.task.management.system.pojo.NoteDto;
import com.example.task.management.system.pojo.TaskDto;
import com.example.task.management.system.pojo.TaskDtoCreation;
import com.example.task.management.system.pojo.TaskFilter;
import com.example.task.management.system.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public TaskDto newTask(@RequestBody TaskDtoCreation newTask) {
        return taskService.addNewTask(newTask);
    }

    @GetMapping()
    public Collection<TaskDto> getAllTask() {
        return taskService.getAllTask();
    }

    @PutMapping("/{id}")
    public TaskDto updateTask(@RequestBody TaskDtoCreation detailsToUpdate, @PathVariable long id) {
        return taskService.updateTask(detailsToUpdate, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);
    }

    @GetMapping("/{id}")
    public TaskDto getTaskById(@PathVariable long id) {
        return taskService.getById(id);
    }

    @PostMapping("/{id}/notes")
    public NoteDto addNoteToTask(@RequestBody NoteDto noteToAdd, @PathVariable long id) {
        return taskService.addNoteToTask(noteToAdd, id);
    }

    @PutMapping("/close/{id}")
    public TaskDto closeTask(@PathVariable long id) {
        return taskService.closeTask(id);
    }

    @GetMapping("/findByName/{name}")
    public TaskDto getTaskByName(@PathVariable String name) {
        return taskService.getByName(name);
    }

    @PostMapping("/filter")
    public Collection<TaskDto> getTaskFilter(@RequestBody TaskFilter dataToFilter) {
        System.out.println(dataToFilter);
        return taskService.filter(dataToFilter);
    }
}
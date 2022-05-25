package com.example.task.management.system.controller;

import com.example.task.management.system.enums.Status;
import com.example.task.management.system.enums.TimeOptions;
import com.example.task.management.system.pojo.NoteDto;
import com.example.task.management.system.pojo.TaskDto;
import com.example.task.management.system.pojo.TaskDtoCreation;
import com.example.task.management.system.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @PutMapping("/update/{id}")
    public TaskDto updateTask(@RequestBody TaskDtoCreation detailsToUpdate, @PathVariable long id) {
        return taskService.updateTask(detailsToUpdate, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);
    }

    @PutMapping("/close/{id}")
    public TaskDto closeTask(@PathVariable long id) {
        return taskService.closeTask(id);
    }

    @PostMapping("/{id}/notes")
    public NoteDto addNoteToTask(@RequestBody NoteDto noteToAdd, @PathVariable long id) {
        return taskService.addNoteToTask(noteToAdd, id);
    }

    @GetMapping("/findById/{id}")
    public TaskDto getTaskById(@PathVariable long id) {
        return taskService.getById(id);
    }

    @GetMapping("/findByName/{name}")
    public TaskDto getTaskByName(@PathVariable String name) {
        return taskService.getByName(name);
    }

    @GetMapping("/findByStatus/{status}")
    public Collection<TaskDto> getTaskByStatus(@PathVariable Status status) {
        return taskService.getByStatus(status);
    }

    //@GetMapping("/findByDates/{startDate}/{endDate}/{timeOptions}")
    @GetMapping("/findByDates")
    public Collection<TaskDto> getTaskByEndDates(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                                 @RequestParam TimeOptions timeOptions) {

        //{"startDate":"2022-05-10","endDate":"2022-05-20"}
        return taskService.getByDates(startDate, endDate, timeOptions);
    }

    @GetMapping()
    public Collection<TaskDto> getAllTask() {
        return taskService.getAllTask();
    }
}
package com.example.task.management.system.service;

import com.example.task.management.system.enums.Status;
import com.example.task.management.system.enums.TimeOptions;
import com.example.task.management.system.exceptions.TaskNotFoundException;
import com.example.task.management.system.pojo.*;
import com.example.task.management.system.repo.NoteRepository;
import com.example.task.management.system.repo.TaskRepository;
import com.example.task.management.system.user.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class TaskServiceImpl implements TaskService {

    private static final String ERROR_MESSAGE_FOR_ID = "There is not task corresponding to id = ";

    @Autowired
    private DtoToEntityConverter dtoToEntityConverter;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.userUrlAllUsers}")
    private String userUrlAllUsers;

    @Override
    public TaskDto addNewTask(TaskDtoCreation newTask) {
        return dtoToEntityConverter.toTaskDto(isNewTaskValid(newTask));
    }

    @Override
    public TaskDto updateTask(TaskDtoCreation detailsToUpdate, long id) {
        Task taskValidToUpdate = taskRepository.findByTaskId(id)
                .map(task -> {
                    isTaskValidForUpdate(detailsToUpdate);
                    return task;
                })
                .orElseGet(() -> isNewTaskValid(detailsToUpdate));

        taskValidToUpdate.updateTask(detailsToUpdate);
        Task taskAfterUpdate = taskRepository.updateTask(taskValidToUpdate);

        return dtoToEntityConverter.toTaskDto(taskAfterUpdate);
    }

    private Task isNewTaskValid(TaskDtoCreation newTask) {
        isTaskValidToAdd(newTask);

        Task taskFromDto = dtoToEntityConverter.getEntityTaskFromUpdateDto(newTask);
        taskFromDto = taskRepository.addNewTask(taskFromDto);

        return taskFromDto;
    }

    @Override
    public void deleteTask(long id) {
        isIdExists(id);
        taskRepository.deleteTaskById(id);
    }

    @Override
    public TaskDto closeTask(long id) {
        Task task = taskRepository.findByTaskId(id)
                .map(currentTask -> {
                    currentTask.setCurrentStatus(Status.CLOSED);
                    return currentTask;
                })
                .orElseThrow(() -> new TaskNotFoundException(ERROR_MESSAGE_FOR_ID + id));

        return dtoToEntityConverter.toTaskDto(taskRepository.updateTask(task));
    }

    @Override
    public NoteDto addNoteToTask(NoteDto noteToAdd, long id) {
        Note newNote = taskRepository.findByTaskId(id)
                .map(task -> dtoToEntityConverter.getEntityNoteFromDto(noteToAdd, task))
                .orElseThrow(() -> new TaskNotFoundException(ERROR_MESSAGE_FOR_ID + id));

        return dtoToEntityConverter.toNoteDto(noteRepository.saveNote(newNote));
    }

    @Override
    public TaskDto getById(long id) {
        Task task = taskRepository.findByTaskId(id)
                .orElseThrow(() -> new TaskNotFoundException(ERROR_MESSAGE_FOR_ID + id));

        return dtoToEntityConverter.toTaskDto(task);
    }

    @Override
    public TaskDto getByName(String name) {
        Task task = taskRepository.findTaskByName(name)
                .orElseThrow(() -> new TaskNotFoundException("There is not task corresponding to name = " + name));

        return dtoToEntityConverter.toTaskDto(task);
    }

    @Override
    public Collection<TaskDto> getByStatus(Status status) {
        return dtoToEntityConverter.getCollectionTaskOfDto(taskRepository.findTaskByStatus(status));
    }

    @Override
    public Collection<TaskDto> getByDates(LocalDate startDate, LocalDate endDate, TimeOptions timeOptions) {
        Collection<Task> result = null;

        switch (timeOptions) {
            case START:
                result = taskRepository.findTaskByStartDates(startDate, endDate);
                break;
            case UPDATE:
                result = taskRepository.findTaskByUpdateDates(startDate, endDate);
                break;
            case EXPECTED:
                result = taskRepository.findTaskByExpectedDates(startDate, endDate);
                break;
        }

        return dtoToEntityConverter.getCollectionTaskOfDto(result);
    }

    @Override
    public Collection<TaskDto> getAllTask() {
        return dtoToEntityConverter.getCollectionTaskOfDto(taskRepository.getAllTask());
    }

    private void isTaskValidToAdd(ITask taskToCheck) {
        isNameExists(taskToCheck);
        isNameUnique(taskToCheck);
        isPriorityExists(taskToCheck);
        isEndDateLegal(taskToCheck);
        isUserIdExists(taskToCheck);
        putDefaultStatus(taskToCheck);
    }

    private void isTaskValidForUpdate(ITask taskToCheck) {
        isNameExists(taskToCheck);
        isNameUnique(taskToCheck);
        isPriorityExists(taskToCheck);
        isEndDateLegal(taskToCheck);
        isUserIdExists(taskToCheck);
        putDefaultStatus(taskToCheck);
    }

    private void isNameExists(ITask nameToCheck) {
        if (StringUtils.isEmpty(nameToCheck.getName())) {
            throw new TaskNotFoundException("Task name is a required field");
        }
    }

    private void isNameUnique(ITask nameToCheck) {
        if (taskRepository.findTaskByName(nameToCheck.getName()).isPresent()) {
            throw new TaskNotFoundException("Task name should be unique");
        }
    }

    private void isPriorityExists(ITask priorityToCheck) {
        if (priorityToCheck.getPriority() == null) {
            throw new TaskNotFoundException("Priority is a required field");
        }
    }

    private void isEndDateLegal(ITask localDateToCheck) {
        if (localDateToCheck.getExpectedEndDate().isBefore(LocalDate.now())) {
            //throw new DateTimeException("The end date was already");
            throw new TaskNotFoundException("The end date was already");
        }
    }

    private void isUserIdExists(ITask userIdToCheck) {
        if (userIdToCheck.getUserId() == null) {
            throw new TaskNotFoundException("User id is a required field");
        }

        try {
            restTemplate.getForObject(userUrlAllUsers + userIdToCheck.getUserId(), UserDto.class);
        }
        catch (Exception e) {
            throw new TaskNotFoundException(e.getMessage());
        }
    }

    private void isIdExists(long id) {
        Task task = taskRepository.findByTaskId(id)
                .orElseThrow(() -> new TaskNotFoundException(ERROR_MESSAGE_FOR_ID + id));
    }

    private void putDefaultStatus(ITask statusToCheck) {
        if (statusToCheck.getCurrentStatus() == null) {
            statusToCheck.setCurrentStatus(Status.OPEN);
        }
    }
}


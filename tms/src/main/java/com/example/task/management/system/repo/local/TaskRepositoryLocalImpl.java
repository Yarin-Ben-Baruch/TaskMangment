package com.example.task.management.system.repo.local;

import com.example.task.management.system.enums.Status;
import com.example.task.management.system.pojo.Note;
import com.example.task.management.system.pojo.Task;
import com.example.task.management.system.repo.NoteRepository;
import com.example.task.management.system.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("Local")
public class TaskRepositoryLocalImpl implements TaskRepository {

    @Autowired private Db db;

    @Autowired private NoteRepository noteRepository;

    public TaskRepositoryLocalImpl() {
    }

    @Override
    public Collection<Task> getAllTask() {
        return db.getTasks();
    }

    @Override
    public Task addNewTask(Task newTask) {
        db.getTasks().add(newTask);

        return newTask;
    }

    @Override
    public Task updateTask(Task taskToUpdate) {
        return taskToUpdate;
    }

    @Override
    public void deleteTaskById(long id) {
        db.getNotes().removeIf(note -> note.getId().equals(id));
        db.getTasks().removeIf(task -> task.getId().equals(id));
    }

    @Override
    public Optional<Task> findByTaskId(long id) {
        return db.getTasks().stream()
                .filter(currentTask -> currentTask.getId().equals(id))
                .findAny();
    }

    @Override
    public Optional<Task> findTaskByName(String name) {
        return db.getTasks().stream()
                .filter(currentTask -> currentTask.getName().equalsIgnoreCase(name)).findAny();
    }

    @Override
    public Collection<Task> findTaskByStatus(Status status) {
        return db.getTasks().stream()
                .filter(currentTask -> currentTask.getCurrentStatus() == status).collect(Collectors.toList());
    }

    @Override
    public Collection<Task> findTaskByStartDates(LocalDate startDate, LocalDate endDate) {
        return db.getTasks().stream()
                .filter(currentTask -> startDate.isBefore(currentTask.getStartDate())
                        && endDate.isAfter(currentTask.getStartDate()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Task> findTaskByExpectedDates(LocalDate startDate, LocalDate endDate) {
        return db.getTasks().stream()
                .filter(currentTask -> startDate.isBefore(currentTask.getExpectedEndDate())
                        && endDate.isAfter(currentTask.getExpectedEndDate()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Task> findTaskByUpdateDates(LocalDate startDate, LocalDate endDate) {
        return db.getTasks().stream()
                .filter(currentTask -> startDate.isBefore(currentTask.getUpdateDate())
                        && endDate.isAfter(currentTask.getUpdateDate()))
                .collect(Collectors.toList());
    }

    @Override
    public Task saveTask(Task task) {
        return addNewTask(task);
    }

    @Override
    public Collection<Task> getTheMostCriticalTask(int count) {
        List<Task> criticalTask = (List<Task>) getAllTask();
        Collections.sort(criticalTask);

        count = criticalTask.size() < 5 ? criticalTask.size() : count;

        return criticalTask.subList(0, count);
    }

    @Override
    public Collection<Task> getTaskByStatusFilterByDays(Status statusToFilter, int days) {
        LocalDate lastWeekDate = LocalDate.now().minusDays(days);
        Collection<Task> tasks = getAllTask();

        Collection<Task> taskCollection =
                tasks.stream().filter(currentTask -> currentTask.getCurrentStatus() == statusToFilter
                                && currentTask.getStartDate().isAfter(lastWeekDate))
                        .collect(Collectors.toList());

        return taskCollection;
    }

    @Override
    public Collection<Task> getDelayedTasks() {
        return getAllTask().stream()
                .filter(currentTask -> currentTask.getExpectedEndDate().isBefore(LocalDate.now()))
                .filter(currentTask -> currentTask.getCurrentStatus() != Status.CLOSED)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Task> getOpenCriticalTask() {
        Collection<Note> notes = noteRepository.getAllNotes().stream()
//                .filter(currentTask -> currentTask.getCurrentStatus() == Status.OPEN)
                .filter(currentNote -> currentNote.getContent().contains("critical"))
                .collect(Collectors.toList());

        return getAllTask().stream()
                .filter(currentTask -> currentTask.getCurrentStatus() == Status.OPEN)
                .filter(task -> notes.stream().anyMatch(note -> task.getName().equals(note.getName())))
                .collect(Collectors.toList());
    }
}

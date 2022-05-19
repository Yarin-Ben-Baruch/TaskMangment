package com.example.task.management.system.repo.mysql;

import com.example.task.management.system.enums.Status;
import com.example.task.management.system.pojo.Task;
import com.example.task.management.system.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

//@Primary
@Repository
@Profile("Db")
public class TaskRepositoryMySqlImp implements TaskRepository {

    @Autowired private TaskRepositoryMySql taskRepositoryMySql;

    private EntityManager entityManager;

    private CriteriaBuilder criteriaBuilder;

    private CriteriaQuery<Task> criteriaQuery;

    private Root<Task> taskRoot;

    public TaskRepositoryMySqlImp(EntityManager entityManager) {
        this.entityManager = entityManager;
        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(Task.class);
        taskRoot = criteriaQuery.from(Task.class);
    }

    @Override
    public Collection<Task> getAllTask() {
        return taskRepositoryMySql.findAll();
    }

    @Override
    public Task addNewTask(Task newTask) {
        return taskRepositoryMySql.save(newTask);
    }

    @Override
    public Task updateTask(Task taskToUpdate) {
        return saveTask(taskToUpdate);
    }

    @Override
    public Optional<Task> findByTaskId(long id) {
        return taskRepositoryMySql.findById(id);
    }

    @Override
    public void deleteTaskById(long id) {
        taskRepositoryMySql.deleteById(id);
    }

    @Override
    public Optional<Task> findTaskByName(String name) {
        return taskRepositoryMySql.findByName(name);
    }

    @Override
    public Collection<Task> findTaskByStatus(Status status) {
        return taskRepositoryMySql.findByCurrentStatus(status);
    }

    @Override
    public Collection<Task> findTaskByStartDates(LocalDate startDate, LocalDate endDate) {
        //return findTaskByDate(startDate, endDate, "startDate");

        return taskRepositoryMySql.findByStartDateBetween(startDate, endDate);
    }

    @Override
    public Collection<Task> findTaskByExpectedDates(LocalDate startDate, LocalDate endDate) {
        return taskRepositoryMySql.findByExpectedEndDateBetween(startDate, endDate);
    }

    @Override
    public Collection<Task> findTaskByUpdateDates(LocalDate startDate, LocalDate endDate) {
        return taskRepositoryMySql.findByUpdateDateBetween(startDate, endDate);
    }

    @Override
    public Task saveTask(Task task) {
        return addNewTask(task);
    }

    @Override
    public Collection<Task> getTheMostCriticalTask(int count) {
        return entityManager.createQuery("SELECT t FROM Task t ORDER BY t.priority",
                Task.class).setMaxResults(count).getResultList();
    }

    @Override
    public Collection<Task> getTaskByStatusFilterByDays(Status statusToFilter, int days) {
        LocalDate lastWeekDate = LocalDate.now().minusDays(days);

        Predicate startDatePredicate = criteriaBuilder.greaterThanOrEqualTo(taskRoot.get("startDate"), lastWeekDate);
        Predicate endDatePredicate =
                criteriaBuilder.equal(taskRoot.<Status>get("currentStatus"), statusToFilter.getValue());

        criteriaQuery.where(startDatePredicate, endDatePredicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Collection<Task> getDelayedTasks() {
        return taskRepositoryMySql.getDelayedTasks(LocalDate.now());
    }

    @Override
    public Collection<Task> getOpenCriticalTask() {
        return taskRepositoryMySql.getOpenCriticalTask();
    }
}

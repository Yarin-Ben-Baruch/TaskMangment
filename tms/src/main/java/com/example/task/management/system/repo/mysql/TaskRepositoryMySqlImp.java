package com.example.task.management.system.repo.mysql;

import com.example.task.management.system.enums.Status;
import com.example.task.management.system.pojo.Task;
import com.example.task.management.system.pojo.TaskFilter;
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

    public TaskRepositoryMySqlImp(EntityManager entityManager) {
        this.entityManager = entityManager;
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

//    @Override
//    public Collection<Task> findTaskByStatus(Status status) {
//        return taskRepositoryMySql.findByCurrentStatus(status);
//    }
//
//    @Override
//    public Collection<Task> findTaskByStartDates(LocalDate startDate, LocalDate endDate) {
//        //return findTaskByDate(startDate, endDate, "startDate");
//
//        return taskRepositoryMySql.findByStartDateBetween(startDate, endDate);
//    }
//
//    @Override
//    public Collection<Task> findTaskByExpectedDates(LocalDate startDate, LocalDate endDate) {
//        return taskRepositoryMySql.findByExpectedEndDateBetween(startDate, endDate);
//    }
//
//    @Override
//    public Collection<Task> findTaskByUpdateDates(LocalDate startDate, LocalDate endDate) {
//        return taskRepositoryMySql.findByUpdateDateBetween(startDate, endDate);
//    }

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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);
        Root<Task> taskRoot = criteriaQuery.from(Task.class);

        LocalDate lastWeekDate = LocalDate.now().minusDays(days);

        Predicate startDatePredicateByStartTime =
                criteriaBuilder.greaterThanOrEqualTo(taskRoot.get("startDate"), lastWeekDate);
        Predicate startDatePredicateByEndTime =
                criteriaBuilder.lessThanOrEqualTo(taskRoot.get("startDate"), LocalDate.now());
        Predicate endDatePredicate =
                criteriaBuilder.equal(taskRoot.<Status>get("currentStatus"), statusToFilter.getValue());

        criteriaQuery.where(startDatePredicateByStartTime, endDatePredicate, startDatePredicateByEndTime);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Collection<Task> getTaskByStatus(Status statusToFilter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);
        Root<Task> taskRoot = criteriaQuery.from(Task.class);

        Predicate endDatePredicate =
                criteriaBuilder.equal(taskRoot.<Status>get("currentStatus"), statusToFilter.getValue());

        criteriaQuery.where(endDatePredicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Collection<Task> filter(TaskFilter taskFilter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);
        Root<Task> taskRoot = criteriaQuery.from(Task.class);

        Predicate predicate1 = criteriaBuilder.and(), predicate2 = criteriaBuilder.and(), predicate3 =
                criteriaBuilder.and(),
                predicate4 = criteriaBuilder.and(), predicate5 = criteriaBuilder.and(), predicate6 =
                criteriaBuilder.and(), predicate7 = criteriaBuilder.and();

        if (taskFilter.getFromStartDate() != null) {
            predicate1 = criteriaBuilder.greaterThanOrEqualTo(taskRoot.get("startDate"), taskFilter.getFromStartDate());
        }

        if (taskFilter.getToStartDate() != null) {
            predicate2 = criteriaBuilder.lessThanOrEqualTo(taskRoot.get("startDate"), taskFilter.getToStartDate());
        }

        if (taskFilter.getFromExpectedEndDate() != null) {
            predicate3 = criteriaBuilder.greaterThanOrEqualTo(taskRoot.get("expectedEndDate"),
                    taskFilter.getFromExpectedEndDate());
        }
        if (taskFilter.getToExpectedEndDate() != null) {
            predicate4 = criteriaBuilder.lessThanOrEqualTo(taskRoot.get("expectedEndDate"),
                    taskFilter.getToExpectedEndDate());
        }

        if (taskFilter.getFromUpdateDate() != null) {
            predicate5 =
                    criteriaBuilder.greaterThanOrEqualTo(taskRoot.get("updateDate"), taskFilter.getFromUpdateDate());
        }
        if (taskFilter.getToUpdateDate() != null) {
            predicate6 = criteriaBuilder.lessThanOrEqualTo(taskRoot.get("updateDate"), taskFilter.getToUpdateDate());
        }

        if (taskFilter.getStatus() != null) {
            predicate7 =
                    criteriaBuilder.equal(taskRoot.<Status>get("currentStatus"), taskFilter.getStatus().getValue());
        }

        criteriaQuery.where(predicate1, predicate2, predicate3, predicate4, predicate5, predicate6, predicate7);

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

package com.example.task.management.system.repo.mysql;

import com.example.task.management.system.pojo.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface TaskRepositoryMySql extends JpaRepository<Task, Long> {

    Optional<Task> findByName(String name);

//    Collection<Task> findByCurrentStatus(Status status);
//
//    List<Task> findByStartDateBetween(LocalDate start, LocalDate end);
//
//    List<Task> findByUpdateDateBetween(LocalDate start, LocalDate end);
//
//    List<Task> findByExpectedEndDateBetween(LocalDate start, LocalDate end);

    @Query("SELECT t FROM Task t WHERE t.expectedEndDate < :currentTime AND t.currentStatus <> 2")
    Collection<Task> getDelayedTasks(@Param("currentTime") LocalDate currentTime);

    @Query("SELECT DISTINCT n.task FROM Note n WHERE n.content LIKE '%critical%' AND n.task.currentStatus = 0 ")
    Collection<Task> getOpenCriticalTask();
}


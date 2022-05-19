package com.example.task.management.system.repo.mysql;

import com.example.task.management.system.pojo.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface NoteRepositoryMySql extends JpaRepository<Note, Long> {

    Collection<Note> findByName(String name);

    @Modifying
    @Query("delete from Note n where n.task.id = :id ")
    void deleteByTask(@Param("id") long id);
}

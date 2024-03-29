package com.hateoas.demo.repository;

import com.hateoas.demo.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findAllByPersonId(Long id);

}

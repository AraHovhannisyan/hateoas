package com.hateoas.demo.rest;

import com.hateoas.demo.hateoasResource.JobResource;
import com.hateoas.demo.model.Job;
import com.hateoas.demo.repository.JobRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "jobs")
public class JobEndPoint {

    private final JobRepository jobRepository;

    public JobEndPoint(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Job job) {
        jobRepository.save(job);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Job>> findAll() {
        return ResponseEntity.ok(jobRepository.findAll());
    }

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public ResponseEntity<JobResource> findById(@PathVariable Long id) {
        Optional<Job> byId = jobRepository.findById(id);
        if (!byId.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(byId.map(JobResource::new).get());
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Job job) {
        Optional<Job> jobFromRepo = jobRepository.findById(job.getId());
        if (!jobFromRepo.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<Job> mapJob = jobFromRepo.map(b -> {
            b.setName(job.getName());
            b.setDescription(job.getDescription());
            b.setPerson(job.getPerson());
            b.setSalary(job.getSalary());
            return b;
        });
        jobRepository.save(mapJob.get());
        return ResponseEntity.ok().body("job updated");
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody Job job) {
        Optional<Job> jobFromRepo = jobRepository.findById(job.getId());
        if (!jobFromRepo.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        jobRepository.delete(job);
        return ResponseEntity.ok().body("job deleted");
    }


}

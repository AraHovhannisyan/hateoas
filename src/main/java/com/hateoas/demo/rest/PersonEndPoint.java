package com.hateoas.demo.rest;

import com.hateoas.demo.hateoasResource.JobResource;
import com.hateoas.demo.hateoasResource.PersonResource;
import com.hateoas.demo.model.Person;
import com.hateoas.demo.repository.JobRepository;
import com.hateoas.demo.repository.PersonRepository;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/persons", produces = "application/hal+json")
public class PersonEndPoint {

    private final PersonRepository personRepository;
    private final JobRepository jobRepository;

    public PersonEndPoint(PersonRepository personRepository, JobRepository jobRepository) {
        this.personRepository = personRepository;
        this.jobRepository = jobRepository;
    }

    @PostMapping
    public ResponseEntity<PersonResource> save(@RequestBody Person person) {
        personRepository.save(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PersonResource(person));
    }

    @GetMapping
    public ResponseEntity<Resources<PersonResource>> findAll() {
        List<PersonResource> personResources = personRepository.findAll().stream().map(PersonResource::new).collect(Collectors.toList());
        Resources<PersonResource> resources = new Resources<>(personResources);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResource> findById(@PathVariable long id) {
        Optional<Person> byId = personRepository.findById(id);
        return byId.map(person -> ResponseEntity.ok(new PersonResource(person))).
                orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/{personId}/jobs")
    public ResponseEntity<Resources<JobResource>> findJobsByPersonId(@PathVariable long personId) {
        List<JobResource> jobsByAuthorId = findAllJobsByPersonId(personId);
        Resources<JobResource> jobResources = new Resources<>(jobsByAuthorId);
        String toUriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        jobResources.add(new Link(toUriString, "jobs"));
        return ResponseEntity.ok(jobResources);
    }

    private List<JobResource> findAllJobsByPersonId(long personId) {
        return jobRepository.findAllByPersonId(personId)
                .stream()
                .map(JobResource::new)
                .collect(Collectors.toList());
    }

    @PutMapping
    public ResponseEntity<PersonResource> update(@RequestBody Person person) {
        Optional<Person> personFromRepo = personRepository.findById(person.getId());
        return personFromRepo.map(a -> {
            a.setName(person.getName());
            a.setAge(person.getAge());
            personRepository.save(a);
            return ResponseEntity.ok().body(new PersonResource(a));
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody Person person) {
        Optional<Person> personFromRepo = personRepository.findById(person.getId());
        if (!personFromRepo.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        personRepository.delete(person);
        return ResponseEntity.ok().body("person deleted");
    }
}
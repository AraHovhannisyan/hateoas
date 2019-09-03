package com.hateoas.demo;

import com.hateoas.demo.model.Person;
import com.hateoas.demo.model.Job;
import com.hateoas.demo.repository.JobRepository;
import com.hateoas.demo.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HateoasApplication implements CommandLineRunner {

    private final PersonRepository personRepository;
    private final JobRepository jobRepository;

    public HateoasApplication(PersonRepository personRepository, JobRepository jobRepository) {
        this.personRepository = personRepository;
        this.jobRepository = jobRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(HateoasApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Person person = Person.builder()
                .name("Poxos")
                .age(32)
                .build();

        if (personRepository.count() < 1) {
            personRepository.save(person);
        }

        Job job = Job.builder()
                .name("Gyumri")
                .person(person)
                .description("Demo description")
                .build();

        if (jobRepository.count() < 1) {
            jobRepository.save(job);
        }
    }
}

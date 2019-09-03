package com.hateoas.demo.hateoasResource;

import com.hateoas.demo.model.Person;
import com.hateoas.demo.rest.PersonEndPoint;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

@Getter
@Relation(value = "person", collectionRelation = "persons")
public class PersonResource extends ResourceSupport {
    private Person person;

    public PersonResource(Person person) {
        this.person = person;
        long id = person.getId();
        add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(PersonEndPoint.class).save(person)).withSelfRel().withType("POST"));
        add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(PersonEndPoint.class).findAll()).withSelfRel().withType("GET"));
        add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(PersonEndPoint.class).findById(id)).withSelfRel().withType("GET"));
        add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(PersonEndPoint.class).findJobsByPersonId(id)).withRel("jobs").withType("GET"));
        add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(PersonEndPoint.class).update(person)).withSelfRel().withType("PUT"));
        add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(PersonEndPoint.class).delete(person)).withSelfRel().withType("DELETE"));
    }
}
package com.hateoas.demo.hateoasResource;

import com.hateoas.demo.model.Job;
import com.hateoas.demo.rest.JobEndPoint;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

@Getter
public class JobResource extends ResourceSupport {
    private Job job;

    public JobResource(Job job) {
        this.job = job;
        long id = job.getId();
        add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(JobEndPoint.class).findById(id)).withSelfRel());
    }
}
package com.hateoas.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_generator")
    @SequenceGenerator(name = "job_generator", sequenceName = "job_id_seq", allocationSize = 1)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private int salary;

    @ManyToOne
    private Person person;


}

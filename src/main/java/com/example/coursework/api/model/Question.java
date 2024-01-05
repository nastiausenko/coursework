package com.example.coursework.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "number", nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer number;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "Quiz_id")
    private Quiz quiz;
}

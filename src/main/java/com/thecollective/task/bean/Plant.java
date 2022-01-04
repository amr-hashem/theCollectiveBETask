package com.thecollective.task.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Plant {
    @Id
    @GeneratedValue(generator = "sequence-generator")
    @Column(name = "SEQGEN19")
    private int sequenceNumber;

}

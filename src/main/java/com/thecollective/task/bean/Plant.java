package com.thecollective.task.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "SEQGEN19")
    private Integer sequenceNumber;

    @Column(name = "YEAR")
    private Integer year;

    @Column(name = "PSTATABB")
    private String plantState;

    @Column(name = "PNAME")
    private String plantName;

    @Column(name = "GENID")
    private String generatorId;

    @Column(name = "GENSTAT")
    @Enumerated(EnumType.STRING)
    private GeneratorStatus status;

    @Column(name = "GENNTAN")
    private Long generatorAnnualNetGeneration;
}

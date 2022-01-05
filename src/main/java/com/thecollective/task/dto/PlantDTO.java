package com.thecollective.task.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.thecollective.task.bean.GeneratorStatus;
import com.thecollective.task.util.GeneratorAnnualNetGenerationConverter;
import com.thecollective.task.util.StatusConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantDTO {

    @CsvBindByName(column = "SEQGEN19")
    private Integer sequenceNumber;

    @CsvBindByName(column = "YEAR")
    private Integer year;

    @CsvBindByName(column = "PSTATABB")
    private String plantState;

    @CsvBindByName(column = "PNAME")
    private String plantName;

    @CsvBindByName(column = "GENID")
    private String generatorId;

    @CsvCustomBindByName(column = "GENSTAT", converter = StatusConverter.class)
    private GeneratorStatus status;

    @CsvCustomBindByName(column = "GENNTAN", converter = GeneratorAnnualNetGenerationConverter.class)
    private Long generatorAnnualNetGeneration;
}

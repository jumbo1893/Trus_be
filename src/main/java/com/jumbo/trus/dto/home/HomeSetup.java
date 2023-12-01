package com.jumbo.trus.dto.home;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jumbo.trus.dto.pkfl.PkflMatchDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeSetup {

    @NotNull
    private String nextBirthday;

    private List<String> randomFacts;

    private List<PkflMatchDTO> nextAndLastPkflMatch;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Chart chart;

}

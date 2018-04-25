package it.gdhi.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PhaseDto {

    public String phaseName;
    public Integer phaseValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhaseDto phaseDto = (PhaseDto) o;
        return Objects.equals(phaseName, phaseDto.phaseName) &&
                Objects.equals(phaseValue, phaseDto.phaseValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phaseName, phaseValue);
    }
}

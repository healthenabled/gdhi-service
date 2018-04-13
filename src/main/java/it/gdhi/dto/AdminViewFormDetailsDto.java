package it.gdhi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class AdminViewFormDetailsDto {

    private String countryName;
    private UUID countryUUID;
    private String status;
    private String contactName;
    private String contactEmail;


}

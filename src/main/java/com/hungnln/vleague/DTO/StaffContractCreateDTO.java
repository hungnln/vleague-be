package com.hungnln.vleague.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hungnln.vleague.constant.validation_message.ValidationMessage;
import com.hungnln.vleague.constant.validation_size.ValidationSize;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffContractCreateDTO {

    @NotNull(message = ValidationMessage.SALARY_VALID_MESSAGE)
    @Positive
    private int salary;
    
    private String description;

    @NotNull(message = ValidationMessage.START_DATE_VALID_MESSAGE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date start;

    @NotNull(message = ValidationMessage.END_DATE_VALID_MESSAGE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date end;

    @NotNull(message = ValidationMessage.IMAGE_VALID_MESSAGE)
    private UUID staffId;

    @NotNull(message = ValidationMessage.IMAGE_VALID_MESSAGE)
    private UUID clubId;

    @AssertTrue(message = ValidationMessage.DATE_VALID_MESSAGE)
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @JsonProperty("end")
    public boolean isValidDateRange() {
        return start.before(end);
    }
}

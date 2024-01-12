package com.hungnln.vleague.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hungnln.vleague.constant.validation_message.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Date;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffContractUpdateDTO {
    @NotEmpty(message = ValidationMessage.NAME_VALID_MESSAGE)
    private String description;

    @NotNull(message = ValidationMessage.SALARY_VALID_MESSAGE)
    @Positive
    private float salary;

    @NotNull(message = ValidationMessage.END_DATE_VALID_MESSAGE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date end;

}

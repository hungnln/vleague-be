package com.hungnln.vleague.DTO;

import com.hungnln.vleague.constant.validation_message.ValidationMessage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerContractUpdateDTO {
    private String description;

    @NotNull(message = ValidationMessage.NUMBER_VALID_MESSAGE)
    @Min(0)
    private int number;

    @NotNull(message = ValidationMessage.SALARY_VALID_MESSAGE)
    private float salary;

    @NotNull(message = ValidationMessage.END_DATE_VALID_MESSAGE)
    private Date end;

}

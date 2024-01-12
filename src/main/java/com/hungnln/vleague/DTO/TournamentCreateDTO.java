package com.hungnln.vleague.DTO;

import com.hungnln.vleague.constant.validation_message.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TournamentCreateDTO {
    @NotEmpty(message = ValidationMessage.NAME_VALID_MESSAGE)
    private String name;
    @NotNull(message = ValidationMessage.START_DATE_VALID_MESSAGE)
    private Date start;
    @NotNull(message = ValidationMessage.END_DATE_VALID_MESSAGE)
    private Date end;
}

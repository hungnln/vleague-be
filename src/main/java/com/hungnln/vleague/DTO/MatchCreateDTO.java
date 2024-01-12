package com.hungnln.vleague.DTO;

import com.hungnln.vleague.constant.validation_message.ValidationMessage;
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
public class MatchCreateDTO {
    @NotNull(message = ValidationMessage.DATE_VALID_MESSAGE)
    private Date startDate;
    @NotNull(message = ValidationMessage.HOME_CLUB_VALID_MESSAGE)
    private UUID homeClubId;
    @NotNull(message = ValidationMessage.AWAY_CLUB_VALID_MESSAGE)
    private UUID awayClubId;
    @NotNull(message = ValidationMessage.STADIUM_VALID_MESSAGE)
    private UUID stadiumId;
    @NotNull(message = ValidationMessage.ROUND_VALID_MESSAGE)
    private UUID roundId;
}

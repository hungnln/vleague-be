package com.hungnln.vleague.DTO;

import com.hungnln.vleague.constant.validation_message.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchActivityCreateDTO {
    @NotNull(message = ValidationMessage.MINUTE_IN_MATCH_VALID_MESSAGE)
    private int minuteInMatch;
    @NotNull(message = ValidationMessage.TYPE_VALID_MESSAGE)
    private int type;
    private List<UUID> refereeIds;
    private List<UUID> playerContractIds;
    private List<UUID> staffContractIds;

}

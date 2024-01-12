package com.hungnln.vleague.DTO;

import com.hungnln.vleague.constant.validation_message.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubCreateDTO {
    @NotEmpty(message = ValidationMessage.NAME_VALID_MESSAGE)
    private String name;

    @NotEmpty(message = ValidationMessage.HEADQUARTER_VALID_MESSAGE)
    private String headQuarter;

    @NotEmpty(message = ValidationMessage.IMAGE_VALID_MESSAGE)
    private String imageURL;

    @NotNull(message = ValidationMessage.IMAGE_VALID_MESSAGE)
    private UUID stadiumId;
}

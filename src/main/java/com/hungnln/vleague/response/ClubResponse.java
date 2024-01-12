package com.hungnln.vleague.response;

import com.hungnln.vleague.constant.validation_message.ValidationMessage;
import com.hungnln.vleague.entity.Stadium;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClubResponse {
    @NotEmpty(message = ValidationMessage.NAME_VALID_MESSAGE)
    private UUID id;
    private String name;
    private String imageURL;
    private String headQuarter;
    private UUID stadiumId;
    private Stadium stadium;
}

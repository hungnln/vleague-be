package com.hungnln.vleague.DTO;

import com.hungnln.vleague.constant.validation_message.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StadiumUpdateDTO {
    @NotEmpty(message = ValidationMessage.NAME_VALID_MESSAGE)
    private String name;
    @NotEmpty(message = ValidationMessage.ADDRESS_VALID_MESSAGE)
    private String address;
    @NotEmpty(message = ValidationMessage.IMAGE_VALID_MESSAGE)
    private String imageURL;
}

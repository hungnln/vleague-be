package com.hungnln.vleague.DTO;

import com.hungnln.vleague.constant.validation_message.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsCreateDTO {
    @NotEmpty(message = ValidationMessage.TITLE_VALID_MESSAGE)
    private String title;
    @NotEmpty(message = ValidationMessage.THUMBNAIL_VALID_MESSAGE)
    private String thumbnailImageURL;
    @NotEmpty(message = ValidationMessage.CONTENT_VALID_MESSAGE)
    private String content;
//    private UUID playerContractId;
    private List<UUID> playerIds;
    private List<UUID> clubIds;

}

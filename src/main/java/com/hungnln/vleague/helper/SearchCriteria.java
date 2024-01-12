package com.hungnln.vleague.helper;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor()
@NoArgsConstructor()
@Service
public class SearchCriteria {
    private String key;
    private SearchOperation operation;
    private Object value;
}

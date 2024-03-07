package com.demo.demo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HealthyResponse {
    @JsonProperty("code")
    private String code;

    @JsonProperty("detail")
    private String detail;

    @JsonProperty("message")
    private String message;
}

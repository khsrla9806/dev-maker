package com.fastcampus.devmaker.devmaker.dto;

import com.fastcampus.devmaker.devmaker.exception.DMakerErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DevMakerErrorResponse {
    private DMakerErrorCode errorCode;
    private String errorMessage;
}

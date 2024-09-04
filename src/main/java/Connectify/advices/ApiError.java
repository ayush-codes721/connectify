package Connectify.advices;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiError {

    @Builder.Default
    private boolean success = false;
    private HttpStatus status;
    @Builder.Default
    private LocalDateTime timeStamp = LocalDateTime.now();
    private String error;

}

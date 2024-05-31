package wanted.market.api.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResult {

    private int code;
    private String message;
    private LocalDateTime time;
}

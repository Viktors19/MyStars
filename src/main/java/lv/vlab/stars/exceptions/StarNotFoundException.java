package lv.vlab.stars.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Star Not Found")
public class StarNotFoundException extends RuntimeException {
}

package lv.vlab.stars.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Constellation Not Found")
public class ConstellationNotFoundException extends RuntimeException {
}

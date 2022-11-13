package pl.pjatk.car_rent.exceptions;

import lombok.Data;


@Data
public class ValidationException extends RuntimeException {
    private final String message;
}

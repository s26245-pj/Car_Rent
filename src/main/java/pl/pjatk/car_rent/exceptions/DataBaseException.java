package pl.pjatk.car_rent.exceptions;

import lombok.Data;


@Data
public class DataBaseException extends RuntimeException {
    private final String message;
    private final Exception exception;
}

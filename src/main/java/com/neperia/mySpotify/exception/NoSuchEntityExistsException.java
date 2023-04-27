package com.neperia.mySpotify.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class NoSuchEntityExistsException extends RuntimeException{
    private String message;

    public NoSuchEntityExistsException(String entityName, Object id) {
        super("Entity not found!");
        this.message = entityName + " was not found for id: " + id + "!";
    }
}
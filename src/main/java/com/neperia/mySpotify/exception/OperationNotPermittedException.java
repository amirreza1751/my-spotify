package com.neperia.mySpotify.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class OperationNotPermittedException extends RuntimeException{
    private String message;

    public OperationNotPermittedException(String entityName, Object id) {
        super("Entity not found!");
        this.message = "Operation is not permitted for entity " + entityName + " with id: " + id + "!";
    }
}
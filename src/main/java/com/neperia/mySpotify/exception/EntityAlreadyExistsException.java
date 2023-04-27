package com.neperia.mySpotify.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class EntityAlreadyExistsException extends RuntimeException{
    private String message;

    public EntityAlreadyExistsException(String entityName, Object id) {
        super("Entity not found!");
        this.message = entityName + " already exists with id: " + id + "!";
    }
}
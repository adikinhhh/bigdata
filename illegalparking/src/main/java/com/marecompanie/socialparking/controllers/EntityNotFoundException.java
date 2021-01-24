package com.marecompanie.socialparking.controllers;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException() {
        super("Entity was not found");
    }
}

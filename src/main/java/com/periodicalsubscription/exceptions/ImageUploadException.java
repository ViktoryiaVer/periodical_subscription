package com.periodicalsubscription.exceptions;

/**
 * class of exception thrown when problem while uploading image occurs
 */
public class ImageUploadException extends RuntimeException {
    public ImageUploadException(String message) {
        super(message);
    }
}

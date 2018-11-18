package br.com.gabrielspassos.characters.error;

public class SimpleError {

    private String message;

    public SimpleError() {
    }

    public SimpleError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
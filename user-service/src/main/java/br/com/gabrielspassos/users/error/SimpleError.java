package br.com.gabrielspassos.users.error;

public class SimpleError {

    private String message;

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
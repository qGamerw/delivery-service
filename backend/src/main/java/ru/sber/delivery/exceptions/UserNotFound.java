package ru.sber.delivery.exceptions;

/**
 * Стоит выбрасывать если пользователь не найден
 */
public class UserNotFound extends RuntimeException {
    public UserNotFound() {
    }

    public UserNotFound(String message) {
        super(message);
    }
}

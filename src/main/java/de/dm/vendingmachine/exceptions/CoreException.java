package de.dm.vendingmachine.exceptions;

public class CoreException extends RuntimeException {
    public CoreException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}

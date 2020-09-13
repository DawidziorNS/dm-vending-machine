package de.dm.vendingmachine.exceptions;

public class CoreException extends RuntimeException {
    CoreException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}

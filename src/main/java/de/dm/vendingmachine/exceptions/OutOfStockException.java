package de.dm.vendingmachine.exceptions;

public class OutOfStockException extends CoreException {

    private static final ErrorCode ERROR_MESSAGE = ErrorCode.OUT_OF_STOCK;

    public OutOfStockException() {
        super(ERROR_MESSAGE);
    }
}

package de.dm.vendingmachine.exceptions;

public class ProductNotFoundException extends CoreException {

    private static final ErrorCode ERROR_MESSAGE = ErrorCode.PRODUCT_NOT_FOUND;

    public ProductNotFoundException() {
        super(ERROR_MESSAGE);
    }
}

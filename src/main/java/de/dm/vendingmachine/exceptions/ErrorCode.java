package de.dm.vendingmachine.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    OUT_OF_STOCK("Products out of stock"),
    PRODUCT_NOT_FOUND("Product not found");

    private String message;
}

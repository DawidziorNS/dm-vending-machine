package de.dm.vendingmachine.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductGroup {

    PERSONAL_HYGIENE("Personal hygiene"),
    FACE("Face"),
    PHOTO("Photo"),
    TEXTILE("Textile"),
    HOUSEHOLD("Household");

    private String name;

}

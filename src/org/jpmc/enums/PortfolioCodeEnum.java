package org.jpmc.enums;

public enum PortfolioCodeEnum {
    A,
    B,
    C;

    private static final PortfolioCodeEnum[] copyOfValues = values();

    public static Boolean forName(String name) {
        for (PortfolioCodeEnum value : copyOfValues) {
            if (value.name().equals(name)) {
                return true;
            }
        }
        return false;
    }
}

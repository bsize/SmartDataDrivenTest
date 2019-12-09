package app.utils;

public enum SiteSubMenuEnum {
    REVIEW("Обзор"),
    DEVICES("Устройства"),
    LICENSES("Лицензии"),
    LOADS("Загрузки");

    private String value;

    SiteSubMenuEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}


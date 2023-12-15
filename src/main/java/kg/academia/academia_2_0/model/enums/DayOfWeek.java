package kg.academia.academia_2_0.model.enums;

public enum DayOfWeek {
    MONDAY("Понедельник"),
    TUESDAY("Вторник"),
    WEDNESDAY("Среда"),
    THURSDAY("Четверг"),
    FRIDAY("Пятница"),
    SATURDAY("Суббота"),
    SUNDAY("Воскресенье");

    private final String label;

    DayOfWeek(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}

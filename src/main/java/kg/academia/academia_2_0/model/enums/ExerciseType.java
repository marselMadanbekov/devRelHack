package kg.academia.academia_2_0.model.enums;

public enum ExerciseType {
    TEST ("Тест"),
    TRAINER("Тренажер");

    private final String value;

    ExerciseType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

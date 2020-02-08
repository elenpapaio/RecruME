package gr.codehub.RecruME.models;

public enum EducationLevel {
    HIGH_SCHOOL("HIGH_SCHOOL"),
    BACHELOR_DEGREE("BACHELOR_DEGREE"),
    MASTER_DEGREE("MASTER_DEGREE"),
    PHD("PHD");
    private String stringValue;

    private EducationLevel(String value) {
        this.stringValue = value;
    }

    public String getValue() {
        return stringValue;
    }

    public static EducationLevel getEducationLevelFromString(String stringValue) {
        for (EducationLevel educationLevel : EducationLevel.values()) {
            if (educationLevel.getValue() == stringValue) {
                return educationLevel;
            }
        }
        return null;
    }
}

package gr.codehub.RecruME.models;

public enum SkillLevel {
    JUNIOR("JUNIOR"),
    MID("MID"),
    SENIOR("SENIOR");

    private String stringValue;

    private SkillLevel(String value) {
        this.stringValue = value;
    }

    public String getValue() {
        return stringValue;
    }

    public static SkillLevel getEnumFromString(String stringValue) {
        for (SkillLevel skillLevel : SkillLevel.values()) {
            if (skillLevel.getValue() == stringValue) {
                return skillLevel;
            }
        }
        return null;
    }
}

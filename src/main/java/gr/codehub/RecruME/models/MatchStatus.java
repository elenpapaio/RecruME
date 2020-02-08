package gr.codehub.RecruME.models;

public enum MatchStatus {
    //AUTOMATIC, MANUAL
    AUTOMATIC("AUTOMATIC"),
    MANUAL("MANUAL");

    private String stringValue;

    private MatchStatus(String value) {
        this.stringValue = value;
    }

    public String getValue() {
        return stringValue;
    }

    public static MatchStatus getEnumFromString(String stringValue) {
        for (MatchStatus matchStatus : MatchStatus.values()) {
            if (matchStatus.getValue() == stringValue) {
                return matchStatus;
            }
        }
        return null;
    }
}

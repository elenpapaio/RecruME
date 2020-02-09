package gr.codehub.RecruME.models;

public enum FinalizedMatching {
    NO("NO"),
    YES("YES");

    private String text;
    FinalizedMatching(String Text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }
    public static FinalizedMatching getEnumFromString(String stringValue) {
        for (FinalizedMatching finalizedMatching : FinalizedMatching.values()) {
            if (finalizedMatching.getText().equals(stringValue)) {
                return finalizedMatching;
            }
        }
        return null;
    }

}

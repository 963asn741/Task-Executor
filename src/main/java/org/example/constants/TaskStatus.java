package org.example.constants;

public enum TaskStatus {
    INITIATED("Initiated"),
    PROCESSING("Processing"),
    COMPLETED("Completed"),
    FAILED("Failed");

    private final String value;

    private TaskStatus(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}

package uwaterloo.cs446group7.increpeable.backend;

// Experiment class to test backend; will eventually be deleted.
public enum TaskStatus {
    Open("Open"),
    InProgress("In Progress"),
    Complete("Complete");
    String displayName;
    TaskStatus(String displayName) {
        this.displayName = displayName;
    }
}

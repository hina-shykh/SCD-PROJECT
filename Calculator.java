public abstract class Calculator {
    protected double totalCredits = 0;
    protected double totalGradePoints = 0;

    // Abstract methods to be implemented by subclasses
    public abstract double getGradePoint(String grade);
    public abstract void calculate();

    // Synchronized method to add subject details (thread-safe)
    public synchronized void addSubject(double credits, double gradePoint) {
        if (credits <= 0 || gradePoint < 0) {
            throw new IllegalArgumentException("Invalid credits or grade point value.");
        }
        // encapsulation
        totalCredits += credits;
        totalGradePoints += (credits * gradePoint);
    }

    public double getTotalCredits() {
        return totalCredits;
    }

    public double getTotalGradePoints() {
        return totalGradePoints;
    }
}

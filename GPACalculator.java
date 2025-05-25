public class GPACalculator {
    private double totalCredits;
    private double totalPoints;

    // Constructor to initialize the variables
    public GPACalculator() {
        totalCredits = 0;
        totalPoints = 0;
    }

    // Method to convert grade to grade points
    public double getGradePoint(String grade) {
        switch (grade) {
            case "A": return 4.0;
            case "B": return 3.0;
            case "C": return 2.0;
            case "D": return 1.0;
            case "F": return 0.0;
            default: throw new IllegalArgumentException("Invalid grade: " + grade);
        }
    }

    // Method to add a subject's grade points and credits
    public void addSubject(double gradePoints, int credits) {
        totalCredits += credits;
        totalPoints += gradePoints * credits;
    }

    // Method to calculate the GPA
    public double calculateGPA() {
        if (totalCredits == 0) return 0;  // Avoid division by zero
        return totalPoints / totalCredits;
    }
}


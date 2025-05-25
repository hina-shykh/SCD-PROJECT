public class CGPACalculator {
    private double totalCredits;
    private double totalPoints;

    // Constructor to initialize the variables
    public CGPACalculator() {
        totalCredits = 0;
        totalPoints = 0;
    }

    // Method to add a semester's credits and grade points
    public void addSemester(double gradePoints, int credits) {
        totalCredits += credits;
        totalPoints += gradePoints * credits;
    }

    // Method to calculate the CGPA
    public double calculateCGPA() {
        if (totalCredits == 0) return 0;  // Avoid division by zero
        return totalPoints / totalCredits;
    }
}

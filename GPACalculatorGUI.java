import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class GPACalculatorGUI extends JFrame {
    private JTextField subjectField;
    private JTextField creditsField;
    private JComboBox<String> gradeComboBox;
    private JTable subjectTable;
    private DefaultTableModel tableModel;
    private JTextField gpaTextField;

    private final GPACalculator gpaCalculator = new GPACalculator();

    public GPACalculatorGUI() {
        setTitle("GPA Calculator");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JLabel headerLabel = new JLabel("GPA Calculator", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(70, 130, 180));
        headerLabel.setForeground(Color.WHITE);
        add(headerLabel, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Input Fields
        JLabel subjectLabel = new JLabel("Subject:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(subjectLabel, gbc);

        subjectField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(subjectField, gbc);

        JLabel creditsLabel = new JLabel("Credits:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(creditsLabel, gbc);

        creditsField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(creditsField, gbc);

        JLabel gradeLabel = new JLabel("Grade:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(gradeLabel, gbc);

        gradeComboBox = new JComboBox<>(new String[]{"A", "B", "C", "D", "F"});
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(gradeComboBox, gbc);

        JButton addSubjectButton = new JButton("Add Subject");
        addSubjectButton.addActionListener(this::addSubject);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(addSubjectButton, gbc);

        add(mainPanel, BorderLayout.WEST);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout(10, 10));
        tableModel = new DefaultTableModel(new Object[]{"Subject", "Credits", "Grade"}, 0);
        subjectTable = new JTable(tableModel);
        subjectTable.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(subjectTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel(new GridBagLayout());
        JLabel gpaLabel = new JLabel("GPA:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        footerPanel.add(gpaLabel, gbc);

        gpaTextField = new JTextField(10);
        gpaTextField.setEditable(false);
        gbc.gridx = 1;
        footerPanel.add(gpaTextField, gbc);

        JButton calculateGPAButton = new JButton("Calculate GPA");
        calculateGPAButton.addActionListener(this::calculateGPA);
        gbc.gridx = 2;
        footerPanel.add(calculateGPAButton, gbc);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private void addSubject(ActionEvent e) {
        try {
            String subject = subjectField.getText().trim();
            int credits = Integer.parseInt(creditsField.getText().trim());
            String grade = (String) gradeComboBox.getSelectedItem();

            if (subject.isEmpty()) {
                throw new IllegalArgumentException("Subject name cannot be empty.");
            }
            if (credits <= 0) {
                throw new IllegalArgumentException("Credits must be a positive number.");
            }

            // Convert grade to points
            double gradePoints = gradeToPoints(grade);
            gpaCalculator.addSubject(credits, gradePoints);  // Add the subject to GPA calculator

            // Update the table model
            tableModel.addRow(new Object[]{subject, credits, grade});  // Update table with new subject

            // Clear input fields
            subjectField.setText("");
            creditsField.setText("");
            gradeComboBox.setSelectedIndex(0);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for credits.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void calculateGPA(ActionEvent e) {
        try {
            double gpa = gpaCalculator.calculateGPA();
            gpaTextField.setText(String.format("%.2f", gpa));
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private double gradeToPoints(String grade) {
        return switch (grade) {
            case "A" -> 4.0;
            case "B" -> 3.0;
            case "C" -> 2.0;
            case "D" -> 1.0;
            default -> 0.0;
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GPACalculatorGUI().setVisible(true));
    }
}

class GPACalculator {
    private final ArrayList<Integer> credits = new ArrayList<>();
    private final ArrayList<Double> grades = new ArrayList<>();

    public void addSubject(int credit, double grade) {
        credits.add(credit);
        grades.add(grade);
    }

    public double calculateGPA() {
        if (credits.isEmpty()) {
            throw new IllegalStateException("No subjects added. Please add subjects first.");
        }

        int totalCredits = 0;
        double totalGradePoints = 0.0;

        for (int i = 0; i < credits.size(); i++) {
            totalCredits += credits.get(i);
            totalGradePoints += credits.get(i) * grades.get(i);
        }

        return totalGradePoints / totalCredits;
    }
}

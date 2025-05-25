import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class CGPACalculatorGUI extends JFrame {
    private JTextField semesterField;
    private JTextField creditsField;
    private JTextField gpaField;
    private JTable semesterTable;
    private DefaultTableModel tableModel;
    private JTextField cgpaTextField;

    private final CGPACalculator cgpaCalculator = new CGPACalculator();

    public CGPACalculatorGUI() {
        setTitle("CGPA Calculator");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JLabel headerLabel = new JLabel("CGPA Calculator", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(70, 130, 180));
        headerLabel.setForeground(Color.WHITE);
        add(headerLabel, BorderLayout.NORTH);

        // Main Panel for Semester, GPA and Credits
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Input Fields
        JLabel semesterLabel = new JLabel("Semester:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(semesterLabel, gbc);

        semesterField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(semesterField, gbc);

        JLabel gpaLabel = new JLabel("GPA:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(gpaLabel, gbc);

        gpaField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(gpaField, gbc);

        JLabel creditsLabel = new JLabel("Credits:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(creditsLabel, gbc);

        creditsField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(creditsField, gbc);

        JButton addSemesterButton = new JButton("Add Semester");
        addSemesterButton.addActionListener(this::addSemester);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(addSemesterButton, gbc);

        add(mainPanel, BorderLayout.WEST);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout(10, 10));
        tableModel = new DefaultTableModel(new Object[]{"Semester", "GPA", "Credits"}, 0);
        semesterTable = new JTable(tableModel);
        semesterTable.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(semesterTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);

        // Footer Panel to display CGPA
        JPanel footerPanel = new JPanel(new GridBagLayout());
        JLabel cgpaLabel = new JLabel("CGPA:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        footerPanel.add(cgpaLabel, gbc);

        cgpaTextField = new JTextField(10);
        cgpaTextField.setEditable(false);
        gbc.gridx = 1;
        footerPanel.add(cgpaTextField, gbc);

        JButton calculateCGPAButton = new JButton("Calculate CGPA");
        calculateCGPAButton.addActionListener(this::calculateCGPA);
        gbc.gridx = 2;
        footerPanel.add(calculateCGPAButton, gbc);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private void addSemester(ActionEvent e) {
        try {
            String semester = semesterField.getText().trim();
            double gpa = Double.parseDouble(gpaField.getText().trim());
            int credits = Integer.parseInt(creditsField.getText().trim());

            if (semester.isEmpty()) {
                throw new IllegalArgumentException("Semester name cannot be empty.");
            }
            if (credits <= 0) {
                throw new IllegalArgumentException("Credits must be a positive number.");
            }

            cgpaCalculator.addSemester(gpa, credits);
            tableModel.addRow(new Object[]{semester, gpa, credits});

            // Clear input fields
            semesterField.setText("");
            gpaField.setText("");
            creditsField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for GPA and Credits.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void calculateCGPA(ActionEvent e) {
        try {
            double cgpa = cgpaCalculator.calculateCGPA();
            cgpaTextField.setText(String.format("%.2f", cgpa));
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CGPACalculatorGUI().setVisible(true));
    }
}

class CGPACalculator {
    private final ArrayList<Double> gpas = new ArrayList<>();
    private final ArrayList<Integer> credits = new ArrayList<>();

    public void addSemester(double gpa, int credits) {
        this.gpas.add(gpa);
        this.credits.add(credits);
    }

    public double calculateCGPA() {
        if (gpas.isEmpty()) {
            throw new IllegalStateException("No semesters added. Please add semesters first.");
        }

        int totalCredits = 0;
        double totalGPAWeighted = 0.0;

        for (int i = 0; i < gpas.size(); i++) {
            totalCredits += credits.get(i);
            totalGPAWeighted += gpas.get(i) * credits.get(i);
        }

        return totalGPAWeighted / totalCredits;
    }
}

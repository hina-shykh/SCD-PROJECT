import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    private JButton calculateGPAButton;
    private JButton calculateCGPAButton;

    public HomePage() {
        setTitle("Home Page");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel welcomeLabel = new JLabel("Welcome to the GPA/CGPA Calculator");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(welcomeLabel);

        // GPA Button
        calculateGPAButton = new JButton("Start GPA Calculation");
        calculateGPAButton.setBackground(new Color(100, 255, 100)); // Light green background
        calculateGPAButton.setForeground(Color.BLACK);  // Black text
        calculateGPAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GPACalculatorGUI().setVisible(true);  // Open GPA calculation GUI
            }
        });
        add(calculateGPAButton);

        // CGPA Button
        calculateCGPAButton = new JButton("Start CGPA Calculation");
        calculateCGPAButton.setBackground(new Color(100, 255, 255)); // Light cyan background
        calculateCGPAButton.setForeground(Color.BLACK);  // Black text
        calculateCGPAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CGPACalculatorGUI().setVisible(true);  // Open CGPA calculation GUI
            }
        });
        add(calculateCGPAButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomePage().setVisible(true);  // Display home page
            }
        });
    }
}

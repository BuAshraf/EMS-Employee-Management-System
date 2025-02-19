package org.EMS.BackEnd.GUI;
import javax.swing.BorderFactory;
import javax.swing.*;
import java.awt.*;

public class GUI {
    private  JFrame frame;

    public  GUI() {

        SwingUtilities.invokeLater(() -> {  // Ensure GUI runs in the EDT
            frame = new JFrame("Employee Management System");
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
            panel.setLayout(new GridLayout(0, 1));

           // Add Label
            // ✅ Ensure text matches exactly (no extra spaces)
            JLabel titleLabel = new JLabel("Employee Management System", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            panel.add(titleLabel);


            // Add Panel to Frame
            frame.add(panel, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLocationRelativeTo(null); // Center the window
            frame.setVisible(true);
        });

    }
    public JFrame getFrame() {
        return frame;
    }

    public void show() {
        frame.setVisible(true);
    }

}

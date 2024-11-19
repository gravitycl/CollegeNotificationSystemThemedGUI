import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


interface Notification {
    void send(String message);
}

class EmailNotification implements Notification {
    private String email;

    public EmailNotification(String email) {
        this.email = email;
    }

    @Override
    public void send(String message) {
        JOptionPane.showMessageDialog(null, "Email sent to " + email + ": " + message, "Notification", JOptionPane.INFORMATION_MESSAGE);
    }
}


class SMSNotification implements Notification {
    private String phoneNumber;

    public SMSNotification(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void send(String message) {
        JOptionPane.showMessageDialog(null, "SMS sent to " + phoneNumber + ": " + message, "Notification", JOptionPane.INFORMATION_MESSAGE);
    }
}


class PushNotification implements Notification {
    private String deviceID;

    public PushNotification(String deviceID) {
        this.deviceID = deviceID;
    }

    @Override
    public void send(String message) {
        JOptionPane.showMessageDialog(null, "Push notification sent to device " + deviceID + ": " + message, "Notification", JOptionPane.INFORMATION_MESSAGE);
    }
}


class Student {
    private String name;
    private List<Notification> notificationPreferences;

    public Student(String name) {
        this.name = name;
        this.notificationPreferences = new ArrayList<>();
    }

    public void addNotificationPreference(Notification notification) {
        notificationPreferences.add(notification);
    }

    public void notifyStudent(String message) {
        for (Notification notification : notificationPreferences) {
            notification.send(message);
        }
    }

    public String getName() {
        return name;
    }
}


public class CollegeNotificationSystemThemedGUI {
    private List<Student> students = new ArrayList<>();

    public CollegeNotificationSystemThemedGUI() {
        JFrame frame = new JFrame("College Notification Alert System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        
        JLabel titleLabel = new JLabel("College Notification System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(titleLabel, BorderLayout.NORTH);

       
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(inputPanel, BorderLayout.CENTER);

        JLabel nameLabel = new JLabel("Student Name:");
        JTextField nameField = new JTextField();

        JLabel emailLabel = new JLabel("Email Address:");
        JTextField emailField = new JTextField();

        JLabel phoneLabel = new JLabel("Phone Number:");
        JTextField phoneField = new JTextField();

        JLabel deviceLabel = new JLabel("Device ID:");
        JTextField deviceField = new JTextField();

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);
        inputPanel.add(deviceLabel);
        inputPanel.add(deviceField);

        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        frame.add(buttonPanel, BorderLayout.SOUTH);

        JButton addButton = new JButton("Add Student");
        JButton notifyButton = new JButton("Send Notification");

        buttonPanel.add(addButton);
        buttonPanel.add(notifyButton);

        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();
                String device = deviceField.getText().trim();

                if (name.isEmpty() || (email.isEmpty() && phone.isEmpty() && device.isEmpty())) {
                    JOptionPane.showMessageDialog(frame, "Please fill out the student's name and at least one contact method.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Student student = new Student(name);
                if (!email.isEmpty()) student.addNotificationPreference(new EmailNotification(email));
                if (!phone.isEmpty()) student.addNotificationPreference(new SMSNotification(phone));
                if (!device.isEmpty()) student.addNotificationPreference(new PushNotification(device));

                students.add(student);
                JOptionPane.showMessageDialog(frame, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                
                nameField.setText("");
                emailField.setText("");
                phoneField.setText("");
                deviceField.setText("");
            }
        });

        
        notifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (students.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No students available to notify.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String message = JOptionPane.showInputDialog(frame, "Enter notification message:");
                if (message == null || message.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Message cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                for (Student student : students) {
                    student.notifyStudent(message);
                }
                JOptionPane.showMessageDialog(frame, "Notifications sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

       
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf()); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(CollegeNotificationSystemThemedGUI::new);
    }
}
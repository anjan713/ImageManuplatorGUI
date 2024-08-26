package com.imagemanipulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageManipulatorGUI extends JFrame {
    private ImageProcessor imageProcessor;
    private JLabel imageLabel;
    private JButton loadButton, saveButton, grayscaleButton, resizeButton, rotateButton;

    public ImageManipulatorGUI() {
        imageProcessor = new ImageProcessor();
        initComponents();
    }

    private void initComponents() {
        setTitle("Image Manipulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        add(new JScrollPane(imageLabel), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        loadButton = new JButton("Load Image");
        saveButton = new JButton("Save Image");
        grayscaleButton = new JButton("Convert to Grayscale");
        resizeButton = new JButton("Resize");
        rotateButton = new JButton("Rotate");

        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(grayscaleButton);
        buttonPanel.add(resizeButton);
        buttonPanel.add(rotateButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadButton.addActionListener(this::loadImage);
        saveButton.addActionListener(this::saveImage);
        grayscaleButton.addActionListener(this::convertToGrayscale);
        resizeButton.addActionListener(this::resizeImage);
        rotateButton.addActionListener(this::rotateImage);
    }

    private void loadImage(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                imageProcessor.loadImage(selectedFile);
                updateImageDisplay();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage());
            }
        }
    }

    private void saveImage(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                imageProcessor.saveImage(selectedFile);
                JOptionPane.showMessageDialog(this, "Image saved successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving image: " + ex.getMessage());
            }
        }
    }

    private void convertToGrayscale(ActionEvent e) {
        imageProcessor.convertToGrayscale();
        updateImageDisplay();
    }

    private void resizeImage(ActionEvent e) {
        String input = JOptionPane.showInputDialog(this, "Enter new width and height (e.g., 800 600):");
        if (input != null) {
            String[] dimensions = input.split(" ");
            if (dimensions.length == 2) {
                try {
                    int width = Integer.parseInt(dimensions[0]);
                    int height = Integer.parseInt(dimensions[1]);
                    imageProcessor.resize(width, height);
                    updateImageDisplay();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter two numbers.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter width and height separated by a space.");
            }
        }
    }

    private void rotateImage(ActionEvent e) {
        String input = JOptionPane.showInputDialog(this, "Enter rotation angle in degrees:");
        if (input != null) {
            try {
                double angle = Double.parseDouble(input);
                imageProcessor.rotate(angle);
                updateImageDisplay();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter a number.");
            }
        }
    }

    private void updateImageDisplay() {
        BufferedImage image = imageProcessor.getImage();
        if (image != null) {
            ImageIcon icon = new ImageIcon(image);
            imageLabel.setIcon(icon);
            imageLabel.revalidate();
            imageLabel.repaint();
        }
    }
}
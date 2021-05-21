package de.moinFT.main;

import de.moinFT.utils.GUIComponent;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    public GUI() {
        this.setTitle("Calculator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 600, 450);

        JPanel contentPane = GUIComponent.JPanel();
        this.setContentPane(contentPane);

        JLabel Lbl_Input = GUIComponent.Label(contentPane, Font.BOLD, 13);
        Lbl_Input.setText("Input:");
        Lbl_Input.setBounds(10, 10, 200, 25);

        JTextField TF_Input = GUIComponent.TextField(contentPane);
        TF_Input.setBounds(10, 45, 250, 25);

        JButton Btn_Calculate = GUIComponent.Button(contentPane);
        Btn_Calculate.setText("Calculate");
        Btn_Calculate.setBounds(10, 80, 100, 25);
        Btn_Calculate.addActionListener(event -> {
            FunctionParser.parse(TF_Input.getText());
        });

        JLabel Lbl_Output = GUIComponent.Label(contentPane, Font.BOLD, 13);
        Lbl_Output.setText("Output:");
        Lbl_Output.setBounds(10, 115, 200, 25);

        JTextField TF_Output = GUIComponent.TextField(contentPane);
        TF_Output.setBounds(10, 150, 250, 25);
        TF_Output.setEditable(false);

        this.setVisible(true);
    }
}
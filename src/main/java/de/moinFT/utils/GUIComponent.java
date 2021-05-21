package de.moinFT.utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GUIComponent {
    public static JPanel JPanel() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        contentPane.setBackground(GUIColor.GUIBackground_Color);

        return contentPane;
    }

    public static JTextField TextField(JPanel contentPane) {
        JTextField TField = new JTextField();
        TField.setBackground(GUIColor.Background_TField_Color);
        TField.setForeground(GUIColor.Font_TField_Color);
        TField.setBorder(null);
        contentPane.add(TField);

        return TField;
    }

    public static JButton Button(JPanel contentPane) {
        JButton Btn = new JButton();
        Btn.setForeground(GUIColor.Font_Button_Color);
        Btn.setBackground(GUIColor.Background_Button_Color);
        contentPane.add(Btn);

        return Btn;
    }

    public static JLabel Label(JPanel contentPane, int font, int fontSize) {
        JLabel Lbl = new JLabel();
        Lbl.setFont(new Font("", font, fontSize));
        Lbl.setForeground(GUIColor.Font_Label_Color);
        contentPane.add(Lbl);
        return Lbl;
    }
}
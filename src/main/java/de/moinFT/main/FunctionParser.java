package de.moinFT.main;

import de.moinFT.utils.FunctionNumbers;
import de.moinFT.utils.FunctionParts;
import de.moinFT.utils.FunctionSymbols;

import javax.swing.*;

public class FunctionParser {
    private static FunctionParts functionParts = new FunctionParts();
    private static FunctionNumbers functionNumbers = new FunctionNumbers();
    private static FunctionSymbols functionSymbols = new FunctionSymbols();

    public static boolean parse(String function) {
        System.out.println(function);
        boolean is_AfterOpenBraket = false;
        boolean is_AfterCloseBraket = false;
        boolean is_AfterNumber = false;
        boolean is_AfterSymbol = false;

        boolean is_error = false;

        for (int counter = 0; (counter + 1) <= function.length() && !is_error; counter++) {
            System.out.println(function.substring(counter, counter + 1));
            char symbol = function.substring(counter, counter + 1).charAt(0);
            switch (function.substring(counter, counter + 1)) {
                case "-":
                case "+":
                case "/":
                case "*":
                case "^":
                    if (is_AfterSymbol) {
                        JOptionPane.showMessageDialog(null, "Fehler bei der Eingabe!");
                        is_error = true;
                    } else {
                        is_AfterSymbol = true;
                        is_AfterNumber = false;
                        is_AfterOpenBraket = false;
                        is_AfterCloseBraket = false;
                        int id = functionParts.set(false, true, symbol);
                        functionSymbols.set(id, symbol);
                    }
                    break;
                case "(":
                    if (is_AfterNumber || is_AfterCloseBraket) {
                        JOptionPane.showMessageDialog(null, "Fehler bei der Eingabe!");
                        is_error = true;
                    } else {
                        is_AfterSymbol = false;
                        is_AfterNumber = false;
                        is_AfterOpenBraket = true;
                        is_AfterCloseBraket = false;
                        int id = functionParts.set(false, true, symbol);
                        functionSymbols.set(id, symbol);
                    }
                    break;
                case ")":
                    if (is_AfterOpenBraket || is_AfterSymbol) {
                        JOptionPane.showMessageDialog(null, "Fehler bei der Eingabe!");
                        is_error = true;
                    } else {
                        is_AfterCloseBraket = true;
                    }
                    break;
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                    is_AfterSymbol = false;
                    is_AfterNumber = true;
                    is_AfterOpenBraket = false;
                    is_AfterCloseBraket = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Fehler bei der Eingabe!");
                    is_error = true;
                    break;
            }
        }

        System.out.println(!is_error);

        return !is_error;
    }
}
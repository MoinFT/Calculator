package de.moinFT.main;

import javax.swing.*;

import static de.moinFT.main.Main.*;

public class FunctionOptions {
    public static boolean parse(String function) {
        System.out.println(function);
        boolean is_AfterOpenBracket = false;
        boolean is_AfterCloseBracket = false;
        boolean is_AfterNumber = false;
        boolean is_AfterSymbol = false;

        boolean is_error = false;

        for (int counter = 0; (counter + 1) <= function.length() && !is_error; counter++) {
            String symbol = function.substring(counter, counter + 1);
            switch (function.substring(counter, counter + 1)) {
                case "-":
                case "+":
                    if (is_AfterSymbol) {
                        JOptionPane.showMessageDialog(null, "Fehler bei der Eingabe!");
                        is_error = true;
                    } else {
                        is_AfterSymbol = true;
                        is_AfterNumber = false;
                        is_AfterOpenBracket = false;
                        is_AfterCloseBracket = false;
                        functionParts.set(false, true, false, symbol);
                    }
                    break;
                case "/":
                case "*":
                case "^":
                    if (is_AfterSymbol || is_AfterOpenBracket) {
                        JOptionPane.showMessageDialog(null, "Fehler bei der Eingabe!");
                        is_error = true;
                    } else {
                        is_AfterSymbol = true;
                        is_AfterNumber = false;
                        is_AfterOpenBracket = false;
                        is_AfterCloseBracket = false;
                        functionParts.set(false, true, false, symbol);
                    }
                    break;
                case "(":
                    if (is_AfterNumber || is_AfterCloseBracket) {
                        JOptionPane.showMessageDialog(null, "Fehler bei der Eingabe!");
                        is_error = true;
                    } else {
                        is_AfterSymbol = false;
                        is_AfterNumber = false;
                        is_AfterOpenBracket = true;
                        is_AfterCloseBracket = false;
                        functionParts.set(false, true, true, symbol);
                    }
                    break;
                case ")":
                    if (is_AfterOpenBracket || is_AfterSymbol) {
                        JOptionPane.showMessageDialog(null, "Fehler bei der Eingabe!");
                        is_error = true;
                    } else {
                        is_AfterNumber = false;
                        is_AfterOpenBracket = false;
                        is_AfterCloseBracket = true;
                        functionParts.set(false, true, true, symbol);
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
                    if (is_AfterNumber) {
                        int count = functionParts.count();

                        String lastNumber = functionParts.get(count - 1).getFunctionPart();

                        functionParts.get(count - 1).setNew(true, false, false, lastNumber + symbol);
                    } else {
                        functionParts.set(true, false, false, symbol);
                    }

                    is_AfterSymbol = false;
                    is_AfterNumber = true;
                    is_AfterOpenBracket = false;
                    is_AfterCloseBracket = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Fehler bei der Eingabe!");
                    is_error = true;
                    break;
            }
        }

        System.out.println("Correct Syntax: " + !is_error);

        return !is_error;
    }

    public static void optimizeNumbers() {
        int count = functionParts.count();

        for (int i = 0; i < count; i++) {
            if (functionParts.get(i).isNumber()) {
                String number = functionParts.get(i).getFunctionPart();

                if (i == 1) {
                    if (functionParts.get(0).isSymbol()) {
                        if (functionParts.get(0).getFunctionPart().equals("-")){
                            number = "" + (Integer.parseInt(number) * -1);
                            functionParts.get(0).setNew(true, false, false, number);
                            functionParts.get(1).delete();
                            count = functionParts.count();
                        }
                    }
                } else if (i > 1) {
                    if (functionParts.get(i - 2).isBracket()) {
                        if (functionParts.get(i - 1).isSymbol()) {
                            if (functionParts.get(i - 1).getFunctionPart().equals("-")) {
                                number = "" + (Integer.parseInt(number) * -1);
                                functionParts.get(i - 1).setNew(true, false, false, number);
                                functionParts.get(i).delete();
                                count = functionParts.count();
                            }
                        }
                    }
                }
            }
        }
    }

    public static void optimizeBrackets() {
        int count = functionParts.count();

        int openBracketID = -1;
        int closeBracketID;

        for (int i = 0; i < count; i++) {
            if (functionParts.get(i).isBracket()) {
                if (functionParts.get(i).getFunctionPart().equals("(")) {
                    openBracketID = i;
                }

                if (functionParts.get(i).getFunctionPart().equals(")")) {
                    closeBracketID = i;

                    int bracketDifferent = closeBracketID - openBracketID;

                    if (bracketDifferent == 2) {
                        functionParts.get(closeBracketID).delete();
                        functionParts.get(openBracketID).delete();
                        count = functionParts.count();
                    }
                }
            }
        }
    }
}
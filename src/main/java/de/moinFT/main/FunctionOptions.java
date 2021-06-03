package de.moinFT.main;

import javax.swing.*;

import static de.moinFT.main.Main.functionParts;

public class FunctionOptions {
    private static final String[] sin_cos_tan_mathOperators = {"sin", "cos", "tan", "asin", "acos", "atan", "sinh", "cosh", "tanh"};
    private static final String[] normal_mathOperators = {"+", "-", "*", "/", "^", "(", ")"};

    public static boolean parse(String function) {
        System.out.println(function);
        int openBracketCounter = 0;
        int closeBracketCounter = 0;
        boolean is_AfterOpenBracket = false;
        boolean is_AfterCloseBracket = false;
        boolean is_AfterNumber = false;
        boolean is_AfterDot = false;
        boolean is_AfterSymbol = false;
        boolean is_AfterLetter = false;

        boolean is_error = false;

        for (int counter = 0; (counter + 1) <= function.length() && !is_error; counter++) {
            String symbol = function.substring(counter, counter + 1);
            switch (function.substring(counter, counter + 1)) {
                case "-":
                case "+":
                    if (is_AfterSymbol || is_AfterDot) {
                        JOptionPane.showMessageDialog(null, "Fehler bei der Eingabe!");
                        is_error = true;
                    } else {
                        is_AfterSymbol = true;
                        is_AfterNumber = false;
                        is_AfterOpenBracket = false;
                        is_AfterCloseBracket = false;
                        is_AfterLetter = false;
                        functionParts.set(false, true, false, symbol);
                    }
                    break;
                case "/":
                case "*":
                case "^":
                    if (is_AfterSymbol || is_AfterOpenBracket || is_AfterDot) {
                        JOptionPane.showMessageDialog(null, "Fehler bei der Eingabe!");
                        is_error = true;
                    } else {
                        is_AfterSymbol = true;
                        is_AfterNumber = false;
                        is_AfterOpenBracket = false;
                        is_AfterCloseBracket = false;
                        is_AfterLetter = false;
                        functionParts.set(false, true, false, symbol);
                    }
                    break;
                case "(":
                    if (is_AfterNumber || is_AfterCloseBracket) {
                        JOptionPane.showMessageDialog(null, "Fehler bei der Eingabe!");
                        is_error = true;
                    } else {
                        openBracketCounter++;
                        is_AfterSymbol = false;
                        is_AfterNumber = false;
                        is_AfterOpenBracket = true;
                        is_AfterCloseBracket = false;
                        is_AfterLetter = false;
                        functionParts.set(false, true, true, symbol);
                    }
                    break;
                case ")":
                    if (is_AfterOpenBracket || is_AfterSymbol || is_AfterDot) {
                        JOptionPane.showMessageDialog(null, "Fehler bei der Eingabe!");
                        is_error = true;
                    } else {
                        if (openBracketCounter < closeBracketCounter + 1) {
                            is_error = true;
                        }

                        closeBracketCounter++;
                        is_AfterNumber = false;
                        is_AfterOpenBracket = false;
                        is_AfterCloseBracket = true;
                        is_AfterLetter = false;
                        functionParts.set(false, true, true, symbol);
                    }
                    break;
                case ".":
                    if (counter == 0 || !is_AfterNumber || is_AfterDot) {
                        JOptionPane.showMessageDialog(null, "Fehler bei der Eingabe!");
                        is_error = true;
                    } else {
                        int count = functionParts.count();
                        String lastNumber = functionParts.get(count - 1).getFunctionPart();

                        functionParts.get(count - 1).setNew(true, false, false, lastNumber + symbol);
                        is_AfterDot = true;
                        is_AfterNumber = true;
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
                case "0":
                    if (is_AfterNumber) {
                        int count = functionParts.count();
                        String lastNumber = functionParts.get(count - 1).getFunctionPart();

                        functionParts.get(count - 1).setNew(true, false, false, lastNumber + symbol);
                    } else {
                        functionParts.set(true, false, false, symbol);
                    }
                    is_AfterSymbol = false;
                    is_AfterNumber = true;
                    is_AfterDot = false;
                    is_AfterOpenBracket = false;
                    is_AfterCloseBracket = false;
                    is_AfterLetter = false;
                    break;
                default:
                    if (is_AfterDot || is_AfterCloseBracket) {
                        JOptionPane.showMessageDialog(null, "Fehler bei der Eingabe!");
                        is_error = true;
                    } else {
                        if (is_AfterLetter) {
                            int count = functionParts.count();
                            String lastLetter = functionParts.get(count - 1).getFunctionPart();

                            functionParts.get(count - 1).setNew(false, true, false, lastLetter + symbol);
                        } else {
                            functionParts.set(false, true, false, symbol);
                        }
                        is_AfterLetter = true;
                        is_AfterSymbol = true;
                        is_AfterOpenBracket = false;
                        is_AfterCloseBracket = false;
                        is_AfterNumber = false;
                    }
                    break;
            }
        }

        if (openBracketCounter != closeBracketCounter) {
            is_error = true;
        }

        int count = functionParts.count();
        for (int i = 0; i < count; i++) {
            if (functionParts.get(i).isSymbol()) {
                boolean is_CorrectSymbol = false;

                for (String temp : normal_mathOperators) {
                    if (functionParts.get(i).getFunctionPart().equals(temp)) {
                        is_CorrectSymbol = true;
                        break;
                    }
                }

                for (String temp : sin_cos_tan_mathOperators) {
                    if (functionParts.get(i).getFunctionPart().equals(temp)) {
                        is_CorrectSymbol = true;
                        break;
                    }
                }

                if (!is_CorrectSymbol) {
                    is_error = true;
                    break;
                }
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
                        if (functionParts.get(0).getFunctionPart().equals("-")) {
                            number = "" + (Double.parseDouble(number) * -1);
                            functionParts.get(0).setNew(true, false, false, number);
                            functionParts.get(1).delete();
                            count = functionParts.count();
                            i -= 1;
                        } else if (functionParts.get(0).getFunctionPart().equals("+")) {
                            functionParts.get(0).setNew(true, false, false, number);
                            functionParts.get(1).delete();
                            count = functionParts.count();
                            i -= 1;
                        }
                    }
                } else if (i > 1) {
                    if (functionParts.get(i - 2).getFunctionPart().equals("(")) {
                        if (functionParts.get(i - 1).isSymbol()) {
                            if (functionParts.get(i - 1).getFunctionPart().equals("-")) {
                                number = "" + (Double.parseDouble(number) * -1);
                                functionParts.get(i - 1).setNew(true, false, false, number);
                                functionParts.get(i).delete();
                                count = functionParts.count();
                                i -= 1;
                            } else if (functionParts.get(i - 1).getFunctionPart().equals("+")) {
                                functionParts.get(i - 1).setNew(true, false, false, number);
                                functionParts.get(i).delete();
                                count = functionParts.count();
                                i -= 1;
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
                        i -= 1;
                    }
                }
            }
        }
    }

    public static void calculation() {
        bracketCalculation();
        sin_cos_tan_Calculation(0);
        exponential_Calculation(0);
        multiplication_dividing_Calculation(0);
        addition_subtraction_Calculation(0);

        System.out.println(Main.functionParts.get(0).getFunctionPart());
    }

    private static void bracketCalculation() {
        int count = functionParts.count();
        int countOpenBrackets = functionParts.countOpenBrackets();

        while (0 != countOpenBrackets) {
            int openBrackets = 0;
            for (int i = 0; i < count; i++) {
                if (functionParts.get(i).getFunctionPart().equals("(")) {
                    openBrackets++;
                }

                if (openBrackets == countOpenBrackets) {
                    sin_cos_tan_Calculation(i + 1);
                    exponential_Calculation(i + 1);
                    multiplication_dividing_Calculation(i + 1);
                    addition_subtraction_Calculation(i + 1);
                    optimizeBrackets();
                    break;
                }
            }

            count = functionParts.count();
            countOpenBrackets = functionParts.countOpenBrackets();
        }
    }

    private static void addition_subtraction_Calculation(int startIndex) {
        int count = functionParts.count();

        for (int i = startIndex; i < count; i++) {
            if (functionParts.get(i).isSymbol()) {
                String functionPart = functionParts.get(i).getFunctionPart();

                if (functionPart.equals("+") || functionPart.equals("-")) {
                    double number1 = Double.parseDouble(functionParts.get(i - 1).getFunctionPart());
                    double number2 = Double.parseDouble(functionParts.get(i + 1).getFunctionPart());

                    String numberResult = "";

                    switch (functionPart) {
                        case "+" -> numberResult = "" + (number1 + number2);
                        case "-" -> numberResult = "" + (number1 - number2);
                    }

                    functionParts.get(i).setNew(true, false, false, numberResult);
                    functionParts.get(i + 1).delete();
                    functionParts.get(i - 1).delete();
                    count = functionParts.count();
                    i -= 1;
                } else if (functionParts.get(i).isBracket()) {
                    return;
                }
            }
        }
    }

    private static void multiplication_dividing_Calculation(int startIndex) {
        int count = functionParts.count();
        for (int i = startIndex; i < count; i++) {
            if (functionParts.get(i).isSymbol()) {
                String functionPart = functionParts.get(i).getFunctionPart();

                if (functionPart.equals("*") || functionPart.equals("/")) {
                    double number1 = Double.parseDouble(functionParts.get(i - 1).getFunctionPart());
                    double number2 = Double.parseDouble(functionParts.get(i + 1).getFunctionPart());

                    String numberResult = "";

                    switch (functionPart) {
                        case "*" -> numberResult = "" + (number1 * number2);
                        case "/" -> numberResult = "" + (number1 / number2);
                    }

                    functionParts.get(i).setNew(true, false, false, numberResult);
                    functionParts.get(i + 1).delete();
                    functionParts.get(i - 1).delete();
                    count = functionParts.count();
                    i -= 1;
                } else if (functionParts.get(i).isBracket()) {
                    return;
                }
            }
        }
    }

    private static void exponential_Calculation(int startIndex) {
        int count = functionParts.count();
        for (int i = startIndex; i < count; i++) {
            if (functionParts.get(i).isSymbol()) {
                String functionPart = functionParts.get(i).getFunctionPart();

                if (functionPart.equals("^")) {
                    double number1 = Double.parseDouble(functionParts.get(i - 1).getFunctionPart());
                    double number2 = Double.parseDouble(functionParts.get(i + 1).getFunctionPart());

                    String numberResult = "" + Math.pow(number1, number2);

                    functionParts.get(i).setNew(true, false, false, numberResult);
                    functionParts.get(i + 1).delete();
                    functionParts.get(i - 1).delete();
                    count = functionParts.count();
                    i -= 1;
                } else if (functionParts.get(i).isBracket()) {
                    return;
                }
            }
        }
    }

    private static void sin_cos_tan_Calculation(int startIndex) {
        int count = functionParts.count();

        for (int i = startIndex; i < count; i++) {
            if (functionParts.get(i).isSymbol()) {
                String functionPart = functionParts.get(i).getFunctionPart();

                boolean is_RightSymbol = false;
                for (String temp : sin_cos_tan_mathOperators) {
                    if (functionPart.equals(temp)) {
                        is_RightSymbol = true;
                        break;
                    }
                }

                if (is_RightSymbol) {
                    double number = Double.parseDouble(functionParts.get(i + 1).getFunctionPart());

                    String numberResult = "";

                    switch (functionPart) {
                        case "sin" -> numberResult = "" + Math.sin(Math.toRadians(number));
                        case "cos" -> numberResult = "" + Math.cos(Math.toRadians(number));
                        case "tan" -> numberResult = "" + Math.tan(Math.toRadians(number));
                        case "asin" -> numberResult = "" + Math.toDegrees(Math.asin(number));
                        case "acos" -> numberResult = "" + Math.toDegrees(Math.acos(number));
                        case "atan" -> numberResult = "" + Math.toDegrees(Math.atan(number));
                        case "sinh" -> numberResult = "" + Math.sinh(number);
                        case "cosh" -> numberResult = "" + Math.cosh(number);
                        case "tanh" -> numberResult = "" + Math.tanh(number);
                    }

                    functionParts.get(i).setNew(true, false, false, numberResult);
                    functionParts.get(i + 1).delete();
                    count = functionParts.count();
                    i -= 1;
                } else if (functionParts.get(i).isBracket()) {
                    return;
                }
            }
        }
    }
}
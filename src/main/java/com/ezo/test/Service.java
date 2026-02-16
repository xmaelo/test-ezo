package com.ezo.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Service implements IService {

    private boolean isNumber(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> parse(String input) {
        List<String> result = new ArrayList<>();
        if (input == null) {
            return result;
        }

        StringBuilder buffer = new StringBuilder();
        int i = 0;

        while (i < input.length()) {
            char c = input.charAt(i);

            if (Character.isDigit(c) ||
                    (c == '-' && (result.isEmpty() || "+-*/(^".contains(result.get(result.size() - 1))))) {

                buffer.append(c);
                i++;

                while (i < input.length() &&
                        (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.')) {
                    buffer.append(input.charAt(i));
                    i++;
                }

                result.add(buffer.toString());
                buffer.setLength(0);
                continue;
            }

            if (!Character.isWhitespace(c)) {
                result.add(String.valueOf(c));
            }

            i++;
        }

        return result;
    }

    private void applyOp(Stack<Double> nums, Stack<String> ops) {

        if (ops.isEmpty()) {
            return;
        }

        String op = ops.pop();

        if (nums.size() < 2) {
            throw new TestException("Operation impossible");
        }

        double b = nums.pop();
        double a = nums.pop();
        double r;

        switch (op) {
            case "+":
                r = a + b;
                break;
            case "-":
                r = a - b;
                break;
            case "*":
                r = a * b;
                break;
            case "/":
                if (b == 0) {
                    throw new TestException("Division par zero");
                }
                r = a / b;
                break;
            case "^":
                r = Math.pow(a, b);
                break;
            default:
                throw new TestException("Operateur inconnu " + op);
        }

        nums.push(r);
    }

    public double calculate(List<String> tokens) {

        Stack<Double> numbers = new Stack<>();
        Stack<String> operators = new Stack<>();

        for (String token : tokens) {

            if (isNumber(token)) {
                numbers.push(Double.parseDouble(token));
            }
            else if (token.equals("(")) {
                operators.push(token);
            }
            else if (token.equals(")")) {

                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    applyOp(numbers, operators);
                }

                if (!operators.isEmpty()) {
                    operators.pop();
                }

            }
            else if ("+-*/^".contains(token)) {
                operators.push(token);
            }
            else {
                throw new TestException("Token invalide : " + token);
            }
        }

        while (!operators.isEmpty()) {
            applyOp(numbers, operators);
        }

        if (numbers.size() != 1) {
            throw new TestException("Expression incorrecte");
        }

        return numbers.pop();
    }

    @Override
    public Object run(String input) {
        try {
            List<String> tokens = parse(input);
            if (tokens.isEmpty()) {
                return "Expression vide";
            }
            return calculate(tokens);
        } catch (Exception e) {
            return "Erreur : " + e.getMessage();
        }
    }
}

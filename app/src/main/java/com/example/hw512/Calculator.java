package com.example.hw512;

import java.util.ArrayDeque;
import java.util.Deque;

public class Calculator {

    private final Deque<Character> screenData = new ArrayDeque<>();
    private final Deque<Double> operands = new ArrayDeque<>();

    Operation currentOperation = Operation.NOP;
    boolean secondOperandFlag = false;  //признак начала ввода второго операнда. своего рода костыль


    public Calculator() {
    }

    String getDigit(char digit) {
        if (currentOperation != Operation.NOP) {
            if (!secondOperandFlag) {
                secondOperandFlag = true;
                screenData.clear();
            }

        }
        if (digit == '.') {
            if (screenData.contains('.')) return dequeToString(screenData);
            if (screenData.isEmpty()) {
                screenData.add('0');
                screenData.add('.');
                return dequeToString(screenData);
            }
        }
        if (screenData.size() < 9) screenData.add(digit);
        return dequeToString(screenData);
    }

    String getOperation(Operation operation) {
        switch (operation) {
            case C:    //очистка операндов и состояний
                screenData.clear();
                currentOperation = Operation.NOP;
                return "0";
            case MUL:
                secondOperandFlag = false;
                setOperandsQueue(getCurrentOperand());
                currentOperation = Operation.MUL;
                break;
            case DIV:
                secondOperandFlag = false;
                setOperandsQueue(getCurrentOperand());
                currentOperation = Operation.DIV;
                break;
            case PLUS:
                secondOperandFlag = false;
                setOperandsQueue(getCurrentOperand());
                currentOperation = Operation.PLUS;
                break;
            case MINUS:
                secondOperandFlag = false;
                setOperandsQueue(getCurrentOperand());
                currentOperation = Operation.MINUS;
                break;
            case SIGN:
                if (screenData.isEmpty()) break;
                if (screenData.getFirst().equals('-')) screenData.removeFirst();
                else {
                    if (screenData.size() >= 9) break;
                    screenData.addFirst('-');
                }
                break;
            case EQUAL:
                if (currentOperation == Operation.NOP) break;
                setOperandsQueue(getCurrentOperand());
                getCalcResult();
                currentOperation = Operation.NOP;
                break;
            default:
                break;
        }
        if (screenData.isEmpty()) return "0";
        return dequeToString(screenData);
    }

    public double getCurrentOperand() {
        if (screenData.isEmpty()) return 0;
        return Double.parseDouble(dequeToString(screenData));
    }

    public String dequeToString(Deque<Character> deque) {
        StringBuilder sb = new StringBuilder(deque.size());
        deque.forEach(sb::append);
        return sb.toString();
    }

    public void setOperandsQueue(double op) {
        operands.add(op);
        if (operands.size() > 2) operands.removeFirst();     //этим обеспечивается скользящее окно
        //в 2 операнда
    }

    public double getCalcResult() {
        double result = 0;
        switch (currentOperation) {
            case PLUS:
                result = operands.getFirst() + operands.getLast();
                break;
            case MINUS:
                result = operands.getFirst() - operands.getLast();
                break;
            case MUL:
                result = operands.getFirst() * operands.getLast();
                break;
            case DIV:
                result = operands.getFirst() / operands.getLast();
                break;
            default:
                break;
        }

        String resultString = String.format("%.0f", result);
        screenData.clear();
        for (int i = 0; i < resultString.toCharArray().length; i++) {
            screenData.add(resultString.toCharArray()[i]);
        }
        return result;
    }


}

package com.abramova.applications.monitoring;

public class Utils {
    public String addSymbolBetweenLettersInString(String text, String symbol) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length() - 1; i++) {
            result.append(text.charAt(i)).append(symbol);
        }
        return result.append(text.charAt(text.length() - 1)).toString();
    }
}

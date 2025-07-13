package ru.PhoneDirectory;

import org.junit.Test;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ToLowerCamelCase {
    @Test
    public void test() {
        toLowerCamelCaseTest("checks users by phone number");
    }

    public void toLowerCamelCaseTest(String sentence) {

        List<String> str = Arrays.stream(sentence.toLowerCase().split("[\\s\\p{Punct}]+")).toList();
        String answer = str.stream().map(word -> word.isEmpty() ? word :
                        word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining(""));

        // Первое слово должно остаться с маленькой буквы
        if (!answer.isEmpty()) {
            answer = answer.substring(0, 1).toLowerCase() + answer.substring(1);
        }
        System.out.println(answer);
    }
}

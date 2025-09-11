package org.example;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        String regex = "^0[789]0-?\\d{4}-?\\d{4}$";
        Pattern pattern = Pattern.compile(regex);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("携帯電話番号を入力してください: ");
            String input = scanner.nextLine();

            if (pattern.matcher(input).matches()) {
                System.out.println("「" + input + "」は有効な携帯電話番号です。");
            } else {
                System.out.println("「" + input + "」は無効な携帯電話番号です。");
            }
        }

    }
}
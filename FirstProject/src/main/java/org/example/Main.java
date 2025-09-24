package org.example;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    List<Student> studentList = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    while (true) {
      displayMenu();
      int choice = getChoice(scanner);

      scanner.nextLine();

      switch (choice) {
        case 1:
          addStudent(scanner, studentList);
          break;
        case 2:
          deleteStudent(scanner, studentList);
          break;
        case 3:
          updateStudent(scanner, studentList);
          break;
        case 4:
          calculateAverage(studentList);
          break;
        case 5:
          displayAllStudents(studentList);
          break;
        case 6:
          System.out.println("プログラムを修了します。");
          scanner.close();
          return;
        default:
          System.out.println("無効な選択です。１から６の間で選んでください。");
      }
    }
  }

  public static void displayMenu() {
    System.out.println("1. 学生を追加");
    System.out.println("2. 学生を削除");
    System.out.println("3. 点数を更新");
    System.out.println("4. 平均点を計算");
    System.out.println("5. 全学生の情報を表示");
    System.out.println("6. 終了");
  }

  public static int getChoice(Scanner scanner) {
    while (true) {
      try {
        System.out.println("選択してください: ");
        return scanner.nextInt();
      } catch (InputMismatchException e) {
        System.out.println("エラー: １から６までの数字を入力してください。");
        scanner.next();
      }
    }
  }

  public static void addStudent(Scanner scanner, List<Student> students) {
    String name = getValidName(scanner, "追加する学生の名前を入力してください: ");

    System.out.print(name + "の点数を入力してください: ");
    int score = getValidScore(scanner);

    students.add(new Student(name, score));
    System.out.println(name + "さんを追加しました。");
  }

  public static void deleteStudent(Scanner scanner, List<Student> students) {
    String name = getValidName(scanner, "削除する学生の名前を入力してください: ");

    boolean removed = students.removeIf(student -> student.getName().equals(name));

    if (removed) {
      System.out.println(name + "さんを削除しました。");
    } else {
      System.out.println("エラー: " + name + "さんは見つかりませんでした");
    }
  }

  public static void updateStudent(Scanner scanner, List<Student> students) {
    String name = getValidName(scanner, "点数を更新する学生の名前を入力してください: ");

    Optional<Student> studentToUpdate = students.stream()
        .filter(s -> s.getName().equals(name))
        .findFirst();

    if (studentToUpdate.isPresent()) {
      Student student = studentToUpdate.get();
      System.out.print(name + "の新しい点数を入力してください: ");
      int newScore = getValidScore(scanner);
      student.setScore(newScore);
      System.out.println(name + "さんの点数を更新しました。");
    } else {
      System.out.println("エラー: " + name + "さんは見つかりませんでした。");
    }
  }

  public static void calculateAverage(List<Student> students) {
    if (students.isEmpty()) {
      System.out.println("学生が登録されていません。");
      return;
    }

    double average = students.stream()
        .mapToInt(Student::getScore)
        .average()
        .orElse(0.0);

    System.out.printf("平均点: %.1f点\n", average);
  }

  public static void displayAllStudents(List<Student> students) {
    if (students.isEmpty()) {
      System.out.print("学生が登録されていません。");
      return;
    }

    System.out.println("\n学生一覧:");
    for (Student student : students) {
      System.out.println(student.toString());
    }
  }

  /**
   * 名前の入力とバリデーションを行うメソッド 前後の空白を削除し、空文字列でないことを保証します。
   *
   * @param scanner 入力用スキャナー
   * @param prompt  ユーザーに表示するメッセージ
   * @return 検証済みの名前
   */

  public static String getValidName(Scanner scanner, String prompt) {
    String name;
    while (true) {
      System.out.println(prompt);
      name = scanner.nextLine().trim();
      if (!name.isEmpty()) {
        return name;
      } else {
        System.out.println("エラー: 名前が空です。再度入力してください。");
      }
    }
  }

  /**
   * 点数の入力とバリデーションを行うメソッド 0から100の範囲内の整数であることを保証します。
   *
   * @param scanner 入力用スキャナー
   * @return 検証済みの点数
   */

  public static int getValidScore(Scanner scanner) {
    while (true) {
      try {
        int score = scanner.nextInt();
        scanner.nextLine();

        if (score >= 0 && score <= 100) {
          return score;
        } else {
          System.out.print("エラー: 点数は0から100の間で入力してください。再入力してください: ");
        }
      } catch (InputMismatchException e) {
        System.out.print("エラー: 点数は整数で入力してください。再入力してください。: ");
        scanner.next();
      }
    }
  }
}

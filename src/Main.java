import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static String number;
    private static String name;

    public static void main(String[] args) {
        boolean check = true;
        String inLine;
        Map<String, String> telephoneBook = new HashMap<>();
        telephoneBook.put("375336019174", "Влад");
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Введите номер телефона или Имя:");
            inLine = scanner.nextLine().trim();
            if (checkPhoneNumber(inLine)) {
                if (telephoneBook.containsKey(number)) {
                    System.out.println("Имя:" + telephoneBook.get(number));
                    System.out.println("Номер телефона: " + number);
                } else {
                    System.out.println("Чтобы добавить новый контаккт в книгу, введите имя: ");
                    String inName = scanner.nextLine().trim();
                    if (checkName(inName)) {
                        telephoneBook.put(number, inName);
                    } else {
                        System.out.println("Имя введено неверно!");
                        continue;
                    }
                }
                System.out.println("Ты ввёл номер: " + number);
                continue;
            }
            if (checkName(inLine)) {
                System.out.println("Ты ввёл имя: " + name);
            }

        } while (check);
    }

    private static boolean checkPhoneNumber(String inNumber) {
        boolean check = false;
        if (inNumber.matches("[0-9-)(+]+")) {
            Pattern pattern = Pattern.compile("[^0-9]");
            Matcher matcher = pattern.matcher(inNumber);
            number = matcher.replaceAll("");
            if (number.length() == 12 && number.startsWith("375")) {
                check = true;
            } else if (number.length() == 11 && number.startsWith("80")) {
                number = "375" + number.substring(2);
                check = true;
            } else {
                number = null;
            }
        }
        return check;
    }

    private static boolean checkName(String inName) {
        boolean check = false;
        if (inName.matches("^[А-Яа-я]+")) {
            name = inName;
            check = true;
        } else {
            name = null;
        }
        return check;
    }
}


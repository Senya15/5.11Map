import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static String changeNumber;
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
                System.out.println("Ты ввёл номер: " + changeNumber);
                check = false;
                continue;
            }
            if (checkName(inLine)) {
                System.out.println("Ты ввёл имя: " + name);
                check = false;
            }

        } while (check);
    }

    private static boolean checkPhoneNumber(String inNumber) {
        boolean check = false;
        if (inNumber.matches("[0-9-)(+]+")) {
            Pattern pattern = Pattern.compile("[^0-9]");
            Matcher matcher = pattern.matcher(inNumber);
            changeNumber = matcher.replaceAll("");
            if (changeNumber.length() == 12 && changeNumber.startsWith("375")) {
                    check = true;
            }
            if (changeNumber.length() == 11 && changeNumber.startsWith("80")) {
                    changeNumber = "375" + changeNumber.substring(2);
                    check = true;
            } else {
                changeNumber = null;
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


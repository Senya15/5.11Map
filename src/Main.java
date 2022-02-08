import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class Main {
    private static String number;
    private static String name;
    private static String inLine;
    private static Command command;

    public static void main(String[] args) {
        boolean check = true;
        Map<String, String> telephoneBookKeyNumber = new HashMap<>();
        Map<String, String> telephoneBookKeyName = new TreeMap<>();
        telephoneBookKeyNumber.put("375336019174", "Влад");
        telephoneBookKeyName.put("Влад", "375336019174");
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Введите номер телефона, имя или одну из команд:");
            inLine = scanner.nextLine().trim();
            checkInLine();
            switch (getCommand()) {
                case LIST:
                    printMap(telephoneBookKeyName);
                    break;
                case EXIT:
                    System.out.println("Програма завершена...");
                    check = false;
                    break;
                case NUMBER:
                    if (telephoneBookKeyNumber.containsKey(number)) {
                        System.out.println("Имя: " + telephoneBookKeyNumber.get(number));
                        System.out.println("Номер телефона: " + number);
                    } else {
                        System.out.println("Контакт с таким номером телефона отсутствует!" +
                                "\n\tЧтобы добавить новый контакт в книгу, введите имя: ");
                        if (checkName(scanner.nextLine().trim()) && !telephoneBookKeyNumber.containsValue(name)) {
                            telephoneBookKeyNumber.put(number, name);
                            telephoneBookKeyName.put(name, number);
                        } else {
                            System.out.println("Такое имя уже существует или введено некоректно!");
                        }
                    }
                    break;
                case NAME:
                    if (telephoneBookKeyName.containsKey(name)) {
                        System.out.println("Имя: " + name);
                        System.out.println("Номер тетефона: " + telephoneBookKeyName.get(name));
                    } else {
                        System.out.println("Контакт с таким именем отсутствует!" +
                                "\n\tЧтобы добавить новый контакт в книгу, введите номер телефона: ");
                        if (checkPhoneNumber(scanner.nextLine().trim()) && !telephoneBookKeyName.containsValue(number)) {
                            telephoneBookKeyName.put(name, number);
                            telephoneBookKeyNumber.put(number, name);
                        } else {
                            System.out.println("Такой номер уже существует или введён некоректно!");
                        }
                    }
                    break;
                default:
                    System.out.println("Некоректный ввод! Повторите");
            }
        } while (check);
    }

    private static void checkInLine() {
        if (inLine.matches("^LIST")) {
            command = Command.LIST;
        } else if (inLine.matches("^EXIT")) {
            command = Command.EXIT;
        } else if (checkPhoneNumber(inLine)) {
            command = Command.NUMBER;
        } else if (checkName(inLine)) {
            command = Command.NAME;
        } else {
            command = Command.NONE;
        }
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

    private static Command getCommand() {
        return command;
    }

    private static void printMap(Map<String, String> map) {
        if (!map.isEmpty()) {
            for (String key : map.keySet()) {
                System.out.println(key + " ==> " + map.get(key));
            }
        } else {
            System.out.println("\tТелефонная книга пуста!");
        }
    }
}


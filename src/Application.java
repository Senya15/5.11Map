import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class Application {

    private String number;
    private String name;
    private String inLine;
    private Command command;

    public void run() {
        boolean check = true;
        Map<String, String> telephoneBookKeyNumber = new HashMap<>();
        telephoneBookKeyNumber.put("375336019174", "Влад");
        telephoneBookKeyNumber.put("375157169295", "Варя");
        telephoneBookKeyNumber.put("375292320360", "Мама");
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Введите номер телефона, имя или одну из команд:");
            inLine = scanner.nextLine().trim();
            checkInLine();
            switch (getCommand()) {
                case NUMBER:
                    if (telephoneBookKeyNumber.containsKey(number)) {
                        System.out.println("Имя: " + telephoneBookKeyNumber.get(number));
                        System.out.println("Номер телефона: " + number);
                    } else {
                        System.out.println("Контакт с таким номером телефона отсутствует!" +
                                "\nЧтобы добавить новый контакт в книгу, введите имя: ");
                        if (checkName(scanner.nextLine().trim()) && !telephoneBookKeyNumber.containsValue(name)) {
                            telephoneBookKeyNumber.put(number, name);
                        } else {
                            System.out.println("Такое имя уже существует или введено некоректно!");
                        }
                    }
                    break;
                case NAME:
                    if (telephoneBookKeyNumber.containsValue(name)) {
                        telephoneBookKeyNumber.forEach((key, value) -> {
                            if (name.equals(value)) {
                                System.out.println("Имя: " + value);
                                System.out.println("Номер тетефона: " + key);
                            }
                        });

                    } else {
                        System.out.println("Контакт с таким именем отсутствует!" +
                                "\nЧтобы добавить новый контакт в книгу, введите номер телефона: ");
                        if (checkPhoneNumber(scanner.nextLine().trim()) && !telephoneBookKeyNumber.containsKey(number)) {
                            telephoneBookKeyNumber.put(number, name);
                        } else {
                            System.out.println("Такой номер уже существует или введён некоректно!");
                        }
                    }
                    break;
                case LIST:
                    printMap(telephoneBookKeyNumber);
                    break;
                case DELETE:
                    System.out.println("\tВведите номер телефона или имя контка для удаления:");
                    String s = scanner.nextLine().trim();
                    if (checkName(s) && telephoneBookKeyNumber.containsValue(name)) {
                        AtomicReference<String> keyForRemove = new AtomicReference<>();
                        telephoneBookKeyNumber.forEach((key, value) -> {
                            if (name.equals(value)) {
                                keyForRemove.set(key);
                            }
                        });
                        telephoneBookKeyNumber.remove(keyForRemove.get());
                    }
                    if (checkPhoneNumber(s)) {
                        telephoneBookKeyNumber.remove(number);
                    }
                    break;
                case EXIT:
                    System.out.println("Програма завершена...");
                    check = false;
                    break;
                default:
                    System.out.println("Некоректный ввод! Повторите");
            }
        } while (check);
    }

    private void checkInLine() {
        if (inLine.matches("^LIST")) {
            command = Command.LIST;
        } else if (inLine.matches("^EXIT")) {
            command = Command.EXIT;
        } else if (inLine.matches("^DELETE")) {
            command = Command.DELETE;
        } else if (checkPhoneNumber(inLine)) {
            command = Command.NUMBER;
        } else if (checkName(inLine)) {
            command = Command.NAME;
        } else {
            command = Command.NONE;
        }
    }

    private boolean checkPhoneNumber(String inNumber) {
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

    private boolean checkName(String inName) {
        boolean check = false;
        if (inName.matches("^[А-Яа-я]+")) {
            name = inName;
            check = true;
        } else {
            name = null;
        }
        return check;
    }

    private void printMap(Map<String, String> numberMap) {
        if (!numberMap.isEmpty()) {
            System.out.println("Список контактов:");
            numberMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(entrySet -> System.out.println(entrySet.getValue() + " ==> " + entrySet.getKey()));
        } else {
            System.out.println("\tТелефонная книга пуста!");
        }
    }
}

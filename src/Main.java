import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        telephoneBookKeyNumber.put("375157169295", "Варя");
        telephoneBookKeyNumber.put("375292320360", "Мама");
        telephoneBookKeyName.put("Влад", "375336019174");
        telephoneBookKeyName.put("Варя", "375157169295");
        telephoneBookKeyName.put("Мама", "375292320360");
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Введите номер телефона, имя или одну из команд:");
            inLine = scanner.nextLine().trim();
            checkInLine();
            switch (getCommand()) {
                case LIST:
                    printMap(telephoneBookKeyNumber, telephoneBookKeyName);
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
                                "\nЧтобы добавить новый контакт в книгу, введите имя: ");
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
                                "\nЧтобы добавить новый контакт в книгу, введите номер телефона: ");
                        if (checkPhoneNumber(scanner.nextLine().trim()) && !telephoneBookKeyName.containsValue(number)) {
                            telephoneBookKeyName.put(name, number);
                            telephoneBookKeyNumber.put(number, name);
                        } else {
                            System.out.println("Такой номер уже существует или введён некоректно!");
                        }
                    }
                    break;
                case NONE:
                    break;
                case DELETE:
                    System.out.println("\tВведите номер телефона или имя контка для удаления:");
                    String s = scanner.nextLine().trim();
                    if (checkName(s) && telephoneBookKeyNumber.containsValue(name)) {
                        telephoneBookKeyName.remove(name);
                        telephoneBookKeyNumber.remove(telephoneBookKeyName.get(name));
                    }
                    if (checkPhoneNumber(s) && telephoneBookKeyName.containsValue(number)) {
                        telephoneBookKeyNumber.remove(number);
                        telephoneBookKeyName.remove(telephoneBookKeyNumber.get(number));
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

    private static void printMap(Map<String, String> numberMap, Map<String, String> nameMap) {
        if (!nameMap.isEmpty() && !numberMap.isEmpty()) {
            System.out.println("Список \"telephoneBookKeyNumber\"");

            numberMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(entrySet -> System.out.println(entrySet.getValue() + " ==> " + entrySet.getKey()));

            System.out.println("Список \"telephoneBookKeyName\"");
            nameMap.forEach((key, value) -> System.out.println(key + " ==> " + value));
        } else {
            System.out.println("\tТелефонная книга пуста!");
        }
    }
}


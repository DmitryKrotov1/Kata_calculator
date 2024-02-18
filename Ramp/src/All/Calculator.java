import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {

    private static final Map<String, Integer> rimskie = new HashMap<>();

    static {
        rimskie.put("I", 1);
        rimskie.put("II", 2);
        rimskie.put("III", 3);
        rimskie.put("IV", 4);
        rimskie.put("V", 5);
        rimskie.put("VI", 6);
        rimskie.put("VII", 7);
        rimskie.put("VIII", 8);
        rimskie.put("IX", 9);
        rimskie.put("X", 10);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите выражение (например, 3 + 4):");
        String input = scanner.nextLine();

        try {
            String result = calculate(input);
            System.out.println("Результат: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        scanner.close();
    }


    private static String calculate(String input) {
        String[] args = input.split(" ");

        if (args.length != 3) {
            throw new IllegalArgumentException("Неверное количество аргументов!");
        }

        try {
            int num1 = parseNumber(args[0]);
            int num2 = parseNumber(args[2]);
            char operator = args[1].charAt(0);

            int result;

            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 == 0) {
                        throw new IllegalArgumentException("Деление на ноль!");
                    }
                    result = num1 / num2;
                    break;
                default:
                    throw new IllegalArgumentException("Неверная операция!");
            }
            if ((isRoman(args[0]) && !isRoman(args[2])) || (!isRoman(args[0]) && isRoman(args[2]))) {
                throw new IllegalArgumentException("Калькулятор умеет работать только с арабскими или римскими цифрами одновременно!");
            }

            if (isRoman(args[0]) && isRoman(args[2])) {
                if (result <= 0) {
                    throw new IllegalArgumentException("Результат с римскими числами не может быть меньше 1!");
                }
                return toRoman(result);
            } else {
                return Integer.toString(result);
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат числа!");
        }
    }

    private static int parseNumber(String token) {
        if (isRoman(token)) {
            final int i = romanToDecimal(token);
            return i;
        } else {
            return Integer.parseInt(token);
        }
    }

    private static boolean isRoman(String input) {
        return input.matches("[IVX]+");
    }

    private static int romanToDecimal(String rome) {
        int result = 0;
        int prevValue = 0;

        int i;
        for (i = 1 - rome.length(); i >= 0; i--) {
            int currentValue = rimskie.get(rome.substring(i, i + 1));

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            prevValue = currentValue;
        }

        return result;
    }

    private static String toRoman(int number) {
        if (number <= 0 || number > 10) {
            throw new IllegalArgumentException("Римские числа должны быть от I до X включительно!");
        }

        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, Integer> entry : rimskie.entrySet()) {
            String romanNumeral = entry.getKey();
            int value = entry.getValue();

            while (number >= value) {
                result.append(romanNumeral);
                number = number - value;
            }
        }

        return result.toString();
    }
}
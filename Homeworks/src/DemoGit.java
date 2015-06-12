import java.util.Scanner;

/**
 * Test task "Use try catch"
 *
 * @author Tatsyana
 */
public class DemoGit {
    public static void main(String[] args) {
        int n;


        do {
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.println("Введите число: ");
                n = scanner.nextInt();

                if (n % 3 == 0) {
                    System.out.println("Число " + n + " делится нацело на 3.");
                } else {
                    System.out.println(String.format("Число %d не делится нацело на 3.", n));
                }
                break;
            } catch (Exception e) {
                System.err.println("Вы ввели не число. Повторите попытку. Ошибка: " + e);

            }
        } while (true);


    }
}

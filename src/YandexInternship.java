import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class YandexInternship {
    public static void main(String[] args) {
        /*
        input
        5 1 (first - amount numbers in row, second - amount available permutations of two numbers in places)
        2 2 1 2 2
        output
        4
         */

        findMaxEvenSubsequence(new Scanner(System.in));
    }
    public static void findMaxEvenSubsequence (Scanner scanner) {

        int n = scanner.nextInt(); // колличество чисел
        int bonus = scanner.nextInt(); //колличество перестановок
        List<Integer> rows = new ArrayList<>(); //длины отрезков четный/нечетный

        int parity = 1; //предыдущее нечетное?-false четное?-true
        int currentRow = 0; // текущая длина отрезка

        for (int i = 0; i < n; i++) {

            int currentNum = scanner.nextInt(); //текущее число

            if (currentNum % 2 == 0) { //если текущее число четное
                if (parity == 1) { //если предыдущее было нечетным то
                    if (!rows.isEmpty()) { //если не пуст, то добавить предыдущий отрезок в лист
                        rows.add(currentRow);
                        currentRow = 0; //обнулить отрезок
                    }
                    parity = 2; //поставить что предыдущее было четным
                }
                currentRow++; //увеличить отрезок

            } else if (currentRow != 0) { //если текущее не четное и уже есть отрезки в листе...

                if (parity == 2) { //если предыдущее было четным...
                    rows.add(currentRow); //добавить предыдущий отрезок в лист
                    currentRow = 0; //обнулить отрезок
                    parity = 1; //поставить что предыдущее было нечетным
                }

                currentRow++; //увеличить отрезок
            }
        }
        rows.add(currentRow);

        int max = 0;
        //ищем максимуальный четный отрезок
        for (int i = 0; i < rows.size(); ) {

            int current = 0;
            for (int j = 0, c = i; j <= bonus && c < rows.size(); ) {
                current += rows.get(c);
                if ((c + 1) < rows.size()) {
                    j += rows.get(c + 1);
                }
                c += 2;
            }
            if (current > max) {
                max = current;
            }

            i += 2;
        }
        System.out.println(max);
    }
}

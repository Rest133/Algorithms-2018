package lesson6;

import kotlin.NotImplementedError;
import kotlin.collections.EmptyList;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * .
     * Средняя
     * <p>
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
//------------------------------------------------------------------Объявление переменных и матрицы для поиска НОП
        String result = "";
        int m = first.length() + 1;
        int n = second.length() + 1;
        int[][] lcs = new int[m][n];
//------------------------------------------------------------------Построение матрицы для поиска совпадений
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (first.charAt(i - 1) == second.charAt(j - 1)) {
                    lcs[i][j] = lcs[i - 1][j - 1] + 1;
                } else lcs[i][j] = Math.max(lcs[i - 1][j], lcs[i][j - 1]);
            }
        }
//------------------------------------------------------------------Вывод на консоль
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(lcs[i][j]);
            }
            System.out.println();
        }
        System.out.println();
//------------------------------------------------------------------Построение подстроки
        int i = m - 1;
        int j = n - 1;
        while (i > 0 && j > 0) {
            if (first.charAt(i - 1) == second.charAt(j - 1)) {
                result = first.charAt(i - 1) + result;
                i--;
                j--;
            } else if (lcs[i][j] == lcs[i - 1][j]) {
                i--;
            } else j--;
        }
        return result;
//Трудоемкость: О(n*m), Ресурсоемкость: O(n*m)
    }


    /**
     * Наибольшая возрастающая подпоследовательность
     * Средняя
     * <p>
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     * Тест для СТРОГО возрастающей последовательности
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        if (list.size() <= 1) return list;
//------------------------------------------------------Создание массивов длины последовательности и предшественников,
        //листа результа и длины максимальной подпоследовательности
        List<Integer> result = new LinkedList<>();
        ArrayList<Integer> length = new ArrayList<>();
        ArrayList<Integer> predecessor = new ArrayList<>();
        int maxLength = 0;
//------------------------------------------------------Наполнение массивов
        for (int i = 0; i < list.size(); i++) {
            length.add(1);
            predecessor.add(-1);
        }
//------------------------------------------------------Цикл прохождения по данной последовательности и поиск
        //максимальной длины
        for (int i = 1; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (list.get(j) < list.get(i) && length.get(j) + 1 > length.get(i)) {
                    length.set(i, length.get(j) + 1);
                    predecessor.set(i, j);

                    if (length.get(maxLength) < length.get(i)) {
                        maxLength = i;
                    }
                }
            }
        }
//-------------------------------------------------------"Восстановление" последовательности
        int count = length.get(maxLength);
        int i = maxLength;
        while (i != -1) {
            --count;
            result.add(list.get(i));
            ;
            i = predecessor.get(i);
        }
        Collections.sort(result);
        return result;
//Трудоемкость: О(n^2), Ресурсоемкость: O(n)
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Сложная
     * <p>
     * В файле с именем inputName задано прямоугольное поле:
     * <p>
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     * <p>
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     * <p>
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) throws IOException {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}

package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.File;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     * <p>
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     * <p>
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     * <p>
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) throws IOException {
        Scanner sc = new Scanner(new File(inputName));
        List<Integer> costs = new ArrayList<>();
        Pair<Integer, Integer> answer = new Pair<>(0, 0);
        int different = 0;
        costs.add(0);

        while (sc.hasNextLine()) {
            costs.add(Integer.parseInt(sc.nextLine()));
        }

        for (int i = 1; i < costs.size(); i++) {
            for (int j = i + 1; j < costs.size(); j++) {
                if (costs.get(j) - costs.get(i) > different) different = costs.get(j) - costs.get(i);
            }
        }

        for (int i = 1; i < costs.size(); i++) {
            for (int j = i + 1; j < costs.size(); j++) {
                if (costs.get(j) - costs.get(i) == different) answer = new Pair<>(i, j);
            }
        }

        return answer;
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     * <p>
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 5
     * <p>
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 х
     * <p>
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 х 3
     * 8   4
     * 7 6 Х
     * <p>
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     * <p>
     * 1 Х 3
     * х   4
     * 7 6 Х
     * <p>
     * 1 Х 3
     * Х   4
     * х 6 Х
     * <p>
     * х Х 3
     * Х   4
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   х
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   Х
     * Х х Х
     * Трудоемкость:О(n);
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        int g = 0;
        for (int i = 0; i < menNumber; ++i)
            g = (g + choiceInterval) % (i + 1);
        return g + 1;
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     * <p>
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    static public String longestCommonSubstring(String firs, String second) {
        List<String> str1 = new ArrayList<>();

        System.out.println(firs);
        System.out.println(second);
        System.out.println("/------");
        for (int i = 1; i < firs.length(); i++) {
            str1.add(((Character) firs.toCharArray()[i - 1]).toString() + ((Character) firs.toCharArray()[i]).toString());
        }
        String[][] matrix = new String[str1.size()][second.length()];

        for (int i = 0; i < str1.size(); i++) {
            matrix[i][0] = str1.get(i);
        }
        for (int i = 0; i < second.length(); i++) {
            matrix[0][i] = ((Character) second.toCharArray()[i]).toString();
        }

        for (int i = 0; i < str1.size(); i++) {
            for (int j = 0; j < second.length(); j++) {
                System.out.println(matrix[i][j]);
            }
            System.out.print("\n");
        }

        System.out.println("/------");
        return "";
    }

    /**
     * Число простых чисел в интервале
     * Простая
     * <p>
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     * <p>
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     * Трудоёмкость равна O(N*log(log N)), a ресурсоёмкость: R = O(N)
     */
    static public int calcPrimesNumber(int limit) {
        int answer = 0;
        if (limit <= 1)
            return answer;
        int[] sum = new int[limit + 1];

        for (int i = 2; i <= limit; i++) sum[i] = 1;
        for (int i = 2; i <= limit; i++) {
            if (sum[i] == 1) {
                answer++;
                for (int j = i; j <= limit; j += i) sum[j] = 0;
            }
        }
        return answer;
    }

    /**
     * Балда
     * Сложная
     * <p>
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     * <p>
     * И Т Ы Н
     * К Р А Н
     * А К В А
     * <p>
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     * <p>
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     * <p>
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     * <p>
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {
        throw new NotImplementedError();
    }
}
package lesson1;

import kotlin.NotImplementedError;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
     * каждый на отдельной строке. Пример:
     * <p>
     * 13:15:19
     * 07:26:57
     * 10:00:03
     * 19:56:14
     * 13:15:19
     * 00:40:31
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 00:40:31
     * 07:26:57
     * 10:00:03
     * 13:15:19
     * 13:15:19
     * 19:56:14
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     * Трудоемкость равна O(n * log(n)), а ресурсоемкость: R = O(n)
     */
    static public void sortTimes(String inputName, String outputName) throws IOException {
        Scanner sc = new Scanner(new File(inputName));
        List<String> sortedTimeList = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.matches("^\\d\\d:\\d\\d:\\d\\d\n?$")) sortedTimeList.add(line);
            else throw new IOException("Неверный формат");
        }
        sc.close();

        Collections.sort(sortedTimeList);

        FileWriter newFile = new FileWriter(new File(outputName));
        for (String line : sortedTimeList) newFile.write(line + "\n");
        newFile.close();

    }

    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     * Трудоемкость равна O(n * log(n)), а ресурсоемкость: R = O(n)
     */
    static public void sortAddresses(String inputName, String outputName) throws IOException {
        Scanner sc = new Scanner(new File(inputName));
        List<String> sortedAddresses = new ArrayList<>();
        TreeMap<String, Set<String>> sortedMapAddress = new TreeMap<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.matches("^.+\\s.+\\s*[-]\\s*.+\\s+\\d+$")) sortedAddresses.add(line);
            else throw new IllegalArgumentException("Неверный формат строки");
        }

        for (String x : sortedAddresses) {
            String[] sorted = x.split(" - "); //sorted[0] - Имена,sorted[1] - Адреса
            if (!sortedMapAddress.containsKey(sorted[1])) {
                TreeSet<String> names = new TreeSet<>();
                names.add(sorted[0]);
                sortedMapAddress.put(sorted[1], names);
            } else {
                sortedMapAddress.get(sorted[1]).add(sorted[0]);
            }
        }


        FileWriter newFile = new FileWriter(new File(outputName));
        for (String line : sortedMapAddress.keySet()) {
            List<String> allNames = new ArrayList<>();
            for (String x : sortedMapAddress.get(line)) {
                allNames.add(x);
            }
            String names = "";
            for (String x : allNames) {
                names += x + ", ";
            }
            names = names.substring(0, names.length() - 2);
            newFile.write(line + " - " + names + "\n");
        }
        newFile.close();
    }

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     * Трудоемкость равна O(n * log(n)), а ресурсоемкость: R = O(n)
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        Scanner sc = new Scanner(new File(inputName));
        List<Integer> temperatures = new ArrayList<>();

        while (sc.hasNextLine()) {
            double oneTemperatureDouble = Double.parseDouble(sc.nextLine()) * 10;
            int oneTemperature = (int) oneTemperatureDouble;
            temperatures.add(oneTemperature);
        }
        Collections.sort(temperatures);

        FileWriter newFile = new FileWriter(new File(outputName));

        for (int temperature : temperatures) {
            if (temperature > -10 && temperature < 0)
                newFile.write("-" + temperature / 10 + "." + Math.abs(temperature % 10) + "\n");
            else {
                newFile.write(temperature / 10 + "." + Math.abs(temperature % 10) + "\n");
            }
        }
        newFile.close();
    }

    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2 ///true и false - если встречается несколько раз
     * Трудоемкость равна O(n), а ресурсоемкость: R = O(n)
     */
    static public void sortSequence(String inputName, String outputName) throws IOException {
        Scanner sc = new Scanner(new File(inputName));
        Map<Integer, Integer> numbers = new HashMap<>();
        List<Integer> nums = new ArrayList<>();
        int maxValue = 0;
        int max = 0;


        while (sc.hasNextLine()) {
            nums.add(Integer.parseInt(sc.nextLine()));
        }
        for (int i : nums) {
            int n = 1;
            if (numbers.containsKey(i)) {
                n = numbers.get(i) + 1;
            }
            numbers.put(i, n);
        }
        for (int x : numbers.keySet()) {
            if (numbers.get(x) > maxValue) maxValue = numbers.get(x);
        }
        for (int x : numbers.keySet()) {
            if (numbers.get(x) == maxValue) {
                max = x;
            }
        }
        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i) == max) nums.remove(i);
        }
        for (; maxValue != 0; ) {
            nums.add(max);
            maxValue--;
        }
        FileWriter newFile = new FileWriter(new File(outputName));
        for (Integer numOfNums : nums) {
            newFile.write(numOfNums.toString() + "\n");
        }
        newFile.close();
    }

    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     * Трудоемкость равна O(n), а ресурсоемкость: R = O(n)
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        for (int i = 0; i < first.length; i++) {
            if (second[i] == null) second[i] = first[i];
        }
        Arrays.sort(second);
    }
}
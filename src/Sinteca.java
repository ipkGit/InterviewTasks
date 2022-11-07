import java.io.*;
import java.util.*;

public class Sinteca {

    public static void main(String[] args) {
        estimateToFile(makeEstimate("input.txt"), "output.txt");
    }

    public static Map<String, String> makeEstimate(String fileName) {
        List<String> type; //типы материалов
        List<String> material; //значения материалов
        try (BufferedReader bReader = new BufferedReader(new FileReader(fileName))) {
            type = readMaterial(bReader); //получаем типы материалов
            material = readMaterial(bReader); //получаем значения материалов
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //получаем список  c сопоставленными материалами
        return comparisonOfMaterials(type, material);
    }

    //читаем список материалов
    public static List<String> readMaterial(BufferedReader bReader) {
        int lines; //количество материала
        List<String> materials = new ArrayList<>(); //материалы
        try {
            lines = Integer.parseInt(bReader.readLine()); //получаем количество из файла
            for (int i = 0; i < lines; i++) {
                materials.add(bReader.readLine()); //добавляем материал в список
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return materials;
    }

    //сопоставим материалы из первого списка с материалами из второго
    public static Map<String, String> comparisonOfMaterials(List<String> types, List<String> materials) {
        Map<String, String> estimate = new LinkedHashMap<>();
        //далее ужасный алгоритм из 3 циклов, но я не придумал ничего эффективнее.
        //проверяемая строка
        for (String type : types) {
            UP:
//внешний цикл
            for (int j = 0; j < materials.size(); j++) { //берем строку из 2 списка
                for (String s : materials.get(j).trim().split("\\P{L}+")) { //разбиваем на слова и проверяем на вхождение
                    String word;
                    if (s.length() > 3) { //если слово более 4 символов
                        word = s.substring(0, s.length() - 1); //убираем окончание
                    } else {
                        word = s;
                    }
                    if (type.contains(word)) { //проверяем на вхождение
                        estimate.put(type, materials.remove(j)); //добавляем в смету и убираем материал с совпадением из исходного списка, чтоб не повторяться
                        break UP; //если мы нашли совпадение, то выходим во внешний цикл
                    }
                }
            }
            estimate.putIfAbsent(type, "?"); //если совпадения не было, добавляем в список только тип
        }
        // в случае, если остались не использованные материалы из 2 списка, добавляем их в смету
        if (materials.size() > 0) {
            for (String material : materials) {
                if (estimate.containsValue("?")) { //если есть типы без пары материала, то добавляем в пару.
                    for (Map.Entry pair : estimate.entrySet()) {
                        if (pair.getValue().equals("?")) {
                            estimate.computeIfPresent((String) pair.getKey(), (K, V) -> material);
                        }
                    }
                } else { //если все типы имеют свой материал. то оставшиеся значения из 2 списка образуют свой тип с значением "?"
                    estimate.put(material, "?");
                }
            }
        }
        return estimate;
    }

    public static void estimateToFile(Map<String, String> estimate, String file) { //write estimate to file
        try (FileWriter writer = new FileWriter(file, false)) {
            for (Map.Entry<String, String> entry : estimate.entrySet()) {
                String s = entry.getKey() + ":" + entry.getValue() + "\n";
                writer.write(s);
            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
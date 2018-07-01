import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Planner {
    int temp, firstNumber, secondNumber, priority, count, size; //вспомогательные переменные
    int dependTask; //зависящая задача
    String s; //поочередно считываемая строка из файла
    Map<Integer, Integer> uniqueTask; //хранит уникальные задачи и их приоритет
    List<Integer> firstTask; //массив независящих задач
    List<Integer> secondTask; //массив зависимых задач

    public Planner() {
        this.uniqueTask = new HashMap<>();
        this.firstTask = new ArrayList<>();
        this.secondTask = new ArrayList<>();
    }

    public void planTasks(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((s = br.readLine()) != null) {
                String[] values = s.split(" ");
                firstNumber = Integer.parseInt(values[0]);
                secondNumber = Integer.parseInt(values[1]);
                firstTask.add(firstNumber);
                secondTask.add(secondNumber);
                //Если записи независящей задачи нет
                if (!uniqueTask.containsKey(firstNumber)) {
                    uniqueTask.put(firstNumber, 0);
                }
                //Если записи зависящей задачи нет
                if (!uniqueTask.containsKey(secondNumber)) {
                    priority = uniqueTask.get(firstNumber);
                    priority++;
                    uniqueTask.put(secondNumber, priority);
                }
                //Если запись зависящей задачи есть
                else {
                    temp = uniqueTask.get(secondNumber);
                    priority = uniqueTask.get(firstNumber);
                    if (priority == temp) {
                        priority++;
                        uniqueTask.put(secondNumber, priority);
                        correctFun(secondNumber, priority);
                    }
                }
            }
            showResult();
        } catch (IOException exc) {
            System.out.println("Ошибка чтения файла: " + exc);
        }
    }

    //Корректировка приотетов у зависящих задач
    private void correctFun(int task, int value) {
        for (int i = 0; i < firstTask.size(); i++) {
            if (firstTask.get(i) == task) {
                dependTask = secondTask.get(i); //получаем номер зависяей задачи
                priority = uniqueTask.get(dependTask); //получаем текущий приоритет зависящей задачи
                if (priority <= value) {
                    priority = value;
                    priority++;
                    uniqueTask.put(dependTask, priority);
                    correctFun(dependTask, priority);
                }
            }
        }
        return;
    }

    //Выводит результат в консоль
    private void showResult() {
        priority = 0;
        count = 0;
        size = uniqueTask.size();
        while (count != size) {
            for (Map.Entry<Integer, Integer> pair : uniqueTask.entrySet()) {
                if (priority == pair.getValue()) {
                    System.out.print(pair.getKey() + " ");
                    count++;
                }
            }
            System.out.println();
            priority++;
        }
    }
}
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Planner {
    private int firstNumber, secondNumber; //вспомогательные переменные
    private int count = 0;
    private String s; //поочередно считываемая строка из файла
    private List<Integer> firstTaskArray = new ArrayList<>(); //массив независящих задач
    private List<Integer> secondTaskArray = new ArrayList<>(); //массив зависимых задач
    private Set<Integer> uniqueTaskArray = new HashSet<>();
    private Set<Integer> highestPriorityTasks = new HashSet<>();

    void planTasks(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((s = br.readLine()) != null) {
                String[] values = s.split(" ");
                firstNumber = Integer.parseInt(values[0]);
                secondNumber = Integer.parseInt(values[1]);
                firstTaskArray.add(firstNumber);
                secondTaskArray.add(secondNumber);
                uniqueTaskArray.add(firstNumber);
                uniqueTaskArray.add(secondNumber);
            }
        } catch (IOException exc) {
            System.out.println("Ошибка чтения файла: " + exc);
        }

        // Пока список уникадьных зада не пуст
        while (uniqueTaskArray.size() > 0) {
            //Ищем задачи высшего приоритета
            for (Integer uniqueTask : uniqueTaskArray) {
                count = 0;
                for (Integer secondTask : secondTaskArray) {
                    if (secondTask == uniqueTask) {
                        count++;
                        break;
                    }
                }
                if (count == 0) {
                    highestPriorityTasks.add(uniqueTask);
                }
            }

            //Очищаем коллекции от задач, высшего приоритета
            for (Integer task : highestPriorityTasks) {
                uniqueTaskArray.remove(task);
                for (int i = 0; i < firstTaskArray.size(); i++) {
                    if (firstTaskArray.get(i) == task) {
                        firstTaskArray.remove(i);
                        secondTaskArray.remove(i);
                        i--;
                    }
                }
            }
            System.out.println(highestPriorityTasks);
            highestPriorityTasks.clear();
        }
    }
}
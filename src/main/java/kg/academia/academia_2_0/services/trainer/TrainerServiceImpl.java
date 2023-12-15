package kg.academia.academia_2_0.services.trainer;

import kg.academia.academia_2_0.model.entities.homeWork.MentalExercise;
import kg.academia.academia_2_0.model.enums.TrainerTask;
import kg.academia.academia_2_0.model.utilities.MentalTask;
import kg.academia.academia_2_0.services.trainer.rostAbacus.TaskGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrainerServiceImpl implements TrainerService {

    @Override
    public List<MentalTask> getTasksByTaskName(String taskName, int digits, int count, int arrayCount) {
        List<MentalTask> responseArray = new ArrayList<>();
        TaskGenerator taskGenerator = getTrainerTaskByName(taskName).getGenerator();
        for (int j = 0; j < arrayCount; j++) {
            responseArray.add(getTaskByDigitsAndCount(taskGenerator, digits, count));
        }
        return responseArray;
    }

    @Override
    public List<MentalTask> getTasksByExercise(MentalExercise exercise) {
        List<MentalTask> responseArray = new ArrayList<>();
        for (int j = 0; j < exercise.getQuestionCount() - exercise.getSolved() && j < 10; j++) {
            responseArray.add(getTaskByDigitsAndCount(exercise.getTopic().getGenerator(), exercise.getDigitsCount(), exercise.getNumbersCount()));
        }
        return responseArray;
    }

    @Override
    public Map<String, MentalTask> getMultipleTasksByTopicsAndGeneralInfo(String topic1, String topic2, String topic3, String topic4, Integer digits, Integer count, int i) {
        Map<String, MentalTask> response = new HashMap<>();
        if (topic1 != null)
            response.put("task1", getTaskByDigitsAndCount(getTrainerTaskByName(topic1).getGenerator(), digits, count));
        if(topic2 != null)
            response.put("task2", getTaskByDigitsAndCount(getTrainerTaskByName(topic2).getGenerator(), digits, count));
        if(topic3 != null)
            response.put("task3", getTaskByDigitsAndCount(getTrainerTaskByName(topic3).getGenerator(), digits, count));
        if(topic4 != null)
            response.put("task4", getTaskByDigitsAndCount(getTrainerTaskByName(topic4).getGenerator(), digits, count));
        return response;
    }

    @Override
    public MentalTask getTaskByDigitsAndCount(TaskGenerator taskGenerator, int digits, int count) {
        Integer[] currentArray = new Integer[count];
        int sum = 0;
        if (digits == 1) {
            for (int i = 0; i < count; i++) {
                int currentNum = taskGenerator.head(splitter(sum, 0), splitter(sum, 1));
                currentArray[i] = currentNum;
                sum += currentNum;
            }
        } else {
            for (int i = 0; i < count; i++) {
                int k = 1;
                int genDig = taskGenerator.head(splitter(sum, digits - k), splitter(sum, digits - k + 1));
                int currentNum = genDig * (int) Math.pow(10, digits - k);
                k++;
                while (k <= digits) {
                    genDig = taskGenerator.tail(splitter(sum + currentNum, digits - k), splitter(sum + currentNum, digits - k + 1), currentNum >= 0);
                    currentNum = currentNum + genDig * (int) Math.pow(10, digits - k);
                    k++;
                }
                currentArray[i] = currentNum;
                sum += currentNum;
            }
        }
        return MentalTask.builder()
                .taskEntry(currentArray)
                .answer(sum)
                .build();
    }

    private int splitter(int currentNum, int positionFromRight) {
        try {
            String num = new StringBuilder(Integer.toString(currentNum)).reverse().toString();
            return Character.getNumericValue(num.charAt(positionFromRight));
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }

    public TrainerTask getTrainerTaskByName(String name) {
        return switch (name) {
            case "PB+1" -> TrainerTask.PBP1;
            case "PB+2" -> TrainerTask.PBP2;
            case "PB+3" -> TrainerTask.PBP3;
            case "PB+4" -> TrainerTask.PBP4;
            case "PB-1" -> TrainerTask.PBM1;
            case "PB-2" -> TrainerTask.PBM2;
            case "PB-3" -> TrainerTask.PBM3;
            case "PB-4" -> TrainerTask.PBM4;

            case "PD+9" -> TrainerTask.PDP9;
            case "PD+8" -> TrainerTask.PDP8;
            case "PD+7" -> TrainerTask.PDP7;
            case "PD+6" -> TrainerTask.PDP6;
            case "PD+5" -> TrainerTask.PDP5;
            case "PD+4" -> TrainerTask.PDP4;
            case "PD+3" -> TrainerTask.PDP3;
            case "PD+2" -> TrainerTask.PDP2;
            case "PD+1" -> TrainerTask.PDP1;
            case "PD-9", "PMD-9" -> TrainerTask.PDM9;
            case "PD-8", "PMD-8" -> TrainerTask.PDM8;
            case "PD-7", "PMD-7" -> TrainerTask.PDM7;
            case "PD-6", "PMD-6" -> TrainerTask.PDM6;
            case "PD-5", "PMD-5" -> TrainerTask.PDM5;
            case "PD-4", "PMD-4" -> TrainerTask.PDM4;
            case "PD-3", "PMD-3" -> TrainerTask.PDM3;
            case "PD-2", "PMD-2" -> TrainerTask.PDM2;
            case "PD-1", "PMD-1" -> TrainerTask.PDM1;

            default -> TrainerTask.PSV;
        };
    }


}

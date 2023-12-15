package kg.academia.academia_2_0.services.trainer;

import kg.academia.academia_2_0.model.entities.homeWork.MentalExercise;
import kg.academia.academia_2_0.model.enums.TrainerTask;
import kg.academia.academia_2_0.model.utilities.MentalTask;
import kg.academia.academia_2_0.services.trainer.rostAbacus.TaskGenerator;

import java.util.List;
import java.util.Map;

public interface TrainerService {
    List<MentalTask> getTasksByTaskName(String taskName, int digits, int count, int arrayCount);

    MentalTask getTaskByDigitsAndCount(TaskGenerator taskGenerator, int digits, int count);

    TrainerTask getTrainerTaskByName(String topic);

    List<MentalTask> getTasksByExercise(MentalExercise exercise);

    Map<String, MentalTask> getMultipleTasksByTopicsAndGeneralInfo(String topic1, String topic2, String topic3, String topic4, Integer digits, Integer count, int i);
}

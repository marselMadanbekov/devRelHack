package kg.academia.academia_2_0.services.trainer.rostAbacus;

import java.util.Random;

public interface TaskGenerator {
    int head(int sum, int leftNum);
    int tail(int sum,int leftNum, boolean isPlus);
    Random random = new Random();

}

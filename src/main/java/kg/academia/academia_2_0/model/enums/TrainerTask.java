package kg.academia.academia_2_0.model.enums;

import kg.academia.academia_2_0.services.trainer.rostAbacus.TaskGenerator;
import kg.academia.academia_2_0.services.trainer.rostAbacus.pb.*;
import kg.academia.academia_2_0.services.trainer.rostAbacus.pd.md.*;

public enum TrainerTask {
    PSV("ПСВ", kg.academia.academia_2_0.services.trainer.rostAbacus.psv.PSV.getInstance()),

    PBP1("ПБ+1", Plus1.getInstance()),
    PBP2("ПБ+2", Plus2.getInstance()),
    PBP3("ПБ+3", Plus3.getInstance()),
    PBP4("ПБ+4", Plus4.getInstance()),
    PBM1("ПБ-1", Minus1.getInstance()),
    PBM2("ПБ-2", Minus2.getInstance()),
    PBM3("ПБ-3", Minus3.getInstance()),
    PBM4("ПБ-4", Minus4.getInstance()),

    PDP1("ПД+1", LPlus1.getInstance()),
    PDP2("ПД+2", LPlus1.getInstance()),
    PDP3("ПД+3", LPlus1.getInstance()),
    PDP4("ПД+4", LPlus1.getInstance()),
    PDP5("ПД+5", LPlus1.getInstance()),
    PDP6("ПД+6", LPlus1.getInstance()),
    PDP7("ПД+7", LPlus1.getInstance()),
    PDP8("ПД+8", LPlus1.getInstance()),
    PDP9("ПД+9", LPlus1.getInstance()),

    PDM1("ПД-1", LMinus1.getInstance()),
    PDM2("ПД-2", LMinus2.getInstance()),
    PDM3("ПД-3", LMinus3.getInstance()),
    PDM4("ПД-4", LMinus4.getInstance()),
    PDM5("ПД-5", LMinus5.getInstance()),
    PDM6("ПД-6", LMinus6.getInstance()),
    PDM7("ПД-7", LMinus7.getInstance()),
    PDM8("ПД-8", LMinus8.getInstance()),
    PDM9("ПД-9", LMinus9.getInstance()),


    ;

    private final String value;
    private final TaskGenerator taskGenerator;
    TrainerTask(String value, TaskGenerator taskGenerator){
        this.value = value;
        this.taskGenerator = taskGenerator;
    }

    public String getValue(){
        return this.value;
    }

    public TaskGenerator getGenerator(){
        return taskGenerator;
    }
}

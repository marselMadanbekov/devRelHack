package kg.academia.academia_2_0.services.branch;

import kg.academia.academia_2_0.model.utilities.ChartTuple;

import java.util.List;

public interface BranchAnalyticsService {
    List<ChartTuple> getMonthlyPaymentsSumByBranch(Long branchId, Integer monthShift);

    List<ChartTuple> getMonthlyPupilsCountByBranch(Long branchId, Integer monthShift);

    List<ChartTuple> getPupilsCountBySubjectsAndBranch(Long branchId);
}

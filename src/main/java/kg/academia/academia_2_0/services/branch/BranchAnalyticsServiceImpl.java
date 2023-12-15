package kg.academia.academia_2_0.services.branch;

import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.utilities.ChartTuple;
import kg.academia.academia_2_0.services.group.GroupStorage;
import kg.academia.academia_2_0.services.payment.PaymentStorage;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class BranchAnalyticsServiceImpl implements BranchAnalyticsService{
    private final PaymentStorage paymentStorage;
    private final BranchStorage branchStorage;
    private final UserStorage userStorage;
    private final GroupStorage groupStorage;

    @Autowired
    public BranchAnalyticsServiceImpl(PaymentStorage paymentStorage, BranchStorage branchStorage, UserStorage userStorage, GroupStorage groupStorage) {
        this.paymentStorage = paymentStorage;
        this.branchStorage = branchStorage;
        this.userStorage = userStorage;
        this.groupStorage = groupStorage;
    }



    @Override
    public List<ChartTuple> getMonthlyPaymentsSumByBranch(Long branchId, Integer monthShift) {

        Branch branch = branchStorage.getBranchById(branchId);

        List<ChartTuple> response = new ArrayList<>();

        for (int i = monthShift + 3; i >= monthShift; i--) {
            LocalDate startDate = getStartOfMonthAgo(i);
            LocalDate endDate = getEndOfMonthAgo(i).plusDays(1);

            int sum = paymentStorage.getPaymentsSumBetweenDatesByBranch(branch, Date.valueOf(startDate),Date.valueOf(endDate)).orElse(0);
            response.add(
                    ChartTuple.builder()
                            .label(startDate.format(DateTimeFormatter.ofPattern("MMMM-yyyy")))
                            .value(sum)
                            .build()
            );
        }
        return response;
    }

    @Override
    public List<ChartTuple> getMonthlyPupilsCountByBranch(Long branchId, Integer monthShift) {
        Branch branch = branchStorage.getBranchById(branchId);

        System.out.println("selected branch -> " + branch.getName());
        List<ChartTuple> response = new ArrayList<>();

        for (int i = monthShift + 3; i >= monthShift; i--) {
            LocalDate startDate = getStartOfMonthAgo(i);
            LocalDate endDate = getEndOfMonthAgo(i).plusDays(1);

            int sum = userStorage.getNewPupilsCountBetweenDatesByBranch(branch, Date.valueOf(startDate),Date.valueOf(endDate));
            response.add(
                    ChartTuple.builder()
                            .label(startDate.format(DateTimeFormatter.ofPattern("MMMM-yyyy")))
                            .value(sum)
                            .build()
            );
        }
        return response;
    }

    @Override
    public List<ChartTuple> getPupilsCountBySubjectsAndBranch(Long branchId) {
        return groupStorage.getPupilsCountBySubjectAndBranch(branchId);
    }

    public LocalDate getStartOfMonthAgo(int count) {
        return LocalDate.now().minusMonths(count).withDayOfMonth(1);
    }

    public LocalDate getEndOfMonthAgo(int count) {
        LocalDate startDate = getStartOfMonthAgo(count);
        YearMonth yearMonth = YearMonth.from(startDate);
        return yearMonth.atEndOfMonth();

    }

}

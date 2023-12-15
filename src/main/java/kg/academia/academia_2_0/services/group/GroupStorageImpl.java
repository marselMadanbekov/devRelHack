package kg.academia.academia_2_0.services.group;

import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.model.entities.users.Teacher;
import kg.academia.academia_2_0.model.utilities.ChartTuple;
import kg.academia.academia_2_0.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupStorageImpl implements GroupStorage {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupStorageImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }



    @Override
    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElseThrow();
        //:TODO add correct exception
    }

    @Override
    public Page<Group> findAllByPageable(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public List<Group> findAllByBranch(Branch branch) {
        return groupRepository.findAllByBranch(branch);
    }

    @Override
    public List<Group> findAllBySubject(Subject subject) {
        return groupRepository.findAllBySubject(subject);
    }

    @Override
    public List<Group> findAllByTeacher(Teacher teacher) {
        return groupRepository.findAllByTeacher(teacher);
    }

    @Override
    public List<Group> findAllByPupil(Pupil pupil) {
        return groupRepository.findAllByPupils(pupil);
    }

    @Override
    public Group findByName(String name) {
        return groupRepository.findByName(name).orElseThrow();
        //:TODO add correct exception;
    }

    @Override
    public void save(Group group) {
        groupRepository.save(group);
    }

    @Override
    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }

    @Override
    public List<ChartTuple> getPupilsCountBySubjectAndBranch(Long branchId) {
        List<Object[]> data = groupRepository.countPupilsBySubjectAndBranch(branchId);

        List<ChartTuple> chartTuples = new ArrayList<>();
        for (Object[] item : data){
            String label = item[0].toString();
            Long value = (Long) item[1];

            chartTuples.add(
                    ChartTuple.builder()
                            .label(label)
                            .value(value.intValue())
                            .build()
            );
        }
        return chartTuples;
    }
}

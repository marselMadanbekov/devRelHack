package kg.academia.academia_2_0.services.group;

import kg.academia.academia_2_0.model.creations.GroupCreate;
import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.model.entities.users.Teacher;
import kg.academia.academia_2_0.model.enums.DayOfWeek;
import kg.academia.academia_2_0.services.branch.BranchStorage;
import kg.academia.academia_2_0.services.security.AccessGuardService;
import kg.academia.academia_2_0.services.subject.SubjectStorage;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    private final BranchStorage branchStorage;
    private final UserStorage userStorage;
    private final GroupStorage groupStorage;
    private final SubjectStorage subjectStorage;
    private final AccessGuardService accessGuardService;

    @Autowired
    public GroupServiceImpl(BranchStorage branchStorage, UserStorage userStorage, GroupStorage groupStorage, SubjectStorage subjectStorage, AccessGuardService accessGuardService) {
        this.branchStorage = branchStorage;
        this.userStorage = userStorage;
        this.groupStorage = groupStorage;
        this.subjectStorage = subjectStorage;
        this.accessGuardService = accessGuardService;
    }

    @Override
    public void createGroup(GroupCreate groupCreate) {
        if (accessGuardService.isDeniedToCRUDBranchEntities(groupCreate.getBranchId()))
            throw new AccessDeniedException("Вы не можете создавать группу для данного филиала!");
        Teacher teacher = null;
        Subject subject = null;
        Branch branch = branchStorage.getBranchById(groupCreate.getBranchId());
        if (!groupCreate.getTeacherId().equals(0L))
            teacher = userStorage.getTeacher(groupCreate.getTeacherId());
        if (!groupCreate.getSubjectId().equals(0L))
            subject = subjectStorage.getSubjectById(groupCreate.getSubjectId());

        groupStorage.save(
                Group.builder()
                        .name(groupCreate.getName())
                        .subject(subject)
                        .teacher(teacher)
                        .branch(branch)
                        .active(true)
                        .build());
    }

    @Override
    public void addPupilsToGroup(Long groupId, List<Long> pupils) {
        Group group = groupStorage.getGroupById(groupId);

        for(Long pupilId : pupils){
            Pupil pupil = userStorage.getPupil(pupilId);
            group.addPupil(pupil);
        }

        groupStorage.save(group);
    }

    @Override
    public void removePupil(Long groupId, Long pupilId) {
        Group group = groupStorage.getGroupById(groupId);

        Pupil pupil = userStorage.getPupil(pupilId);
        group.removePupil(pupil);
        groupStorage.save(group);
    }

    @Override
    public void updateGroup(Long groupId, String name, Long subjectId, Long teacherId) {
        Group group = groupStorage.getGroupById(groupId);
        if(!subjectId.equals(0L)){
            Subject subject = subjectStorage.getSubjectById(subjectId);
            group.setSubject(subject);
        }
        if(!teacherId.equals(0L)){
            Teacher teacher = userStorage.getTeacher(teacherId);
            group.setTeacher(teacher);
        }
        if(name != null && !name.isEmpty())
            group.setName(name);

        groupStorage.save(group);
    }

    @Override
    public void addLessonToTimetable(Long groupId, Integer dayOfWeek, String clock) {
        Group group = groupStorage.getGroupById(groupId);
        group.addLessonToTimetable(DayOfWeek.values()[dayOfWeek],clock);
        groupStorage.save(group);
    }

    @Override
    public void deleteLessonDay(Long groupId, Integer dayOfWeek) {
        Group group = groupStorage.getGroupById(groupId);
        group.deleteDayFromTimetable(DayOfWeek.values()[dayOfWeek]);
        groupStorage.save(group);
    }

    @Override
    public void changeStatus(Long groupId) {
        Group group = groupStorage.getGroupById(groupId);
        group.setActive(!group.getActive());
        groupStorage.save(group);
    }

    @Override
    public List<Group> findGroupsByBranch(Long branchId) {
        Branch branch = branchStorage.getBranchById(branchId);
        return groupStorage.findAllByBranch(branch);
    }

    @Override
    public List<Group> findGroupsByTeacher(Teacher teacher) {
        return groupStorage.findAllByTeacher(teacher);
    }

    @Override
    public List<Group> findGroupsByPupil(Pupil pupil) {
        return groupStorage.findAllByPupil(pupil);
    }
}

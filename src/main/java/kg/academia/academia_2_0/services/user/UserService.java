package kg.academia.academia_2_0.services.user;

import kg.academia.academia_2_0.model.creations.UserCreate;
import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.model.entities.users.*;
import kg.academia.academia_2_0.model.updations.UserdataUpdate;

import java.util.List;

public interface UserService {
    Owner createOwner(UserCreate userCreate);
    Admin createAdmin(UserCreate userCreate);
    Teacher createTeacher(UserCreate userCreate);
    Pupil createPupil(UserCreate userCreate);
    TempUser createTempUser(UserCreate userCreate);

    void addLessonToTeacher(Long teacherId, Long subjectId);

    void deleteSubjectForTeacher(Long teacherId, Long subjectId);

    void updateUserdata(UserdataUpdate update);

    void changeStatusOfUser(Long userdataId);

    List<Teacher> findTeachersByBranch(Long branchId);

    List<Pupil> findPupilsByBranch(Long branchId);
    List<Pupil> findPupilsByBranch(Branch branch);

    List<Teacher> findTeachersBySubject(Subject subject);

    List<TempUser> findAllTempUsersByBranch(Long branchId);

    void applyRequestOfTempUser(Long tempUserId);

    void rejectRequestOfTempUser(Long tempUserId);
}

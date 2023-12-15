package kg.academia.academia_2_0.services.user;

import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.model.entities.users.*;

import java.sql.Date;
import java.util.List;

public interface UserStorage {
    Owner getOwner(Long id);
    Admin getAdmin(Long id);
    Teacher getTeacher(Long id);
    Pupil getPupil(Long id);
    TempUser getTempUser(Long id);


    SuperAdmin getSuperAdminByUserData(UserData userData);
    Owner getOwnerByUserData(UserData userData);
    Owner getOwnerByBranch(Branch branch);
    Admin getAdminByUserData(UserData userData);
    Admin getAdminByBranch(Branch branch);
    Teacher getTeacherByUserData(UserData userData);
    Pupil getPupilByUserData(UserData userData);
    TempUser getTempUserByUserData(UserData userData);

    List<Teacher> findAllTeachers();
    List<Teacher> findAllTeachersByBranch(Branch branch);
    List<Owner> findAllOwners();
    List<Admin> findAllAdmins();
    List<Pupil> findAllPupils();
    List<TempUser> findAllTempUsers();

    Owner saveOwner(Owner owner);
    Admin saveAdmin(Admin admin);
    Teacher saveTeacher(Teacher teacher);
    Pupil savePupil(Pupil pupil);
    TempUser saveTempUser(TempUser tempUser);


    void deleteOwnerById(Long id);
    void deleteAdminById(Long id);
    void deleteTeacherById(Long id);
    void deletePupilById(Long id);
    void deleteTempUserById(Long id);
    void deleteTempUserByIdWithoutUserData(Long id);

    UserData getUserDataByUsername(String name);

    UserData getUserDataById(Long id);

    void saveUserdata(UserData userData);


    List<Pupil> findAllPupilsByBranch(Branch branch);

    Integer getNewPupilsCountBetweenDatesByBranch(Branch branch, Date startDate, Date endDate);

    List<Teacher> findAllTeachersBySubject(Subject subject);

    List<TempUser> findAllTempUsersByBranch(Branch branch);

}

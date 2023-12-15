package kg.academia.academia_2_0.services.user;

import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.model.entities.users.*;
import kg.academia.academia_2_0.repositories.userRepos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class UserStorageImpl implements UserStorage {
    private final UserDataRepository userDataRepository;
    private final SuperAdminRepository superAdminRepository;
    private final AdminRepository adminRepository;
    private final OwnerRepository ownerRepository;
    private final TeacherRepository teacherRepository;
    private final PupilRepository pupilRepository;
    private final TempUserRepository tempUserRepository;

    @Autowired
    public UserStorageImpl(UserDataRepository userDataRepository, SuperAdminRepository superAdminRepository, AdminRepository adminRepository, OwnerRepository ownerRepository, TeacherRepository teacherRepository, PupilRepository pupilRepository, TempUserRepository tempUserRepository) {
        this.userDataRepository = userDataRepository;
        this.superAdminRepository = superAdminRepository;
        this.adminRepository = adminRepository;
        this.ownerRepository = ownerRepository;
        this.teacherRepository = teacherRepository;
        this.pupilRepository = pupilRepository;
        this.tempUserRepository = tempUserRepository;
    }


    @Override
    public Owner getOwner(Long id) {
        return ownerRepository.findById(id).orElseThrow();
        //:TODO add correct exception

    }

    @Override
    public Admin getAdmin(Long id) {
        return adminRepository.findById(id).orElseThrow();
        //:TODO add correct exception

    }

    @Override
    public Teacher getTeacher(Long id) {
        return teacherRepository.findById(id).orElseThrow();
        //:TODO add correct exception

    }

    @Override
    public Pupil getPupil(Long id) {
        return pupilRepository.findById(id).orElseThrow();
        //:TODO add correct exception

    }

    @Override
    public TempUser getTempUser(Long id) {
        return tempUserRepository.findById(id).orElseThrow();
        //:TODO add correct exception
    }

    @Override
    public SuperAdmin getSuperAdminByUserData(UserData userData) {
        return superAdminRepository.findByUserData(userData).orElse(null);
    }

    @Override
    public Owner getOwnerByUserData(UserData userData) {
        return ownerRepository.findByUserData(userData).orElse(null);
    }

    @Override
    public Owner getOwnerByBranch(Branch branch) {
        return ownerRepository.findByBranches(branch).orElse(null);
    }

    @Override
    public Admin getAdminByUserData(UserData userData) {
        return adminRepository.findByUserData(userData).orElse(null);
    }

    @Override
    public Admin getAdminByBranch(Branch branch) {
        return adminRepository.findByBranch(branch).orElse(null);
    }

    @Override
    public Teacher getTeacherByUserData(UserData userData) {
        return teacherRepository.findByUserData(userData).orElse(null);
    }

    @Override
    public Pupil getPupilByUserData(UserData userData) {
        return pupilRepository.findByUserData(userData).orElse(null);
    }

    @Override
    public TempUser getTempUserByUserData(UserData userData) {
        return tempUserRepository.findByUserData(userData).orElse(null);
    }

    @Override
    public List<Teacher> findAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public List<Teacher> findAllTeachersByBranch(Branch branch) {
        return teacherRepository.findByBranch(branch);
    }

    @Override
    public List<Owner> findAllOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public List<Admin> findAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public List<Pupil> findAllPupils() {
        return pupilRepository.findAll();
    }

    @Override
    public List<TempUser> findAllTempUsers() {
        return tempUserRepository.findAll();
    }

    @Override
    public Owner saveOwner(Owner owner) {
        userDataRepository.save(owner.getUserData());
        return ownerRepository.save(owner);
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        userDataRepository.save(admin.getUserData());
        return adminRepository.save(admin);
    }

    @Override
    public Teacher saveTeacher(Teacher teacher) {
        userDataRepository.save(teacher.getUserData());
        return teacherRepository.save(teacher);
    }

    @Override
    public Pupil savePupil(Pupil pupil) {
        userDataRepository.save(pupil.getUserData());
        return pupilRepository.save(pupil);
    }

    @Override
    public TempUser saveTempUser(TempUser tempUser) {
        userDataRepository.save(tempUser.getUserData());
        return tempUserRepository.save(tempUser);
    }


    @Override
    public void deleteOwnerById(Long id) {
        Owner owner = getOwner(id);
        UserData userData = owner.getUserData();
        ownerRepository.delete(owner);
        userDataRepository.delete(userData);
    }

    @Override
    public void deleteAdminById(Long id) {
        Admin admin = getAdmin(id);
        UserData userData = admin.getUserData();
        adminRepository.delete(admin);
        userDataRepository.delete(userData);
    }

    @Override
    public void deleteTeacherById(Long id) {
        Teacher teacher = getTeacher(id);
        UserData userData = teacher.getUserData();
        teacherRepository.delete(teacher);
        userDataRepository.delete(userData);
    }

    @Override
    public void deletePupilById(Long id) {
        Pupil pupil = getPupil(id);
        UserData userData = pupil.getUserData();
        pupilRepository.delete(pupil);
        userDataRepository.delete(userData);
    }

    @Override
    public void deleteTempUserById(Long id) {
        TempUser tempUser = getTempUser(id);
        UserData userData = tempUser.getUserData();
        tempUserRepository.delete(tempUser);
        userDataRepository.delete(userData);
    }

    @Override
    public void deleteTempUserByIdWithoutUserData(Long id) {
        tempUserRepository.deleteById(id);
    }

    @Override
    public UserData getUserDataByUsername(String name) {
        return userDataRepository.findByUsername(name).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден!"));
    }

    @Override
    public UserData getUserDataById(Long id) {
        return userDataRepository.findById(id).orElseThrow();
        //:TODO add correct exception
    }

    @Override
    public void saveUserdata(UserData userData) {
        userDataRepository.save(userData);
    }

    @Override
    public List<Pupil> findAllPupilsByBranch(Branch branch) {
        return pupilRepository.findByBranch(branch);
    }

    @Override
    public Integer getNewPupilsCountBetweenDatesByBranch(Branch branch, Date startDate, Date endDate) {

        System.out.println("start date = " + startDate);
        System.out.println("end date = " + endDate);
        System.out.println("branch name = "  + branch.getName());
        return pupilRepository.getNewPupilsCountBetweenDatesByBranch1(startDate, endDate,branch).size();
    }

    @Override
    public List<Teacher> findAllTeachersBySubject(Subject subject) {
        return teacherRepository.findBySubjects(subject);
    }

    @Override
    public List<TempUser> findAllTempUsersByBranch(Branch branch) {
        return tempUserRepository.findByBranch(branch);
    }


}

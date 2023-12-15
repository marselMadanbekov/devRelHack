package kg.academia.academia_2_0.services.user;

import kg.academia.academia_2_0.model.creations.UserCreate;
import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.model.entities.users.*;
import kg.academia.academia_2_0.model.enums.Gender;
import kg.academia.academia_2_0.model.enums.Role;
import kg.academia.academia_2_0.model.updations.UserdataUpdate;
import kg.academia.academia_2_0.services.branch.BranchStorage;
import kg.academia.academia_2_0.services.notification.NotificationService;
import kg.academia.academia_2_0.services.security.AccessGuardService;
import kg.academia.academia_2_0.services.subject.SubjectStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final BranchStorage branchStorage;
    private final PasswordEncoder passwordEncoder;
    private final UserStorage userStorage;
    private final SubjectStorage subjectStorage;
    private final AccessGuardService accessGuardService;
    private final NotificationService notificationService;
    @Autowired
    public UserServiceImpl(BranchStorage branchStorage, PasswordEncoder passwordEncoder, UserStorage userStorage, SubjectStorage subjectStorage, AccessGuardService accessGuardService, NotificationService notificationService) {
        this.branchStorage = branchStorage;
        this.passwordEncoder = passwordEncoder;
        this.userStorage = userStorage;
        this.subjectStorage = subjectStorage;
        this.accessGuardService = accessGuardService;
        this.notificationService = notificationService;
    }

    @Override
    public Owner createOwner(UserCreate userCreate) {
        userCreate.setRole(Role.ROLE_BRANCH_OWNER.ordinal());
        UserData userData = createUserData(userCreate);
        Branch branch = branchStorage.getBranchById(userCreate.getBranchId());

        Owner owner = new Owner();
        owner.setUserData(userData);
        owner.addBranch(branch);
        return userStorage.saveOwner(owner);
    }

    @Override
    public Admin createAdmin(UserCreate userCreate) {
        if (accessGuardService.isDeniedToCRUDBranchEntities(userCreate.getBranchId()))
            throw new AccessDeniedException("Вы не можете создавать администратора для данного филиала!");
        userCreate.setRole(Role.ROLE_ADMIN.ordinal());
        UserData userData = createUserData(userCreate);
        Branch branch = branchStorage.getBranchById(userCreate.getBranchId());
        deleteOldAdminOfBranch(branch);
        return userStorage.saveAdmin(
                Admin.builder()
                        .userData(userData)
                        .branch(branch)
                        .build());
    }

    @Override
    public Teacher createTeacher(UserCreate userCreate) {
        if (accessGuardService.isDeniedToCRUDBranchEntities(userCreate.getBranchId()))
            throw new AccessDeniedException("Вы не можете создавать учителя для данного филиала!");

        userCreate.setRole(Role.ROLE_TEACHER.ordinal());
        UserData userData = createUserData(userCreate);
        Branch branch = branchStorage.getBranchById(userCreate.getBranchId());

        Teacher teacher = new Teacher();
        teacher.setUserData(userData);
        teacher.setBranch(branch);
        return userStorage.saveTeacher(teacher);
    }

    @Override
    public Pupil createPupil(UserCreate userCreate) {
        if (accessGuardService.isDeniedToCRUDBranchEntities(userCreate.getBranchId()))
            throw new AccessDeniedException("Вы не можете создавать ученика для данного филиала!");

        userCreate.setRole(Role.ROLE_PUPIL.ordinal());
        UserData userData = createUserData(userCreate);
        Branch branch = branchStorage.getBranchById(userCreate.getBranchId());

        return userStorage.savePupil(
                Pupil.builder()
                        .userData(userData)
                        .branch(branch)
                        .balance(0)
                        .build());
    }

    @Override
    public TempUser createTempUser(UserCreate userCreate) {
        userCreate.setRole(Role.ROlE_TEMP_USER.ordinal());
        UserData userData = createUserData(userCreate);
        Branch branch = branchStorage.getBranchById(userCreate.getBranchId());


        TempUser tempUser = userStorage.saveTempUser(
                TempUser.builder()
                        .userData(userData)
                        .branch(branch)
                        .build());

        Admin admin = userStorage.getAdminByBranch(branch);
        if(admin != null)
            notificationService.createNewUserNotificationToAdmin(admin.getId());
        else{
            Owner owner = userStorage.getOwnerByBranch(branch);
            if(owner != null)
                notificationService.createNewUserNotificationToOwner(owner.getId());
        }
        return tempUser;
    }

    @Override
    public void addLessonToTeacher(Long teacherId, Long subjectId) {
        Teacher teacher = userStorage.getTeacher(teacherId);
        Subject subject = subjectStorage.getSubjectById(subjectId);

        teacher.addSubject(subject);

        userStorage.saveTeacher(teacher);
    }

    @Override
    public void deleteSubjectForTeacher(Long teacherId, Long subjectId) {
        Teacher teacher = userStorage.getTeacher(teacherId);
        Subject subject = subjectStorage.getSubjectById(subjectId);

        teacher.deleteSubject(subject);

        userStorage.saveTeacher(teacher);
    }

    @Override
    public void updateUserdata(UserdataUpdate update) {
        UserData userData = userStorage.getUserDataById(update.getUserdataId());
        //checking authorities
        if (accessGuardService.isDeniedToUpdateUserData(userData))
            throw new AccessDeniedException("Вы не можете изменять данные этого пользователя!");


        if (!update.getFirstname().isEmpty())
            userData.setFirstname(update.getFirstname());

        if (!update.getLastname().isEmpty())
            userData.setLastname(update.getLastname());

        if (!update.getPhoneNumber().isEmpty())
            userData.setPhoneNumber(update.getPhoneNumber());

        if (!update.getEmail().isEmpty())
            userData.setEmail(update.getEmail());

        if (!update.getDateOfBirth().isEmpty())
            userData.setDateOfBirth(Date.valueOf(update.getDateOfBirth()));

        if (!update.getPassword().isEmpty()) {
            if (!update.getPassword().equals(update.getConfirmPassword()))
                throw new RuntimeException("Пароль и подтверждение не совпадают!");
            userData.setPassword(passwordEncoder.encode(update.getPassword()));
        }

        userStorage.saveUserdata(userData);
    }

    @Override
    public void changeStatusOfUser(Long userdataId) {
        UserData userData = userStorage.getUserDataById(userdataId);
        userData.setActive(!userData.getActive());
        userStorage.saveUserdata(userData);
    }

    @Override
    public List<Teacher> findTeachersByBranch(Long branchId) {
        Branch branch = branchStorage.getBranchById(branchId);
        return userStorage.findAllTeachersByBranch(branch);
    }

    @Override
    public List<Pupil> findPupilsByBranch(Long branchId) {
        Branch branch = branchStorage.getBranchById(branchId);
        return userStorage.findAllPupilsByBranch(branch);
    }

    @Override
    public List<Pupil> findPupilsByBranch(Branch branch) {
        return userStorage.findAllPupilsByBranch(branch);
    }

    @Override
    public List<Teacher> findTeachersBySubject(Subject subject) {
        return userStorage.findAllTeachersBySubject(subject);
    }

    @Override
    public List<TempUser> findAllTempUsersByBranch(Long branchId) {
        Branch branch = branchStorage.getBranchById(branchId);
        return userStorage.findAllTempUsersByBranch(branch);
    }

    @Override
    @Transactional
    public void applyRequestOfTempUser(Long tempUserId) {
        TempUser tempUser = userStorage.getTempUser(tempUserId);
        UserData userData = tempUser.getUserData();
        userData.setRole(Role.ROLE_PUPIL);
        userStorage.savePupil(Pupil.builder()
                .userData(userData)
                .branch(tempUser.getBranch())
                .build());
        userStorage.deleteTempUserByIdWithoutUserData(tempUserId);
    }

    @Override
    public void rejectRequestOfTempUser(Long tempUserId) {
        userStorage.deleteTempUserById(tempUserId);
    }


    private UserData createUserData(UserCreate userCreate) {
        return UserData.builder()
                .active(true)
                .firstname(userCreate.getFirstname())
                .lastname(userCreate.getLastname())
                .username(userCreate.getUsername())
                .gender(Gender.values()[userCreate.getGender()])
                .password(passwordEncoder.encode(userCreate.getPassword()))
                .email(userCreate.getEmail())
                .dateOfBirth(userCreate.getDateOfBirth())
                .phoneNumber(userCreate.getPhoneNumber())
                .role(Role.values()[userCreate.getRole()])
                .build();
    }

    private void deleteOldAdminOfBranch(Branch branch) {
        try {
            Admin admin = userStorage.getAdminByBranch(branch);
            userStorage.deleteAdminById(admin.getId());
        } catch (Exception ignore) {
        }
    }
}

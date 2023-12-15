package kg.academia.academia_2_0.services.security;

import kg.academia.academia_2_0.model.entities.users.*;
import kg.academia.academia_2_0.model.enums.Role;
import kg.academia.academia_2_0.model.utilities.BranchManager;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessGuardService {
    private final ContextService contextService;
    private final UserStorage userStorage;

    @Autowired
    public AccessGuardService(ContextService contextService, UserStorage userStorage) {
        this.contextService = contextService;
        this.userStorage = userStorage;
    }

    public boolean isDeniedToCRUDBranchEntities(Long branchId) {
        UserData userData = userStorage.getUserDataByUsername(contextService.getUsernameOfCurrentUser());
        try {
            if (userData.getRole() == Role.ROLE_ADMIN || userData.getRole() == Role.ROLE_BRANCH_OWNER) {
                if (userData.getRole() == Role.ROLE_ADMIN) {
                    Admin admin = userStorage.getAdminByUserData(userData);
                    return !admin.getBranch().getId().equals(branchId);
                } else {
                    Owner owner = userStorage.getOwnerByUserData(userData);
                    return !owner.isOwnerOfBranch(branchId);
                }
            }
        } catch (Exception e) {
            return true;
        }
        return true;
    }

    public boolean isDeniedToUpdateUserData(UserData userData) {
        UserData actor = contextService.getCurrentUsersData();
        if (actor.equals(userData))
            return false;
        return switch (userData.getRole()) {
            case ROLE_SUPER_ADMIN -> true;
            case ROLE_BRANCH_OWNER -> isDeniedToUpdateOwner(userData);
            case ROLE_ADMIN -> isDeniedToUpdateAdmin(userData);
            case ROLE_TEACHER, ROLE_PUPIL, ROlE_TEMP_USER -> isDeniedToUpdateTempUserAndPupilAndTeacher(userData);
        };
    }

    private boolean isDeniedToUpdateTempUserAndPupilAndTeacher(UserData userData) {
        UserData actor = contextService.getCurrentUsersData();
        if (actor.getRole().ordinal() >= Role.ROLE_TEACHER.ordinal()
                || actor.getRole().equals(Role.ROLE_SUPER_ADMIN))
            return false;
        if (userData.getRole().equals(Role.ROlE_TEMP_USER)) {

            TempUser tempUser = userStorage.getTempUserByUserData(userData);

            BranchManager branchManager = getBranchManagerByUserData(actor);
            return !branchManager.isManagerOfBranch(tempUser.getBranch());
        } else if (userData.getRole().equals(Role.ROLE_PUPIL)) {
            Pupil pupil = userStorage.getPupilByUserData(userData);
            BranchManager branchManager = getBranchManagerByUserData(actor);
            return !branchManager.isManagerOfBranch(pupil.getBranch());
        }
        Teacher teacher = userStorage.getTeacherByUserData(userData);
        BranchManager branchManager = getBranchManagerByUserData(actor);
        return !branchManager.isManagerOfBranch(teacher.getBranch());
    }

    private boolean isDeniedToUpdateAdmin(UserData userData) {
        try {
            Owner owner = contextService.getCurrentOwner();
            Admin admin = userStorage.getAdminByUserData(userData);
            return !owner.isManagerOfBranch(admin.getBranch());
        } catch (Exception e) {
            return true;
        }
    }

    private boolean isDeniedToUpdateOwner(UserData userData) {
        try {
            SuperAdmin superAdmin = contextService.getCurrentSuperAdmin();
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private BranchManager getBranchManagerByUserData(UserData userData) {
        if (userData.getRole().equals(Role.ROLE_ADMIN))
            return userStorage.getAdminByUserData(userData);
        return userStorage.getOwnerByUserData(userData);
    }
}

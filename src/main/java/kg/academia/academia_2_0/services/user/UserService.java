package kg.academia.academia_2_0.services.user;

import kg.academia.academia_2_0.model.creations.EmployeeCreate;
import kg.academia.academia_2_0.model.updations.UserdataUpdate;

public interface UserService {
    void createEmployee(EmployeeCreate employeeCreate);

    void updateUserdata(UserdataUpdate update);


}

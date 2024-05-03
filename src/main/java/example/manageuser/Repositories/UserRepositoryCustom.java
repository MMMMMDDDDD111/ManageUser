package example.manageuser.Repositories;

import java.util.Date;

public interface UserRepositoryCustom {
    public long getMaxUserId();
    public long UpdateUser(String userNo, String userName, Date hireDate);
}

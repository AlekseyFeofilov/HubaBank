package org.huba.users.utils;

import org.huba.users.model.Privilege;
import org.huba.users.model.User;

public class Utils {
    public static boolean checkAdmin(User user) {
        for(Privilege privilege : user.getPrivileges()) {
            if(privilege.getAdmin()) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkRole(User user, String name) {
        for(Privilege privilege : user.getPrivileges()) {
            if(privilege.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}

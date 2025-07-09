package com.alexandre.inventoryservice.utils;

import org.springframework.stereotype.Service;

@Service("rolesUtil")
public class RolesUtil {
    public String getAdmin() {
        return "alex_admin";
    }

    public String getStaff() {
        return "alex_staff";
    }

    public String getAdminOrStaffExpr() {
        return "hasAnyRole('" + getAdmin() + "', '" + getStaff() + "')";
    }
}

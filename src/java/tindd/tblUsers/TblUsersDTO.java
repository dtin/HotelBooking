/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblUsers;

import java.io.Serializable;

/**
 *
 * @author Tin
 */
public class TblUsersDTO implements Serializable {

    private String userId;
    private String password;
    private String name;
    private String address;
    private String phone;
    private boolean status;
    private int roleId;

    public TblUsersDTO() {
    }

    public TblUsersDTO(String userId, String password, String name, String address, String phone, boolean status, int roleId) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

}

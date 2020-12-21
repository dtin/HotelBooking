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
public class TblUsersError implements Serializable {

    private String lengthName;
    private String lengthPassword;
    private String formatEmail;
    private String confirmPassword;
    private String lengthAddress;
    private String lengthPhone;
    private String duplicateEmail;

    public TblUsersError() {
    }

    public TblUsersError(String lengthName, String lengthPassword, String formatEmail, String confirmPassword, String lengthAddress, String lengthPhone, String duplicateEmail) {
        this.lengthName = lengthName;
        this.lengthPassword = lengthPassword;
        this.formatEmail = formatEmail;
        this.confirmPassword = confirmPassword;
        this.lengthAddress = lengthAddress;
        this.lengthPhone = lengthPhone;
        this.duplicateEmail = duplicateEmail;
    }

    public String getLengthName() {
        return lengthName;
    }

    public void setLengthName(String lengthName) {
        this.lengthName = lengthName;
    }

    public String getLengthPassword() {
        return lengthPassword;
    }

    public void setLengthPassword(String lengthPassword) {
        this.lengthPassword = lengthPassword;
    }

    public String getFormatEmail() {
        return formatEmail;
    }

    public void setFormatEmail(String formatEmail) {
        this.formatEmail = formatEmail;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getLengthAddress() {
        return lengthAddress;
    }

    public void setLengthAddress(String lengthAddress) {
        this.lengthAddress = lengthAddress;
    }

    public String getLengthPhone() {
        return lengthPhone;
    }

    public void setLengthPhone(String lengthPhone) {
        this.lengthPhone = lengthPhone;
    }

    public String getDuplicateEmail() {
        return duplicateEmail;
    }

    public void setDuplicateEmail(String duplicateEmail) {
        this.duplicateEmail = duplicateEmail;
    }

}

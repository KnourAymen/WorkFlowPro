package com.example.workflowpro;

public class Users {

    String userAddress;
    String userBirthDay;
    String userCivility;
    String userCompanyName;
    String userFirstName;
    String userLastName;
    String userPassword;
    String userPhoneNumber;
    String userTitle;
    String userTypes;
    String userWorkEmail;


    public Users() {
    }

    public Users(String userAddress, String userBirthDay, String userCivility, String userCompanyName, String userFirstName, String userLastName, String userPassword, String userPhoneNumber, String userTitle, String userTypes, String userWorkEmail) {
        this.userAddress = userAddress;
        this.userBirthDay = userBirthDay;
        this.userCivility = userCivility;
        this.userCompanyName = userCompanyName;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userPassword = userPassword;
        this.userPhoneNumber = userPhoneNumber;
        this.userTitle = userTitle;
        this.userTypes = userTypes;
        this.userWorkEmail = userWorkEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserBirthDay() {
        return userBirthDay;
    }

    public void setUserBirthDay(String userBirthDay) {
        this.userBirthDay = userBirthDay;
    }

    public String getUserCivility() {
        return userCivility;
    }

    public void setUserCivility(String userCivility) {
        this.userCivility = userCivility;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public String getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(String userTypes) {
        this.userTypes = userTypes;
    }

    public String getUserWorkEmail() {
        return userWorkEmail;
    }

    public void setUserWorkEmail(String userWorkEmail) {
        this.userWorkEmail = userWorkEmail;
    }
}

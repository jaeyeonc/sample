package com.example.domain;

public class UserData {
    private String firstName;
    private String lastName;
    private String ptnOrTelephonyNumber;
    private String idNumber;
    private String email;
    private String position;
    private String shortDiallingCode;
    private String operationalCommandUnit;
    private String callSign;
    private String organisation;
    private String row;

    // 기존 필드들에 대한 Getter/Setter 생략
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPtnOrTelephonyNumber() {
        return ptnOrTelephonyNumber;
    }
    public String getIdNumber() {
        return idNumber;
    }
    public String getEmail() {
        return email;
    }
    public String getPosition() {
        return position;
    }
    public String getShortDiallingCode() {
        return shortDiallingCode;
    }
    public String getOperationalCommandUnit() {
        return operationalCommandUnit;
    }
    public String getCallSign() {
        return callSign;
    }
    public String getOrganisation() {
        return organisation;
    }
    public String getRow() {
        return row;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setPtnOrTelephonyNumber(String ptnOrTelephonyNumber) {
        this.ptnOrTelephonyNumber = ptnOrTelephonyNumber;
    }
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public void setShortDiallingCode(String shortDiallingCode) {
        this.shortDiallingCode = shortDiallingCode;
    }
    public void setOperationalCommandUnit(String operationalCommandUnit) {
        this.operationalCommandUnit = operationalCommandUnit;
    }
    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }
    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }
    public void setRow(String row) {
        this.row = row;
    }

}
package com.example.android.iwill;

public class BeneficiariesClass {
    public String fullname;
    public String email;
    public String phone_number;
    public String relationship;
    public String legal_solicitors;

    public BeneficiariesClass(){

    }

    public BeneficiariesClass(String fullname, String email, String phone_number, String relationship, String legal_solicitors) {
        this.fullname = fullname;
        this.email = email;
        this.phone_number = phone_number;
        this.relationship = relationship;
        this.legal_solicitors = legal_solicitors;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getLegal_solicitors() {
        return legal_solicitors;
    }

    public void setLegal_solicitors(String legal_solicitors) {
        this.legal_solicitors = legal_solicitors;
    }
}

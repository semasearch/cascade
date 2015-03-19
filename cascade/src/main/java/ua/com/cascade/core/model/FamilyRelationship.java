package ua.com.cascade.core.model;


public class FamilyRelationship {

    public String firstPersonName;

    public String firstPersonDob;

    public String relationshipType;

    public String secondPersonName;

    public String secondPersonDob;

    public String getFirstPersonName() {
        return firstPersonName;
    }

    public void setFirstPersonName(String firstPersonName) {
        this.firstPersonName = firstPersonName;
    }

    public String getFirstPersonDob() {
        return firstPersonDob;
    }

    public void setFirstPersonDob(String firstPersonDob) {
        this.firstPersonDob = firstPersonDob;
    }

    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getSecondPersonName() {
        return secondPersonName;
    }

    public void setSecondPersonName(String secondPersonName) {
        this.secondPersonName = secondPersonName;
    }

    public String getSecondPersonDob() {
        return secondPersonDob;
    }

    public void setSecondPersonDob(String secondPersonDob) {
        this.secondPersonDob = secondPersonDob;
    }
}

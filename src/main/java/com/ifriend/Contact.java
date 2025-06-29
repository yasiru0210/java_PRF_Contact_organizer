package com.ifriend;

public class Contact {
    private String id;
    private String name;
    private String phoneNumber;
    private String companyName;
    private double salary;
    private String birthday;
    
    public Contact(String id, String name, String phoneNumber, String companyName, 
                   double salary, String birthday) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.salary = salary;
        this.birthday = birthday;
    }
    
    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getCompanyName() { return companyName; }
    public double getSalary() { return salary; }
    public String getBirthday() { return birthday; }
    
    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public void setSalary(double salary) { this.salary = salary; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    
    @Override
    public String toString() {
        return String.format("Contact{id='%s', name='%s', phoneNumber='%s', " +
                           "companyName='%s', salary=%.2f, birthday='%s'}", 
                           id, name, phoneNumber, companyName, salary, birthday);
    }
}
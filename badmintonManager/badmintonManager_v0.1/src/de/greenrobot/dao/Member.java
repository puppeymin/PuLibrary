package de.greenrobot.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table MemberTable.
 */
public class Member {

    private Long id;
    private String name;
    private String balance;
    private String grandTotal;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Member() {
    }

    public Member(Long id) {
        this.id = id;
    }

    public Member(Long id, String name, String balance, String grandTotal) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.grandTotal = grandTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
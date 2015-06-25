package de.greenrobot.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

import com.example.db.MemberDBHelper;

import android.content.Context;
// KEEP INCLUDES - put your custom includes here
import android.util.SparseArray;
// KEEP INCLUDES END
/**
 * Entity mapped to table MatchTable.
 */
public class Match {

    private Long id;
    private java.util.Date time;
    private String area;
    private String manager;
    private String menbers;
    private String paidMenbers;

    // KEEP FIELDS - put your custom fields here
	private String menbersString;
	private String dateString;
	private SparseArray<Member> menbersList;//参与成员
	private SparseArray<Member> paidMenbersList;//已缴费成员
    // KEEP FIELDS END

    public Match() {
    }

    public Match(Long id) {
        this.id = id;
    }

    public Match(Long id, java.util.Date time, String area, String manager, String menbers, String paidMenbers) {
        this.id = id;
        this.time = time;
        this.area = area;
        this.manager = manager;
        this.menbers = menbers;
        this.paidMenbers = paidMenbers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.util.Date getTime() {
        return time;
    }

    public void setTime(java.util.Date time) {
        this.time = time;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getMenbers() {
        return menbers;
    }

    public void setMenbers(String menbers) {
        this.menbers = menbers;
    }

    public String getPaidMenbers() {
        return paidMenbers;
    }

    public void setPaidMenbers(String paidMenbers) {
        this.paidMenbers = paidMenbers;
    }

    // KEEP METHODS - put your custom methods here
	public String getMenbersString() {
		return menbersString;
	}

	public void setMenbersString(String menbersString) {
		this.menbersString = menbersString;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public SparseArray<Member> getMenbersList() {
		return menbersList;
	}

	public void setMenbersList(SparseArray<Member> menbersList) {
		this.menbersList = menbersList;
	}

	public SparseArray<Member> getPaidMenbersList() {
		return paidMenbersList;
	}

	public void setPaidMenbersList(SparseArray<Member> paidMenbersList) {
		this.paidMenbersList = paidMenbersList;
	}

    // KEEP METHODS END

}
package com.sms.models;

import java.io.Serializable;
import java.util.*;
import java.util.Date;
import java.util.Set;

public class Semester implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id; // e.g., 2025_Spring
    private Date startDate;
    private Date endDate;
    private Set<String> courseIds = new HashSet<>();

    public Semester(String id, Date start, Date end) {
        this.id = id; setStartDate(start); setEndDate(end);
    }

    public String getId() { return id; }
    public Set<String> getCourseIds() { return courseIds; }
    public void addCourse(String courseId) { courseIds.add(courseId); }
    public void removeCourse(String courseId) { courseIds.remove(courseId); }

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
package com.sms.persistence;

import java.io.*;
import java.util.*;
import com.sms.models.*;

public class DataStore {
    private static final String DB_DIR = "data";
    private static final String STUDENT_FILE = DB_DIR + File.separator + "students.dat";
    private static final String FACULTY_FILE = DB_DIR + File.separator + "faculty.dat";
    private static final String ADMIN_FILE = DB_DIR + File.separator + "admins.dat";
    private static final String COURSE_FILE = DB_DIR + File.separator + "courses.dat";
    private static final String SEM_FILE = DB_DIR + File.separator + "sems.dat";

    public static void ensureDir() {
        File d = new File(DB_DIR);
        if (!d.exists()) d.mkdirs();
    }

    public static <T> void save(String path, T obj) {
        ensureDir();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(obj);
        } catch (Exception e) {
            System.err.println("Save error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T load(String path, T defaultObj) {
        File f = new File(path);
        if (!f.exists()) return defaultObj;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            return (T) ois.readObject();
        } catch (Exception e) {
            System.err.println("Load error: " + e.getMessage());
            return defaultObj;
        }
    }

    // convenience wrappers
    public static Map<String, Student> loadStudents() {
        return load(STUDENT_FILE, new HashMap<String, Student>());
    }
    public static void saveStudents(Map<String, Student> m) { save(STUDENT_FILE, m); }

    public static Map<String, Faculty> loadFaculty() { return load(FACULTY_FILE, new HashMap<String, Faculty>()); }
    public static void saveFaculty(Map<String, Faculty> m) { save(FACULTY_FILE, m); }

    public static Map<String, Admin> loadAdmins() { return load(ADMIN_FILE, new HashMap<String, Admin>()); }
    public static void saveAdmins(Map<String, Admin> m) { save(ADMIN_FILE, m); }

    public static Map<String, Course> loadCourses() { return load(COURSE_FILE, new HashMap<String, Course>()); }
    public static void saveCourses(Map<String, Course> m) { save(COURSE_FILE, m); }

    public static Map<String, Semester> loadSems() { return load(SEM_FILE, new HashMap<String, Semester>()); }
    public static void saveSems(Map<String, Semester> m) { save(SEM_FILE, m); }
}
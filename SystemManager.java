package com.sms.system;

import com.sms.models.*;
import com.sms.persistence.DataStore;

import java.util.*;
import java.text.SimpleDateFormat;

public class SystemManager {
    private Scanner in = new Scanner(System.in);

    private Map<String, Student> students;
    private Map<String, Faculty> faculties;
    private Map<String, Admin> admins;
    private Map<String, Course> courses;
    private Map<String, Semester> sems;

    public SystemManager() {
        DataStore.ensureDir();
        students = DataStore.loadStudents();
        faculties = DataStore.loadFaculty();
        admins = DataStore.loadAdmins();
        courses = DataStore.loadCourses();
        sems = DataStore.loadSems();

        // create default admin if none
        if (admins.isEmpty()) {
            Admin a = new Admin("A001", "Administrator", "admin@univ.edu", "admin");
            admins.put(a.getId(), a);
            DataStore.saveAdmins(admins);
        }
    }

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Semester Management System ===");
            System.out.println("1. Admin login");
            System.out.println("2. Faculty login");
            System.out.println("3. Student login");
            System.out.println("4. Exit");
            System.out.print("Choose: ");
            String choice = in.nextLine().trim();
            switch (choice) {
                case "1": adminMenu(); break;
                case "2": facultyMenu(); break;
                case "3": studentMenu(); break;
                case "4": saveAll(); running = false; break;
                default: System.out.println("Invalid choice");
            }
        }
        System.out.println("Goodbye!");
    }

    private void adminMenu() {
        System.out.print("Enter admin id: ");
        String id = in.nextLine().trim();
        Admin a = admins.get(id);
        if (a == null) { System.out.println("No admin with that id"); return; }
        System.out.print("Password: ");
        String pw = in.nextLine().trim();
        if (!a.checkPassword(pw)) { System.out.println("Wrong password"); return; }

        boolean back = false;
        while (!back) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add student");
            System.out.println("2. Add faculty");
            System.out.println("3. Add course");
            System.out.println("4. Create semester");
            System.out.println("5. Assign course to semester");
            System.out.println("6. Assign faculty to course");
            System.out.println("7. View reports");
            System.out.println("8. Back");
            System.out.print("Choose: ");
            String c = in.nextLine().trim();
            switch (c) {
                case "1": addStudent(); break;
                case "2": addFaculty(); break;
                case "3": addCourse(); break;
                case "4": createSemester(); break;
                case "5": assignCourseToSemester(); break;
                case "6": assignFacultyToCourse(); break;
                case "7": viewReports(); break;
                case "8": back = true; break;
                default: System.out.println("Invalid");
            }
        }
    }

    private void facultyMenu() {
        System.out.print("Enter faculty id: ");
        String id = in.nextLine().trim();
        Faculty f = faculties.get(id);
        if (f == null) { System.out.println("No faculty"); return; }
        System.out.print("Password: ");
        String pw = in.nextLine().trim();
        if (!f.checkPassword(pw)) { System.out.println("Wrong password"); return; }

        boolean back = false;
        while (!back) {
            System.out.println("\n--- Faculty Menu ---");
            System.out.println("1. View my courses");
            System.out.println("2. Record grade for student");
            System.out.println("3. Mark attendance for student");
            System.out.println("4. Back");
            System.out.print("Choose: ");
            String c = in.nextLine().trim();
            switch (c) {
                case "1": viewFacultyCourses(f); break;
                case "2": recordGrade(f); break;
                case "3": markAttendance(f); break;
                case "4": back = true; break;
                default: System.out.println("Invalid");
            }
        }
    }

    private void studentMenu() {
        System.out.print("Enter student id: ");
        String id = in.nextLine().trim();
        Student s = students.get(id);
        if (s == null) { System.out.println("No student"); return; }
        System.out.print("Password: ");
        String pw = in.nextLine().trim();
        if (!s.checkPassword(pw)) { System.out.println("Wrong password"); return; }

        boolean back = false;
        while (!back) {
            System.out.println("\n--- Student Menu ---");
            System.out.println("1. View available courses");
            System.out.println("2. Enroll in course");
            System.out.println("3. Drop course");
            System.out.println("4. View my grades & GPA");
            System.out.println("5. Back");
            System.out.print("Choose: ");
            String c = in.nextLine().trim();
            switch (c) {
                case "1": viewAllCourses(); break;
                case "2": studentEnroll(s); break;
                case "3": studentDrop(s); break;
                case "4": viewStudentGrades(s); break;
                case "5": back = true; break;
                default: System.out.println("Invalid");
            }
        }
    }

    // Admin actions
    private void addStudent() {
        System.out.print("Student id: "); String id = in.nextLine().trim();
        if (students.containsKey(id)) { System.out.println("Already exists"); return; }
        System.out.print("Name: "); String name = in.nextLine().trim();
        System.out.print("Email: "); String email = in.nextLine().trim();
        System.out.print("Password: "); String pw = in.nextLine().trim();
        System.out.print("Department: "); String dept = in.nextLine().trim();
        Student s = new Student(id, name, email, pw, dept);
        students.put(id, s);
        DataStore.saveStudents(students);
        System.out.println("Student added.");
    }

    private void addFaculty() {
        System.out.print("Faculty id: "); String id = in.nextLine().trim();
        if (faculties.containsKey(id)) { System.out.println("Already exists"); return; }
        System.out.print("Name: "); String name = in.nextLine().trim();
        System.out.print("Email: "); String email = in.nextLine().trim();
        System.out.print("Password: "); String pw = in.nextLine().trim();
        System.out.print("Department: "); String dept = in.nextLine().trim();
        Faculty f = new Faculty(id, name, email, pw, dept);
        faculties.put(id, f);
        DataStore.saveFaculty(faculties);
        System.out.println("Faculty added.");
    }

    private void addCourse() {
        System.out.print("Course id: "); String id = in.nextLine().trim();
        if (courses.containsKey(id)) { System.out.println("Already exists"); return; }
        System.out.print("Title: "); String title = in.nextLine().trim();
        System.out.print("Credits (int): "); int credits = parseIntSafe(in.nextLine().trim(), 3);
        System.out.print("Capacity: "); int cap = parseIntSafe(in.nextLine().trim(), 30);
        Course c = new Course(id, title, credits, cap);
        courses.put(id, c);
        DataStore.saveCourses(courses);
        System.out.println("Course added.");
    }

    private void createSemester() {
        System.out.print("Semester id (e.g., 2025_Spring): "); String id = in.nextLine().trim();
        if (sems.containsKey(id)) { System.out.println("Already exists"); return; }
        System.out.print("Start date (yyyy-MM-dd): "); String sdate = in.nextLine().trim();
        System.out.print("End date (yyyy-MM-dd): "); String edate = in.nextLine().trim();
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date st = fmt.parse(sdate);
            Date en = fmt.parse(edate);
            Semester sem = new Semester(id, st, en);
            sems.put(id, sem);
            DataStore.saveSems(sems);
            System.out.println("Semester created.");
        } catch (Exception e) { System.out.println("Date parse error"); }
    }

    private void assignCourseToSemester() {
        System.out.print("Semester id: "); String sid = in.nextLine().trim();
        Semester sem = sems.get(sid); if (sem == null) { System.out.println("No such semester"); return; }
        System.out.print("Course id: "); String cid = in.nextLine().trim();
        Course c = courses.get(cid); if (c == null) { System.out.println("No such course"); return; }
        sem.addCourse(cid);
        DataStore.saveSems(sems);
        System.out.println("Course assigned to semester.");
    }

    private void assignFacultyToCourse() {
        System.out.print("Course id: "); String cid = in.nextLine().trim();
        Course c = courses.get(cid); if (c == null) { System.out.println("No such course"); return; }
        System.out.print("Faculty id: "); String fid = in.nextLine().trim();
        Faculty f = faculties.get(fid); if (f == null) { System.out.println("No such faculty"); return; }
        c.setFacultyId(fid);
        DataStore.saveCourses(courses);
        System.out.println("Faculty assigned to course.");
    }

    private void viewReports() {
        System.out.println("Students:"); students.values().forEach(s -> System.out.println("  " + s));
        System.out.println("Courses:"); courses.values().forEach(c -> System.out.println("  " + c));
        System.out.println("Faculty:"); faculties.values().forEach(f -> System.out.println("  " + f));
        System.out.println("Semesters:"); sems.forEach((k,v) -> System.out.println("  " + k + " -> courses: " + v.getCourseIds()));
    }

    // Faculty actions
    private void viewFacultyCourses(Faculty f) {
        System.out.println("My courses:");
        courses.values().stream().filter(c -> f.getId().equals(c.getFacultyId())).forEach(c -> System.out.println("  " + c));
    }

    private void recordGrade(Faculty f) {
        System.out.print("Course id: "); String cid = in.nextLine().trim();
        Course c = courses.get(cid); if (c == null) { System.out.println("No such course"); return; }
        if (!f.getId().equals(c.getFacultyId())) { System.out.println("You are not assigned to this course"); return; }
        System.out.print("Student id: "); String sid = in.nextLine().trim();
        Student s = students.get(sid); if (s == null) { System.out.println("No such student"); return; }
        if (!c.getEnrolledStudentIds().contains(sid)) { System.out.println("Student not enrolled in this course"); return; }
        System.out.print("Grade point (0.0 - 4.0): "); double gp = parseDoubleSafe(in.nextLine().trim(), 0.0);
        s.setGrade(cid, gp);
        DataStore.saveStudents(students);
        System.out.println("Grade recorded.");
    }

    private void markAttendance(Faculty f) {
        System.out.print("Course id: "); String cid = in.nextLine().trim();
        Course c = courses.get(cid); if (c == null) { System.out.println("No such course"); return; }
        if (!f.getId().equals(c.getFacultyId())) { System.out.println("You are not assigned to this course"); return; }
        System.out.print("Student id: "); String sid = in.nextLine().trim();
        Student s = students.get(sid); if (s == null) { System.out.println("No such student"); return; }
        if (!c.getEnrolledStudentIds().contains(sid)) { System.out.println("Student not enrolled in this course"); return; }
        System.out.print("Days to add: "); int days = parseIntSafe(in.nextLine().trim(), 1);
        s.addAttendance(cid, days);
        DataStore.saveStudents(students);
        System.out.println("Attendance updated.");
    }

    // Student actions
    private void viewAllCourses() {
        System.out.println("Available courses:");
        courses.values().forEach(c -> System.out.println("  " + c));
    }

    private void studentEnroll(Student s) {
        System.out.print("Course id: "); String cid = in.nextLine().trim();
        Course c = courses.get(cid); if (c == null) { System.out.println("No such course"); return; }
        boolean ok = c.enroll(s.getId());
        if (!ok) { System.out.println("Enroll failed (maybe full or already enrolled)"); return; }
        DataStore.saveCourses(courses);
        System.out.println("Enrolled successfully.");
    }

    private void studentDrop(Student s) {
        System.out.print("Course id: "); String cid = in.nextLine().trim();
        Course c = courses.get(cid); if (c == null) { System.out.println("No such course"); return; }
        boolean ok = c.drop(s.getId());
        if (!ok) { System.out.println("Drop failed (maybe not enrolled)"); return; }
        DataStore.saveCourses(courses);
        System.out.println("Dropped successfully.");
    }

    private void viewStudentGrades(Student s) {
        System.out.println("Grades:");
        Map<String, Integer> courseCredits = new HashMap<>();
        courses.values().forEach(c -> courseCredits.put(c.getId(), c.getCredits()));
        s.getAllGrades().forEach((k,v) -> System.out.println("  " + k + ": " + v));
        double gpa = s.calculateGPA(courseCredits);
        System.out.printf("GPA: %.2f\n", gpa);
    }

    // helpers
    private int parseIntSafe(String s, int def) { try { return Integer.parseInt(s); } catch (Exception e) { return def; } }
    private double parseDoubleSafe(String s, double def) { try { return Double.parseDouble(s); } catch (Exception e) { return def; } }

    private void saveAll() {
        DataStore.saveStudents(students);
        DataStore.saveFaculty(faculties);
        DataStore.saveAdmins(admins);
        DataStore.saveCourses(courses);
        DataStore.saveSems(sems);
    }
}
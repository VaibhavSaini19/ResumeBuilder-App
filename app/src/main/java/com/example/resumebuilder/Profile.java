package com.example.resumebuilder;

import android.net.Uri;

import java.util.ArrayList;

public class Profile {
    private String profileId, name, category, profilePic;
    private String address, email, contact;                                 // Personal details
    private ArrayList<Education> educationArrayList = new ArrayList<>();    // Education
    private ArrayList<Experience> experienceArrayList = new ArrayList<>();  // Experience
    private ArrayList<Skill> skillArrayList = new ArrayList<>();            // Skill
    private String objective;                                               // Objective
    private ArrayList<Project> projectArrayList = new ArrayList<>();        // Project

    public Profile(){
        // Empty constructor
    }

    public Profile(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getProfileId() {
        return profileId;
    }
    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getProfilePic() {
        return profilePic;
    }
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    public ArrayList<Education> getEducationArrayList() {
        return educationArrayList;
    }
    public void setEducationArrayList(ArrayList<Education> educationArrayList) {
        this.educationArrayList = educationArrayList;
    }

    public ArrayList<Experience> getExperienceArrayList() {
        return experienceArrayList;
    }
    public void setExperienceArrayList(ArrayList<Experience> experienceArrayList) {
        this.experienceArrayList = experienceArrayList;
    }

    public ArrayList<Skill> getSkillArrayList() {
        return skillArrayList;
    }
    public void setSkillArrayList(ArrayList<Skill> skillArrayList) {
        this.skillArrayList = skillArrayList;
    }

    public String getObjective() {
        return objective;
    }
    public void setObjective(String objective) {
        this.objective = objective;
    }

    public ArrayList<Project> getProjectArrayList() {
        return projectArrayList;
    }
    public void setProjectArrayList(ArrayList<Project> projectArrayList) {
        this.projectArrayList = projectArrayList;
    }


    // Helper classes

    public static class Education {
        private String degree, university, grade, year;
        public Education(){
            // Empty constructor
        }
        public Education(String degree, String university, String grade, String year){
            this.degree = degree;
            this.university = university;
            this.grade = grade;
            this.year = year;
        }

        public void setDegree(String degree) {
            this.degree = degree;
        }
        public String getDegree() {
            return degree;
        }

        public void setUniversity(String university) {
            this.university = university;
        }
        public String getUniversity() {
            return university;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }
        public String getGrade() {
            return grade;
        }

        public void setYear(String year) {
            this.year = year;
        }
        public String getYear() {
            return year;
        }
    }

    public static class Experience {
        private String company, job, start, end, description;
        public Experience(){ }
        public Experience(String company, String job, String start, String end, String description){
            this.company = company;
            this.job = job;
            this.start = start;
            this.end = end;
            this.description = description;
        }

        public void setCompany(String company) {
            this.company = company;
        }
        public String getCompany() {
            return company;
        }

        public void setJob(String job) {
            this.job = job;
        }
        public String getJob() {
            return job;
        }

        public void setStart(String start) {
            this.start = start;
        }
        public String getStart() {
            return start;
        }

        public void setEnd(String end) {
            this.end = end;
        }
        public String getEnd() {
            return end;
        }

        public void setDescription(String description) {
            this.description = description;
        }
        public String getDescription() {
            return description;
        }
    }

    public static class Skill {
        private String name, level;
        public Skill () { }
        public Skill (String name, String level){
            this.name = name;
            this.level = level;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getLevel() {
            return level;
        }
    }

    public static class Project {
        private String title, description;
        public Project () { }
        public Project (String title, String description){
            this.title = title;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}


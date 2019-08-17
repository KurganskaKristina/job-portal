package com.rutik.entity;

public class VacancyEntity {

    private String section;
    private String name;
    private String url;
    private String description;


    public VacancyEntity(String section, String name, String url, String description) {
        this.section = section;
        this.name = name;
        this.url = url;
        this.description = description;
    }

    public String getSection() {
        return section;
    }
    public void setSection(String section) {
        this.section = section;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}

package com.jcg.jaxb.json;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ashraf_sarhan
 *
 */
@XmlRootElement
public class Employee {

    private int id;

    private String name;

    private List skills;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getSkills() {
        return skills;
    }

    public void setSkills(List skills) {
        this.skills = skills;
    }

}
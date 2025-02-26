package cn.picc.finance.sample.model;

import java.io.Serializable;

public class TestTeacher implements Serializable {
    private static final long serialVersionUID = -484241838095280808L;

    private String id;
    private String name;
    private int age;
    private String className;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.hie2j.bmob_studentlist;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Student extends BmobObject {
    private String name;
    private int age;
    private String profession;
    private int score;
    private BmobFile headimg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public BmobFile getHeadimg() {
        return headimg;
    }

    public void setHeadimg(BmobFile headimg) {
        this.headimg = headimg;
    }

    @Override
    public String toString() {
        return name + " 年龄" + age + " 专业" +
                profession + " 分数 " +score;
    }
}

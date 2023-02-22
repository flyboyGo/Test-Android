package com.example.chapter12_network_communication.gson.bean;

public class Job {

    private String name;
    private int salary;

    public Job() {
    }

    public Job(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Job{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}

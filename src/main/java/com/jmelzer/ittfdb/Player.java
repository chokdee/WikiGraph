package com.jmelzer.ittfdb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by J. Melzer on 30.03.2016.
 */
public class Player {

    String id;
    String name;

    List<Result> results = new ArrayList<>();

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Player() {
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

    public void addResult(Result result) {
        results.add(result);
    }

    public List<Result> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

package com.jmelzer.ittfdb;

/**
 * Created by J. Melzer on 30.03.2016.
 * Turnier.
 */
public class Tournament {
    String name;
    String type;
    String city;
    String country;
    String year;

    public Tournament(String name, String type, String city, String country, String year) {
        this.name = name;
        this.type = type;
        this.city = city;
        this.country = country;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tournament that = (Tournament) o;

        return name.equals(that.name);

    }

    public String getYear() {
        return year;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }


    public String getType() {
        return type;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}

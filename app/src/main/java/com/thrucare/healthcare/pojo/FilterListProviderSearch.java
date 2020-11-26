package com.thrucare.healthcare.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterListProviderSearch {

    public class Category {

        @SerializedName("Counselling")
        @Expose
        private Integer counselling;
        @SerializedName("Test")
        @Expose
        private Integer test;

        public Integer getCounselling() {
            return counselling;
        }

        public void setCounselling(Integer counselling) {
            this.counselling = counselling;
        }

        public Integer getTest() {
            return test;
        }

        public void setTest(Integer test) {
            this.test = test;
        }

    }

    public class Characteristic {
        @SerializedName("Wheelchair")
        @Expose
        private Integer wheelchair;

        public Integer getWheelchair() {
            return wheelchair;
        }

        public void setWheelchair(Integer wheelchair) {
            this.wheelchair = wheelchair;
        }

    }

    public class Distance {

        @SerializedName("1")
        @Expose
        private Integer _1;
        @SerializedName("3")
        @Expose
        private Integer _3;
        @SerializedName("5")
        @Expose
        private Integer _5;
        @SerializedName("10")
        @Expose
        private Integer _10;

        public Integer get1() {
            return _1;
        }

        public void set1(Integer _1) {
            this._1 = _1;
        }

        public Integer get3() {
            return _3;
        }

        public void set3(Integer _3) {
            this._3 = _3;
        }

        public Integer get5() {
            return _5;
        }

        public void set5(Integer _5) {
            this._5 = _5;
        }

        public Integer get10() {
            return _10;
        }

        public void set10(Integer _10) {
            this._10 = _10;
        }

    }

    public class Facets {

        @SerializedName("category")
        @Expose
        private Category category;
        @SerializedName("speciality")
        @Expose
        private Speciality speciality;
        @SerializedName("type")
        @Expose
        private Type type;
        @SerializedName("characteristic")
        @Expose
        private Characteristic characteristic;
        @SerializedName("distance")
        @Expose
        private Distance distance;
        @SerializedName("schedule")
        @Expose
        private Schedule schedule;
        @SerializedName("gender")
        @Expose
        private Gender gender;
        @SerializedName("rating")
        @Expose
        private Rating rating;
        @SerializedName("insurance")
        @Expose
        private Insurance insurance;

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public Speciality getSpeciality() {
            return speciality;
        }

        public void setSpeciality(Speciality speciality) {
            this.speciality = speciality;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public Characteristic getCharacteristic() {
            return characteristic;
        }

        public void setCharacteristic(Characteristic characteristic) {
            this.characteristic = characteristic;
        }

        public Distance getDistance() {
            return distance;
        }

        public void setDistance(Distance distance) {
            this.distance = distance;
        }

        public Schedule getSchedule() {
            return schedule;
        }

        public void setSchedule(Schedule schedule) {
            this.schedule = schedule;
        }

        public Gender getGender() {
            return gender;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Rating getRating() {
            return rating;
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public Insurance getInsurance() {
            return insurance;
        }

        public void setInsurance(Insurance insurance) {
            this.insurance = insurance;
        }

    }

    public class Gender {

        @SerializedName("M")
        @Expose
        private Integer m;
        @SerializedName("F")
        @Expose
        private Integer f;

        public Integer getM() {
            return m;
        }

        public void setM(Integer m) {
            this.m = m;
        }

        public Integer getF() {
            return f;
        }

        public void setF(Integer f) {
            this.f = f;
        }
    }


    public class Hit {

        @SerializedName("uuid")
        @Expose
        private String uuid;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("middle_name")
        @Expose
        private String middleName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("metadata")
        @Expose
        private Metadata metadata;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Metadata getMetadata() {
            return metadata;
        }

        public void setMetadata(Metadata metadata) {
            this.metadata = metadata;
        }

    }

    public class Insurance {

        @SerializedName("BCBS")
        @Expose
        private Integer bCBS;
        @SerializedName("Cigna")
        @Expose
        private Integer cigna;
        @SerializedName("Aetna")
        @Expose
        private Integer aetna;

        public Integer getBCBS() {
            return bCBS;
        }

        public void setBCBS(Integer bCBS) {
            this.bCBS = bCBS;
        }

        public Integer getCigna() {
            return cigna;
        }

        public void setCigna(Integer cigna) {
            this.cigna = cigna;
        }

        public Integer getAetna() {
            return aetna;
        }

        public void setAetna(Integer aetna) {
            this.aetna = aetna;
        }
    }


    public class Location {

        @SerializedName("uuid")
        @Expose
        private String uuid;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("address")
        @Expose
        private Object address;
        @SerializedName("line1")
        @Expose
        private String line1;
        @SerializedName("line2")
        @Expose
        private String line2;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("country")
        @Expose
        private String country;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public String getLine1() {
            return line1;
        }

        public void setLine1(String line1) {
            this.line1 = line1;
        }

        public String getLine2() {
            return line2;
        }

        public void setLine2(String line2) {
            this.line2 = line2;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    public class Metadata {

        @SerializedName("organization")
        @Expose
        private Organization organization;
        @SerializedName("services")
        @Expose
        private List<Service> services = null;

        public Organization getOrganization() {
            return organization;
        }

        public void setOrganization(Organization organization) {
            this.organization = organization;
        }

        public List<Service> getServices() {
            return services;
        }

        public void setServices(List<Service> services) {
            this.services = services;
        }

    }

    public class Organization {

        @SerializedName("uuid")
        @Expose
        private String uuid;
        @SerializedName("name")
        @Expose
        private String name;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public class Rating {

        @SerializedName("0")
        @Expose
        private Integer _0;
        @SerializedName("1")
        @Expose
        private Integer _1;
        @SerializedName("2")
        @Expose
        private Integer _2;
        @SerializedName("3")
        @Expose
        private Integer _3;
        @SerializedName("4")
        @Expose
        private Integer _4;
        @SerializedName("5")
        @Expose
        private Integer _5;

        public Integer get0() {
            return _0;
        }

        public void set0(Integer _0) {
            this._0 = _0;
        }

        public Integer get1() {
            return _1;
        }

        public void set1(Integer _1) {
            this._1 = _1;
        }

        public Integer get2() {
            return _2;
        }

        public void set2(Integer _2) {
            this._2 = _2;
        }

        public Integer get3() {
            return _3;
        }

        public void set3(Integer _3) {
            this._3 = _3;
        }

        public Integer get4() {
            return _4;
        }

        public void set4(Integer _4) {
            this._4 = _4;
        }

        public Integer get5() {
            return _5;
        }

        public void set5(Integer _5) {
            this._5 = _5;
        }

    }


    public class ResponseFilterSearch {

        @SerializedName("results")
        @Expose
        private List<Result> results = null;
        @SerializedName("facets")
        @Expose
        private Facets facets;

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }

        public Facets getFacets() {
            return facets;
        }

        public void setFacets(Facets facets) {
            this.facets = facets;
        }

    }


    public class Result {

        @SerializedName("hits")
        @Expose
        private List<Hit> hits = null;

        public List<Hit> getHits() {
            return hits;
        }

        public void setHits(List<Hit> hits) {
            this.hits = hits;
        }

    }

    public class Schedule {

        @SerializedName("1")
        @Expose
        private Integer _1;
        @SerializedName("3")
        @Expose
        private Integer _3;
        @SerializedName("5")
        @Expose
        private Integer _5;
        @SerializedName("10")
        @Expose
        private Integer _10;

        public Integer get1() {
            return _1;
        }

        public void set1(Integer _1) {
            this._1 = _1;
        }

        public Integer get3() {
            return _3;
        }

        public void set3(Integer _3) {
            this._3 = _3;
        }

        public Integer get5() {
            return _5;
        }

        public void set5(Integer _5) {
            this._5 = _5;
        }

        public Integer get10() {
            return _10;
        }

        public void set10(Integer _10) {
            this._10 = _10;
        }

    }


    public class Service {

        @SerializedName("uuid")
        @Expose
        private String uuid;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("location")
        @Expose
        private Location location;
        @SerializedName("role")
        @Expose
        private List<String> role = null;
        @SerializedName("category")
        @Expose
        private List<String> category = null;
        @SerializedName("type")
        @Expose
        private List<String> type = null;
        @SerializedName("speciality")
        @Expose
        private List<String> speciality = null;
        @SerializedName("characteristic")
        @Expose
        private List<String> characteristic = null;
        @SerializedName("insurance")
        @Expose
        private List<String> insurance = null;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public List<String> getRole() {
            return role;
        }

        public void setRole(List<String> role) {
            this.role = role;
        }

        public List<String> getCategory() {
            return category;
        }

        public void setCategory(List<String> category) {
            this.category = category;
        }

        public List<String> getType() {
            return type;
        }

        public void setType(List<String> type) {
            this.type = type;
        }

        public List<String> getSpeciality() {
            return speciality;
        }

        public void setSpeciality(List<String> speciality) {
            this.speciality = speciality;
        }

        public List<String> getCharacteristic() {
            return characteristic;
        }

        public void setCharacteristic(List<String> characteristic) {
            this.characteristic = characteristic;
        }

        public List<String> getInsurance() {
            return insurance;
        }

        public void setInsurance(List<String> insurance) {
            this.insurance = insurance;
        }

    }


    public class Speciality {

        @SerializedName("Posttraumatic stress disorder")
        @Expose
        private Integer posttraumaticStressDisorder;
        @SerializedName("Test")
        @Expose
        private Integer test;

        public Integer getPosttraumaticStressDisorder() {
            return posttraumaticStressDisorder;
        }

        public void setPosttraumaticStressDisorder(Integer posttraumaticStressDisorder) {
            this.posttraumaticStressDisorder = posttraumaticStressDisorder;
        }

        public Integer getTest() {
            return test;
        }

        public void setTest(Integer test) {
            this.test = test;
        }

    }


    public class Type {

        @SerializedName("Psychotherapy")
        @Expose
        private Integer psychotherapy;
        @SerializedName("Psychiatry")
        @Expose
        private Integer psychiatry;

        public Integer getPsychotherapy() {
            return psychotherapy;
        }

        public void setPsychotherapy(Integer psychotherapy) {
            this.psychotherapy = psychotherapy;
        }

        public Integer getPsychiatry() {
            return psychiatry;
        }

        public void setPsychiatry(Integer psychiatry) {
            this.psychiatry = psychiatry;
        }

    }
}

package com.thrucare.healthcare.activity.provider.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ProviderServiceList {

    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("metadata")
    @Expose
    private Metadata metadata;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public class Item {

        @SerializedName("uuid")
        @Expose
        private String uuid;
        @SerializedName("service")
        @Expose
        private Service service;
        @SerializedName("organization")
        @Expose
        private Organization organization;
        @SerializedName("location")
        @Expose
        private Location location;
        @SerializedName("provider")
        @Expose
        private Provider provider;
        @SerializedName("role")
        @Expose
        private List<Role> role = null;
        @SerializedName("speciality")
        @Expose
        private List<Speciality> speciality = null;
        @SerializedName("period")
        @Expose
        private Period period;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public Service getService() {
            return service;
        }

        public void setService(Service service) {
            this.service = service;
        }

        public Organization getOrganization() {
            return organization;
        }

        public void setOrganization(Organization organization) {
            this.organization = organization;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Provider getProvider() {
            return provider;
        }

        public void setProvider(Provider provider) {
            this.provider = provider;
        }

        public List<Role> getRole() {
            return role;
        }

        public void setRole(mList<Role> role) {
            this.role = role;
        }

        public List<Speciality> getSpeciality() {
            return speciality;
        }

        public void setSpeciality(List<Speciality> speciality) {
            this.speciality = speciality;
        }

        public Period getPeriod() {
            return period;
        }

        public void setPeriod(Period period) {
            this.period = period;
        }

    }

    public class Location {

        @SerializedName("uuid")
        @Expose
        private String uuid;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

    }

    public class Metadata {

        @SerializedName("limit")
        @Expose
        private Integer limit;
        @SerializedName("offset")
        @Expose
        private Integer offset;
        @SerializedName("count")
        @Expose
        private Integer count;
        @SerializedName("number")
        @Expose
        private Integer number;

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public Integer getOffset() {
            return offset;
        }

        public void setOffset(Integer offset) {
            this.offset = offset;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

    }

    public class Organization {

        @SerializedName("uuid")
        @Expose
        private String uuid;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

    }

    public class Period {

        @SerializedName("start")
        @Expose
        private String start;
        @SerializedName("end")
        @Expose
        private String end;

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

    }

    public class Provider {

        @SerializedName("uuid")
        @Expose
        private String uuid;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

    }

    public class Role {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("display")
        @Expose
        private String display;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

    }

    public class Service {

        @SerializedName("uuid")
        @Expose
        private String uuid;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

    }

    public class Speciality {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("display")
        @Expose
        private String display;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

    }


}


package com.thrucare.healthcare.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImmunizationList {
    public class Item {

        @SerializedName("uuid")
        @Expose
        private String uuid;
        @SerializedName("vaccine")
        @Expose
        private Vaccine vaccine;
        @SerializedName("origin")
        @Expose
        private Origin origin;
        @SerializedName("status")
        @Expose
        private Status status;
        @SerializedName("occur_date")
        @Expose
        private String occurDate;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public Vaccine getVaccine() {
            return vaccine;
        }

        public void setVaccine(Vaccine vaccine) {
            this.vaccine = vaccine;
        }

        public Origin getOrigin() {
            return origin;
        }

        public void setOrigin(Origin origin) {
            this.origin = origin;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public String getOccurDate() {
            return occurDate;
        }

        public void setOccurDate(String occurDate) {
            this.occurDate = occurDate;
        }

    }

    public class Links {

        @SerializedName("first")
        @Expose
        private String first;
        @SerializedName("next")
        @Expose
        private String next;
        @SerializedName("prev")
        @Expose
        private String prev;
        @SerializedName("last")
        @Expose
        private String last;

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getPrev() {
            return prev;
        }

        public void setPrev(String prev) {
            this.prev = prev;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
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

    public class Origin {

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

    public class ResponseListImmunization {

        @SerializedName("items")
        @Expose
        private List<Item> items = null;
        @SerializedName("metadata")
        @Expose
        private Metadata metadata;
        @SerializedName("links")
        @Expose
        private Links links;

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

        public Links getLinks() {
            return links;
        }

        public void setLinks(Links links) {
            this.links = links;
        }

    }

    public class Status {

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

    public class Vaccine {

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

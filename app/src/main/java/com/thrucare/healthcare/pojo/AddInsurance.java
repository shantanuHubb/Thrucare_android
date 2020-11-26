package com.thrucare.healthcare.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddInsurance {
    public class Item {

        @SerializedName("uuid")
        @Expose
        private String uuid;
        @SerializedName("payer")
        @Expose
        private Payer payer;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public Payer getPayer() {
            return payer;
        }

        public void setPayer(Payer payer) {
            this.payer = payer;
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

    public class Payer {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("display")
        @Expose
        private String display;
        @SerializedName("product")
        @Expose
        private List<Product> product = null;

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

        public List<Product> getProduct() {
            return product;
        }

        public void setProduct(List<Product> product) {
            this.product = product;
        }

    }

    public class Product {

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

    public class ResponseAddInsurance {

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
}

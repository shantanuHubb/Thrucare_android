package com.thrucare.healthcare.pojo.modelClasses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class PatientInsuranceList {

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
    public class Attachment {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("url")
        @Expose
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }


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
    public class Payer {

        @SerializedName("uuid")
        @Expose
        private String uuid;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("display")
        @Expose
        private String display;
        @SerializedName("product")
        @Expose
        private List<Product> product = null;
        @SerializedName("attachments")
        @Expose
        private List<Attachment> attachments = null;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

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

        public List<Attachment> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<Attachment> attachments) {
            this.attachments = attachments;
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
    }



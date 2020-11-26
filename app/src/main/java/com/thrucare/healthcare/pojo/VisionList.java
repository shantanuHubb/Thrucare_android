package com.thrucare.healthcare.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VisionList {

    public class Item {

        @SerializedName("uuid")
        @Expose
        private String uuid;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("provider")
        @Expose
        private Provider provider;
        @SerializedName("status")
        @Expose
        private Status status;
        @SerializedName("specification")
        @Expose
        private List<Specification> specification = null;
        @SerializedName("notes")
        @Expose
        private String notes;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public Provider getProvider() {
            return provider;
        }

        public void setProvider(Provider provider) {
            this.provider = provider;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public List<Specification> getSpecification() {
            return specification;
        }

        public void setSpecification(List<Specification> specification) {
            this.specification = specification;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
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

    public class Prism {

        @SerializedName("amount")
        @Expose
        private Double amount;
        @SerializedName("base")
        @Expose
        private String base;

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

    }

    public class Provider {

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

    }


    public class ResponseListVision {

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

    public class Specification {

        @SerializedName("product")
        @Expose
        private String product;
        @SerializedName("eye")
        @Expose
        private String eye;
        @SerializedName("sphere")
        @Expose
        private Integer sphere;
        @SerializedName("cylinder")
        @Expose
        private Double cylinder;
        @SerializedName("axis")
        @Expose
        private Integer axis;
        @SerializedName("prism")
        @Expose
        private Prism prism;
        @SerializedName("add")
        @Expose
        private Integer add;
        @SerializedName("power")
        @Expose
        private Double power;
        @SerializedName("backCurve")
        @Expose
        private Double backCurve;
        @SerializedName("diameter")
        @Expose
        private Integer diameter;
        @SerializedName("color")
        @Expose
        private String color;
        @SerializedName("brand")
        @Expose
        private String brand;

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getEye() {
            return eye;
        }

        public void setEye(String eye) {
            this.eye = eye;
        }

        public Integer getSphere() {
            return sphere;
        }

        public void setSphere(Integer sphere) {
            this.sphere = sphere;
        }

        public Double getCylinder() {
            return cylinder;
        }

        public void setCylinder(Double cylinder) {
            this.cylinder = cylinder;
        }

        public Integer getAxis() {
            return axis;
        }

        public void setAxis(Integer axis) {
            this.axis = axis;
        }

        public Prism getPrism() {
            return prism;
        }

        public void setPrism(Prism prism) {
            this.prism = prism;
        }

        public Integer getAdd() {
            return add;
        }

        public void setAdd(Integer add) {
            this.add = add;
        }

        public Double getPower() {
            return power;
        }

        public void setPower(Double power) {
            this.power = power;
        }

        public Double getBackCurve() {
            return backCurve;
        }

        public void setBackCurve(Double backCurve) {
            this.backCurve = backCurve;
        }

        public Integer getDiameter() {
            return diameter;
        }

        public void setDiameter(Integer diameter) {
            this.diameter = diameter;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
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
}

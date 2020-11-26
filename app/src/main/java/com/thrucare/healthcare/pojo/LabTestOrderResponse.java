
package com.thrucare.healthcare.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class LabTestOrderResponse {

    @SerializedName("items")
    private List<Item> mItems;
    @SerializedName("links")
    private Links mLinks;
    @SerializedName("metadata")
    private Metadata mMetadata;

    public List<Item> getItems() {
        return mItems;
    }

    public void setItems(List<Item> items) {
        mItems = items;
    }

    public Links getLinks() {
        return mLinks;
    }

    public void setLinks(Links links) {
        mLinks = links;
    }

    public Metadata getMetadata() {
        return mMetadata;
    }

    public void setMetadata(Metadata metadata) {
        mMetadata = metadata;
    }


    public class Item {

        @SerializedName("category")
        private String mCategory;
        @SerializedName("created_date")
        private String mCreatedDate;
        @SerializedName("procedure")
        private Procedure mProcedure;
        @SerializedName("provider")
        private Provider mProvider;
        @SerializedName("status")
        private Status mStatus;
        @SerializedName("uuid")
        private String mUuid;

        public String getCategory() {
            return mCategory;
        }

        public void setCategory(String category) {
            mCategory = category;
        }

        public String getCreatedDate() {
            return mCreatedDate;
        }

        public void setCreatedDate(String createdDate) {
            mCreatedDate = createdDate;
        }

        public Procedure getProcedure() {
            return mProcedure;
        }

        public void setProcedure(Procedure procedure) {
            mProcedure = procedure;
        }

        public Provider getProvider() {
            return mProvider;
        }

        public void setProvider(Provider provider) {
            mProvider = provider;
        }

        public Status getStatus() {
            return mStatus;
        }

        public void setStatus(Status status) {
            mStatus = status;
        }

        public String getUuid() {
            return mUuid;
        }

        public void setUuid(String uuid) {
            mUuid = uuid;
        }

    }

    public class Links {

        @SerializedName("first")
        private String mFirst;
        @SerializedName("last")
        private String mLast;
        @SerializedName("next")
        private String mNext;
        @SerializedName("prev")
        private String mPrev;

        public String getFirst() {
            return mFirst;
        }

        public void setFirst(String first) {
            mFirst = first;
        }

        public String getLast() {
            return mLast;
        }

        public void setLast(String last) {
            mLast = last;
        }

        public String getNext() {
            return mNext;
        }

        public void setNext(String next) {
            mNext = next;
        }

        public String getPrev() {
            return mPrev;
        }

        public void setPrev(String prev) {
            mPrev = prev;
        }

    }

    public class Metadata {

        @SerializedName("count")
        private Long mCount;
        @SerializedName("limit")
        private Long mLimit;
        @SerializedName("number")
        private Long mNumber;
        @SerializedName("offset")
        private Long mOffset;

        public Long getCount() {
            return mCount;
        }

        public void setCount(Long count) {
            mCount = count;
        }

        public Long getLimit() {
            return mLimit;
        }

        public void setLimit(Long limit) {
            mLimit = limit;
        }

        public Long getNumber() {
            return mNumber;
        }

        public void setNumber(Long number) {
            mNumber = number;
        }

        public Long getOffset() {
            return mOffset;
        }

        public void setOffset(Long offset) {
            mOffset = offset;
        }

    }

    public class Procedure {

        @SerializedName("code")
        private String mCode;
        @SerializedName("display")
        private String mDisplay;

        public String getCode() {
            return mCode;
        }

        public void setCode(String code) {
            mCode = code;
        }

        public String getDisplay() {
            return mDisplay;
        }

        public void setDisplay(String display) {
            mDisplay = display;
        }

    }

    public class Provider {

        @SerializedName("first_name")
        private String mFirstName;
        @SerializedName("last_name")
        private String mLastName;
        @SerializedName("middle_name")
        private String mMiddleName;
        @SerializedName("uuid")
        private String mUuid;

        public String getFirstName() {
            return mFirstName;
        }

        public void setFirstName(String firstName) {
            mFirstName = firstName;
        }

        public String getLastName() {
            return mLastName;
        }

        public void setLastName(String lastName) {
            mLastName = lastName;
        }

        public String getMiddleName() {
            return mMiddleName;
        }

        public void setMiddleName(String middleName) {
            mMiddleName = middleName;
        }

        public String getUuid() {
            return mUuid;
        }

        public void setUuid(String uuid) {
            mUuid = uuid;
        }

    }

    public class Status {

        @SerializedName("code")
        private String mCode;
        @SerializedName("display")
        private String mDisplay;

        public String getCode() {
            return mCode;
        }

        public void setCode(String code) {
            mCode = code;
        }

        public String getDisplay() {
            return mDisplay;
        }

        public void setDisplay(String display) {
            mDisplay = display;
        }

    }


}

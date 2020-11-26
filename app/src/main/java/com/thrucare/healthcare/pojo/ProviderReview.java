
package com.thrucare.healthcare.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProviderReview {

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

        @SerializedName("comment")
        private String mComment;
        @SerializedName("patient")
        private Patient mPatient;
        @SerializedName("rating")
        private Long mRating;
        @SerializedName("review_date")
        private String mReviewDate;
        @SerializedName("uuid")
        private String mUuid;

        public String getComment() {
            return mComment;
        }

        public void setComment(String comment) {
            mComment = comment;
        }

        public Patient getPatient() {
            return mPatient;
        }

        public void setPatient(Patient patient) {
            mPatient = patient;
        }

        public Long getRating() {
            return mRating;
        }

        public void setRating(Long rating) {
            mRating = rating;
        }

        public String getReviewDate() {
            return mReviewDate;
        }

        public void setReviewDate(String reviewDate) {
            mReviewDate = reviewDate;
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

    public class Patient {

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

}

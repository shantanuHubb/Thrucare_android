
package com.thrucare.healthcare.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {

    @SerializedName("address")
    private Address mAddress;
    @SerializedName("birth_date")
    private String mBirthDate;
    @SerializedName("blood_group")
    private BloodGroup mBloodGroup;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("gender")
    private Gender mGender;
    @SerializedName("identifier")
    private List<Identifier> mIdentifier;
    @SerializedName("insurances")
    private Insurances mInsurances;
    @SerializedName("last_name")
    private String mLastName;
    @SerializedName("middle_name")
    private String mMiddleName;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("photo")
    private String mPhoto;
    @SerializedName("uuid")
    private String mUuid;

    public Address getAddress() {
        return mAddress;
    }

    public void setAddress(Address address) {
        mAddress = address;
    }

    public String getBirthDate() {
        return mBirthDate;
    }

    public void setBirthDate(String birthDate) {
        mBirthDate = birthDate;
    }

    public BloodGroup getBloodGroup() {
        return mBloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        mBloodGroup = bloodGroup;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public Gender getGender() {
        return mGender;
    }

    public void setGender(Gender gender) {
        mGender = gender;
    }

    public List<Identifier> getIdentifier() {
        return mIdentifier;
    }

    public void setIdentifier(List<Identifier> identifier) {
        mIdentifier = identifier;
    }

    public Insurances getInsurances() {
        return mInsurances;
    }

    public void setInsurances(Insurances insurances) {
        mInsurances = insurances;
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

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public String getUuid() {
        return mUuid;
    }

    public void setUuid(String uuid) {
        mUuid = uuid;
    }


    public class Address {

        @SerializedName("city")
        private String mCity;
        @SerializedName("country")
        private String mCountry;
        @SerializedName("line1")
        private String mLine1;
        @SerializedName("line2")
        private String mLine2;
        @SerializedName("state")
        private String mState;

        public String getCity() {
            return mCity;
        }

        public void setCity(String city) {
            mCity = city;
        }

        public String getCountry() {
            return mCountry;
        }

        public void setCountry(String country) {
            mCountry = country;
        }

        public String getLine1() {
            return mLine1;
        }

        public void setLine1(String line1) {
            mLine1 = line1;
        }

        public String getLine2() {
            return mLine2;
        }

        public void setLine2(String line2) {
            mLine2 = line2;
        }

        public String getState() {
            return mState;
        }

        public void setState(String state) {
            mState = state;
        }

    }

    public class BloodGroup {

        @SerializedName("code")
        private Long mCode;
        @SerializedName("display")
        private String mDisplay;

        public Long getCode() {
            return mCode;
        }

        public void setCode(Long code) {
            mCode = code;
        }

        public String getDisplay() {
            return mDisplay;
        }

        public void setDisplay(String display) {
            mDisplay = display;
        }

    }

    public class Gender {

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

    public class Identifier {

        @SerializedName("code")
        private String mCode;
        @SerializedName("value")
        private Long mValue;

        public String getCode() {
            return mCode;
        }

        public void setCode(String code) {
            mCode = code;
        }

        public Long getValue() {
            return mValue;
        }

        public void setValue(Long value) {
            mValue = value;
        }

    }

    public class Insurances {

        @SerializedName("items")
        private List<Item> mItems;

        public List<Item> getItems() {
            return mItems;
        }

        public void setItems(List<Item> items) {
            mItems = items;
        }

    }

    public class Item {

        @SerializedName("payer")
        private Payer mPayer;
        @SerializedName("uuid")
        private String mUuid;

        public Payer getPayer() {
            return mPayer;
        }

        public void setPayer(Payer payer) {
            mPayer = payer;
        }

        public String getUuid() {
            return mUuid;
        }

        public void setUuid(String uuid) {
            mUuid = uuid;
        }

    }

    public class Payer {

        @SerializedName("code")
        private String mCode;
        @SerializedName("display")
        private String mDisplay;
        @SerializedName("product")
        private List<Product> mProduct;

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

        public List<Product> getProduct() {
            return mProduct;
        }

        public void setProduct(List<Product> product) {
            mProduct = product;
        }

    }

    public class Product {

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

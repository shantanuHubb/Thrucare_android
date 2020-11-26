
package com.thrucare.healthcare.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UpdateProfileResponse {

    @SerializedName("birth_date")
    private String mBirthDate;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("gender")
    private Gender mGender;
    @SerializedName("identifier")
    private List<Identifier> mIdentifier;
    @SerializedName("last_name")
    private String mLastName;
    @SerializedName("middle_name")
    private String mMiddleName;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("photo")
    private String mPhoto;
    @SerializedName("signature")
    private String mSignature;
    @SerializedName("uuid")
    private String mUuid;

    public String getBirthDate() {
        return mBirthDate;
    }

    public void setBirthDate(String birthDate) {
        mBirthDate = birthDate;
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

    public String getSignature() {
        return mSignature;
    }

    public void setSignature(String signature) {
        mSignature = signature;
    }

    public String getUuid() {
        return mUuid;
    }

    public void setUuid(String uuid) {
        mUuid = uuid;
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

        @SerializedName("type")
        private String mType;
        @SerializedName("value")
        private Long mValue;

        public String getType() {
            return mType;
        }

        public void setType(String type) {
            mType = type;
        }

        public Long getValue() {
            return mValue;
        }

        public void setValue(Long value) {
            mValue = value;
        }

    }

}

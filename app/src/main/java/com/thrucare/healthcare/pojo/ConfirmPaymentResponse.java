
package com.thrucare.healthcare.pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class ConfirmPaymentResponse {

    @SerializedName("amount")
    private Long mAmount;
    @SerializedName("end")
    private String mEnd;
    @SerializedName("location")
    private Location mLocation;
    @SerializedName("patient")
    private Patient mPatient;
    @SerializedName("patient_name")
    private String mPatientName;
    @SerializedName("provider")
    private Provider mProvider;
    @SerializedName("service_type")
    private List<ServiceType> mServiceType;
    @SerializedName("speciality")
    private List<Speciality> mSpeciality;
    @SerializedName("start")
    private String mStart;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("uuid")
    private String mUuid;

    public Long getAmount() {
        return mAmount;
    }

    public void setAmount(Long amount) {
        mAmount = amount;
    }

    public String getEnd() {
        return mEnd;
    }

    public void setEnd(String end) {
        mEnd = end;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public Patient getPatient() {
        return mPatient;
    }

    public void setPatient(Patient patient) {
        mPatient = patient;
    }

    public String getPatientName() {
        return mPatientName;
    }

    public void setPatientName(String patientName) {
        mPatientName = patientName;
    }

    public Provider getProvider() {
        return mProvider;
    }

    public void setProvider(Provider provider) {
        mProvider = provider;
    }

    public List<ServiceType> getServiceType() {
        return mServiceType;
    }

    public void setServiceType(List<ServiceType> serviceType) {
        mServiceType = serviceType;
    }

    public List<Speciality> getSpeciality() {
        return mSpeciality;
    }

    public void setSpeciality(List<Speciality> speciality) {
        mSpeciality = speciality;
    }

    public String getStart() {
        return mStart;
    }

    public void setStart(String start) {
        mStart = start;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
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

    public class Location {

        @SerializedName("address")
        private Address mAddress;
        @SerializedName("name")
        private String mName;
        @SerializedName("uuid")
        private String mUuid;

        public Address getAddress() {
            return mAddress;
        }

        public void setAddress(Address address) {
            mAddress = address;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public String getUuid() {
            return mUuid;
        }

        public void setUuid(String uuid) {
            mUuid = uuid;
        }

    }

    public class Patient {

        @SerializedName("email")
        private String mEmail;
        @SerializedName("first_name")
        private String mFirstName;
        @SerializedName("last_name")
        private String mLastName;
        @SerializedName("middle_name")
        private String mMiddleName;
        @SerializedName("phone")
        private String mPhone;
        @SerializedName("uuid")
        private String mUuid;

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

        public String getUuid() {
            return mUuid;
        }

        public void setUuid(String uuid) {
            mUuid = uuid;
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

    public class ServiceType {

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

    public class Speciality {

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

}

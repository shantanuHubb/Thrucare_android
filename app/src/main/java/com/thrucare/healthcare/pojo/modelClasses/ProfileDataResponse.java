package com.thrucare.healthcare.pojo.modelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileDataResponse {

    public class Gender {

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

    public class Insurance {

        @SerializedName("provider")
        @Expose
        private Provider provider;
        @SerializedName("payer")
        @Expose
        private Payer payer;

        public Provider getProvider() {
            return provider;
        }

        public void setProvider(Provider provider) {
            this.provider = provider;
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


    public class UserProfile {

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
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("gender")
        @Expose
        private Gender gender;
        @SerializedName("birth_date")
        @Expose
        private String birthDate;
        @SerializedName("photo")
        @Expose
        private Object photo;
        @SerializedName("signature")
        @Expose
        private Object signature;
        @SerializedName("qualifications")
        @Expose
        private List<Object> qualifications = null;
        @SerializedName("insurances")
        @Expose
        private List<Insurance> insurances = null;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Gender getGender() {
            return gender;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public Object getPhoto() {
            return photo;
        }

        public void setPhoto(Object photo) {
            this.photo = photo;
        }

        public Object getSignature() {
            return signature;
        }

        public void setSignature(Object signature) {
            this.signature = signature;
        }

        public List<Object> getQualifications() {
            return qualifications;
        }

        public void setQualifications(List<Object> qualifications) {
            this.qualifications = qualifications;
        }

        public List<Insurance> getInsurances() {
            return insurances;
        }

        public void setInsurances(List<Insurance> insurances) {
            this.insurances = insurances;
        }

    }
}

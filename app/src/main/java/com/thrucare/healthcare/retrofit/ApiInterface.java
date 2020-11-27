package com.thrucare.healthcare.retrofit;

import com.google.gson.JsonObject;
import com.thrucare.healthcare.activity.patient.myAppointment.MyAppointmentsModel;
import com.thrucare.healthcare.activity.provider.insurance.InsuranceListActivity;
import com.thrucare.healthcare.pojo.AddInsurance;
import com.thrucare.healthcare.pojo.ConfirmPaymentResponse;
import com.thrucare.healthcare.pojo.DependentsList;
import com.thrucare.healthcare.pojo.DeviceList;
import com.thrucare.healthcare.pojo.EmergencyContactList;
import com.thrucare.healthcare.pojo.ImmunizationList;
import com.thrucare.healthcare.pojo.QualificationList;
import com.thrucare.healthcare.pojo.ResponseQuotation;
import com.thrucare.healthcare.pojo.ServiceAssigment;
import com.thrucare.healthcare.pojo.StaffList;
import com.thrucare.healthcare.pojo.UpdateProfileResponse;
import com.thrucare.healthcare.pojo.ProfileResponse;
import com.thrucare.healthcare.pojo.AddMedication;
import com.thrucare.healthcare.pojo.LabTestOrderResponse;
import com.thrucare.healthcare.pojo.ProviderApointmentConfirm;
import com.thrucare.healthcare.pojo.AvailableSchedule;
import com.thrucare.healthcare.pojo.AvailableSlotTime;
import com.thrucare.healthcare.pojo.BookAppointmentFilter;
import com.thrucare.healthcare.pojo.FamilyHistory;
import com.thrucare.healthcare.pojo.FilterListProviderSearch;
import com.thrucare.healthcare.pojo.FinalResponseSearch;
import com.thrucare.healthcare.pojo.LoginResponse;
import com.thrucare.healthcare.pojo.MedicalReportList;
import com.thrucare.healthcare.pojo.ModelQuestionnaire;
import com.thrucare.healthcare.pojo.OrganizationsType;
import com.thrucare.healthcare.pojo.OrigazationSearch;
import com.thrucare.healthcare.pojo.PatientAllergies;
import com.thrucare.healthcare.pojo.PatientDiagnosis;
import com.thrucare.healthcare.pojo.PatientMedication;
import com.thrucare.healthcare.pojo.PatientOrderType;
import com.thrucare.healthcare.pojo.PatientOrderTypeForOthers;
import com.thrucare.healthcare.pojo.PatientRegistrationResponse;
import com.thrucare.healthcare.pojo.PatientSocialHistory;
import com.thrucare.healthcare.pojo.ProviderReview;
import com.thrucare.healthcare.pojo.ReportCategory;
import com.thrucare.healthcare.pojo.ResponseMedicationStatusAPI;
import com.thrucare.healthcare.pojo.VisionList;
import com.thrucare.healthcare.pojo.modelClasses.PatientAddInsurance;
import com.thrucare.healthcare.pojo.modelClasses.PatientEmergencyContact;
import com.thrucare.healthcare.pojo.modelClasses.PatientEmergencyContactList;
import com.thrucare.healthcare.pojo.modelClasses.PatientInsuranceList;
import com.thrucare.healthcare.pojo.modelClasses.PatientRegister;
import com.thrucare.healthcare.pojo.modelClasses.ProfileDataResponse;
import com.thrucare.healthcare.pojo.modelClasses.ProfilePatientResponse;
import com.thrucare.healthcare.pojo.modelClasses.ProviderAddInsurance;
import com.thrucare.healthcare.pojo.modelClasses.ProviderInsuranceList;
import com.thrucare.healthcare.pojo.modelClasses.ProviderQualificationList;
import com.thrucare.healthcare.pojo.modelClasses.ProviderRegister;
import com.thrucare.healthcare.pojo.modelClasses.UpdateResponseProfile;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {


    @GET("providers/1/appointments/2")
    Call<ProviderApointmentConfirm> getAppointmentDetailsForConformation();

    @POST("/patients/login")
    Call<LoginResponse> signIn(@Header ("Content-Type" ) String value, @Body JsonObject jsonObject);


    @POST("/patients")
    Call<PatientRegistrationResponse> signUp(@Header ("Content-Type" ) String value, @Body JsonObject jsonObject);

    @GET("/patients/1/conditions")
    Call<PatientDiagnosis.PatientDiagnosisResponse> patientDiagnosis();

    @GET("/patients/1/medications")
    Call<PatientMedication.PatientMedicationRes> patientMedications();

    @GET("/patients/1/allergies")
    Call<PatientAllergies.PatientAllergiesResponse> patientAllergies();

    @GET("/patients/1/family-history")
    Call<FamilyHistory.PatientFamilyHistory> patientFamilyHistory();


    @DELETE("/patients/1/family-history/2")
    Call<ResponseBody> deleteFamilyHistory();


    @POST("/patients/uuid/family-history")
    Call<FamilyHistory.PatientFamilyHistory> addFamilyHistory(@Body JsonObject obj);

    @POST("/patients/1/social-history")
    Call<PatientSocialHistory> getSocialHistory();

    @PUT("/patients/1/social-history/123e4567-e89b-12d3-a456-426614174000")
    Call<ResponseBody> updateSocialHistory(@Body JsonObject obj);

    @POST("/patients/1/social-history/123e4567-e89b-12d3-a456-426614174000")
    Call<ResponseBody> saveSocialHistory(@Body JsonObject obj);

    @GET("/reference/search?type=condition-severity")
    Call<FinalResponseSearch.ResposeSearch> getSeverity();

    @GET("/reference/search?type=condition-clinical")
    Call<FinalResponseSearch.ResposeSearch> getStatus();


    @POST("/patients/uuid/conditions")
    Call<PatientDiagnosis.PatientDiagnosisResponse> saveAddDiagnosis(@Header("Content-Type") String contentType,
                                                                     @Body JsonObject jsonObject);


    @GET("/reference/search?type=medication-status")
    Call<ResponseMedicationStatusAPI.ResponseMedicationStatus> getMedeicationStatus();

    @GET("/reference/search?type=medication-reason&terms")
    Call<ResponseMedicationStatusAPI.ResponseMedicationStatus> getReasons();



    @POST("/patients/uuid/medications")
    Call<PatientMedication.PatientMedicationRes> saveAddMedications(@Header("Content-Type") String contentType,
                                                                     @Body JsonObject jsonObject);


    @GET("/reference/search?type=allergy-status")
    Call<FinalResponseSearch.ResposeSearch> getStatusAllergies();

    @GET("/reference/search?type=allergy-criticality")
    Call<FinalResponseSearch.ResposeSearch> getAllergiesCriticality();



    @GET("/reference/search?type=allergy-reaction&terms=")
    Call<FinalResponseSearch.ResposeSearch> getAllergiesReactions(@Url String url);

    @GET("/patients/uuid/appointments")
    Call<MyAppointmentsModel.ResponseMyappointment> getMyAppointmentsOfPatients();


    @GET("/providers/1/appointments?")
    Call<MyAppointmentsModel.ResponseMyappointment> getMyAppointmentsOfProvider( @Query("start") String start,
                                                                                  @Query("end") String end);


    @GET("/providers/search")
    Call<FilterListProviderSearch.ResponseFilterSearch> getProviderSearchFilter();




    @GET("/organizations/types")
    Call<OrganizationsType.ResponseOrganizationType> getOrganizationsTypes();

    @GET("organizations/search")
    Call<OrigazationSearch.ResponseOrganizationSearch> getOrganizationsSearch();



    @GET("/providers/123e4567-e89b-12d3-a456-426614174000/service-assignments")
    Call<BookAppointmentFilter.ResponseBookAppointment> getBookAppointmentFilter();


    @GET("/providers/1/schedules?start=2020-09-01&end=2020-09-31&location=uuid&service_type=code&speciality=code")
    Call<AvailableSchedule.ResponseScheduleAvailable> getAvailableAppointment();




    @GET("/providers/1/schedules/1/slots?start=2020-07-06&end=2020-07-06")
    Call<AvailableSlotTime.ResponseSlotTime> getTimeSlotOfAvailableDate();


    @GET("/providers/uuid/appointments")
    Call<ResponseBody> saveBookAppointment();



    @PUT("/appointments/auuid")
    Call<ResponseBody> rescheduleBooking();

    @GET("/patients/1/questionnaire/1")
    Call<ModelQuestionnaire.ResponseQuestion> getQuestionnaire();




    @POST("/patients/1/questionnaire/1/response")
    Call<ResponseBody> saveIndividualQuestion(@Header("Content-Type") String contentType,
                                              @Body JsonObject jsonObject);

    @GET
    Call<PatientOrderType.ResponseOrderType> getPatientOrderType(@Url String url);


    @GET
    Call<PatientOrderTypeForOthers.ResponseOrderTypeOthers> getPatientOrderForOtherType(@Url String url);

    @GET("/patients/1/reports")
    Call<MedicalReportList.ResponseMedicalReport> getMedicalReport();


    @GET("/reference/search?type=diagnostic-report-category")
    Call<ReportCategory.ResponseReportCategory> getReportCategory();


    @GET("/reference/search?type=")
    Call<ReportCategory.ResponseReportCategory> getConclusion(@Url String url);



    @GET("/reference/search?type=medication-status")
    Call<ReportCategory.ResponseReportCategory> getMedicationReportStatus();

    @POST("/patients/123e4567-e89b-12d3-a456-426614174000/medication-orders")
    Call<AddMedication> addMedication(@Header("Content-Type") String value, @Body JsonObject jsonObject);

    @GET("/providers/1/reviews")
    Call<ProviderReview> getProviderReview();

    @POST("/patients/123e4567-e89b-12d3-a456-426614174000/lab-orders")
    Call<LabTestOrderResponse> addLabTestOrders(@Header("Content-Type") String value, @Body JsonObject jsonObject);
    @POST("/patients/123e4567-e89b-12d3-a456-426614174000/referral-orders")
    Call<LabTestOrderResponse> addReferralOrders(@Header("Content-Type") String value, @Body JsonObject jsonObject);

    @POST("/patients/123e4567-e89b-12d3-a456-426614174000/radiology-orders")
    Call<LabTestOrderResponse> addRadiologyOrders(@Header("Content-Type") String value, @Body JsonObject jsonObject);


    @GET("/patients/1/profile")
    Call<ProfileResponse> getPatientProfileData();

    @GET("/providers/1/profile")
    Call<ProfileResponse> getProviderProfileData();

    @PUT("/providers/uuid")
    Call<UpdateProfileResponse> updateProfile(@Header("Content-Type") String value, @Body JsonObject jsonObject);

    @GET("/patients/1/appointments/2/invoice")
    Call<ConfirmPaymentResponse>   getPaymentData();

    //demo qualification is commented

//    @GET("/providers/1/qualifications")
//    Call<QualificationList.ResponseListQualification>   getQualificationListData();

    @GET("/reference/search?type=provider-profile-qualification-type")
    Call<ReportCategory.ResponseReportCategory> getQualificationType();


//    @POST("/providers/1/qualifications")
//    Call<QualificationList.ResponseListQualification>   saveNewQualification(@Header("Content-Type") String contentType,
//                                                                                   @Body JsonObject jsonObject );






    @GET("/reference/search?type=insurance-product")
    Call<ReportCategory.ResponseReportCategory> getProductListingInsurance();





    @GET("/reference/search?type=insurance-payer")
    Call<ReportCategory.ResponseReportCategory> getProductInsurancePayer();


//save new insurance demo api

//    @POST("/providers/uuid/insurances")
//    Call<AddInsurance.ResponseAddInsurance>   saveNewInsurance(@Header("Content-Type") String contentType,
//                                          @Body JsonObject jsonObject );


    @GET("/patients/1/dependents")
    Call<DependentsList.ResponseListDependents> getListOfPatientDependents();


    @GET("/patients/1/device-orders")
    Call<DeviceList.ResponseListDeviceOrder> getDeviceListOrder();




    @POST("/patients/uuid/device-orders")
    Call<ResponseBody> saveAddNewDeviceOrder(@Header("Content-Type") String contentType ,
                                                                   @Body JsonObject jsonObjectBody);



    @GET("/patients/uuid/vision-orders")
    Call<VisionList.ResponseListVision> getVisionList();





    @POST("/patients/uuid/vision-orders")
    Call<ResponseBody> saveVisionData(@Header("Content-Type") String contentType ,
                                                       @Body JsonObject jsonObject);




    @GET("/patients/1/immunizations")
    Call<ImmunizationList.ResponseListImmunization> getImmunizationList();






    @GET("/reference/search?type=immunization-origin")
    Call<FinalResponseSearch.ResposeSearch> getOriginImmunization();


    @GET("/reference/search?type=immunization-status")
    Call<FinalResponseSearch.ResposeSearch> getStatusImmunization();


    @POST("/patients/1/immunizations")
    Call<ResponseBody> saveImmunization(@Header("Content-Type") String contentType ,
                                      @Body JsonObject jsonObject);




    @GET("/patients/uuid/contacts")
    Call<EmergencyContactList.ResponseEmergencyContact> getEmergencyContact();

    @POST("/patients/uuid/contacts")
    Call<ResponseBody> saveEmergencyContact(@Header("Content-Type") String contentType ,
                                            @Body JsonObject jsonObject);




    @GET("/providers/1/team-members")
    Call<StaffList.ResponseStaffList> getStaffList();



    @GET("/providers/1/service-assignments")
    Call<ServiceAssigment.ResponseServiceAssiegment> getServiceAssignments();


    @DELETE("/appointments/1")
    Call<ResponseBody> cancelAppointment();


    @GET("/orders/1/quotes")
    Call<ResponseQuotation.ResponseQuotationList> getQuotationList();

    //Starting with the Real API

    //Provider API CALLING

    @POST("https://a20rhqyk4c.execute-api.us-east-1.amazonaws.com/develop/providers/v1")
    Call<ProviderRegister> createUser(@Header ("Content-Type" ) String value, @Body JsonObject jsonObject , @Header("x-api-key") String keyValue);

    //qualification list provider
    @GET("{puuid}/qualifications")
    Call<ProviderQualificationList.ProviderRegister>
                              getQualificationList(@Header("Content-Type") String contentType ,@Path("puuid") String puuid
                                , @Header("x-api-key") String keyValue);

    @POST("{puuid}/qualifications")
    Call<ProviderQualificationList.ProviderRegister>   saveNewQualification(@Header("Content-Type") String contentType,
                                                                             @Body JsonObject jsonObject , @Header("x-api-key") String keyValue ,
                                                                            @Path("puuid") String puuid);
    //Add Insurance Real Api provider

    @POST("{puuid}/insurances")
    Call<ProviderAddInsurance.AddInsuranceRes>   saveNewInsurance(@Header("Content-Type") String contentType,
                                                  @Body JsonObject jsonObject  , @Path("puuid") String puuid  , @Header("x-api-key") String keyValue);

    @GET("{puuid}/insurances")
    Call<ProviderInsuranceList.ListInsurance> getInsuranceList(@Header("Content-Type") String contentType,@Path("puuid") String puuid,
                                                               @Header("x-api-key") String keyValue );
    //provider profile data
    @GET("{puuid}")
    Call<ProfileDataResponse.UserProfile> getProviderProfile(@Header("Content-Type") String contentType,@Path("puuid") String puuid,
                                                                 @Header("x-api-key") String keyValue );
    //profile update data provider
    @PUT("{puuid}")
    Call<UpdateResponseProfile> updateProfileDataProvider(@Header("Content-Type") String value, @Body JsonObject jsonObject, @Path("puuid") String puuid,
                                                          @Header("x-api-key") String keyValue);

    //PATIENT API CALLING

    //create patient User
    @POST("https://a20rhqyk4c.execute-api.us-east-1.amazonaws.com/develop/patients/v1")
    Call<PatientRegister> createUserPatient(@Header ("Content-Type" ) String value, @Body JsonObject jsonObject , @Header("x-api-key") String keyValue);

    //Patient profile data
    @GET("{puuid}")
    Call<ProfilePatientResponse> getPatientProfile(@Header("Content-Type") String contentType, @Path("puuid") String puuid,
                                                   @Header("x-api-key") String keyValue );
    //profile update data provider
    @PUT("{puuid}")
    Call<ProfilePatientResponse> updateProfileDataPatient(@Header("Content-Type") String value, @Body JsonObject jsonObject, @Path("puuid") String puuid,
                                                          @Header("x-api-key") String keyValue);


    //patient insurance add data
    @POST("{puuid}/insurances")
    Call<PatientAddInsurance>   saveNewInsurancePatient(@Header("Content-Type") String contentType,
                                                        @Body JsonObject jsonObject  , @Path("puuid") String puuid  , @Header("x-api-key") String keyValue);
    //patient insurance list
    @GET("{puuid}/insurances")
    Call<PatientInsuranceList> getInsuranceListPatient(@Path("puuid") String puuid,
                                                @Header("x-api-key") String keyValue );

    //Patient Add Emergency contact
    @POST("{puuid}/contacts")
    Call<PatientEmergencyContact> savePatientEmergencyContact(@Header("Content-Type") String contentType,
                                                              @Body JsonObject jsonObject  , @Path("puuid") String puuid  , @Header("x-api-key") String keyValue);

    //Patient Emergency Contact List
    @GET("{puuid}/contacts")
    Call<PatientEmergencyContactList> getPatientEmergencyContact(@Path("puuid") String puuid  ,
                                                                 @Header("x-api-key") String keyValue);
 }

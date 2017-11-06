package com.abyz.infotech.warroomapp.retofit;


import com.abyz.infotech.warroomapp.retofit.wsmodel.ChangeMobileNoResponse;
import com.abyz.infotech.warroomapp.retofit.wsmodel.ChangePasswordResponse;
import com.abyz.infotech.warroomapp.retofit.wsmodel.EventResponse;
import com.abyz.infotech.warroomapp.retofit.wsmodel.EventTaskAddResponse;
import com.abyz.infotech.warroomapp.retofit.wsmodel.EventTaskAddsResponse;
import com.abyz.infotech.warroomapp.retofit.wsmodel.EventTaskAllotmentResponse;
import com.abyz.infotech.warroomapp.retofit.wsmodel.FeedbackResponse;
import com.abyz.infotech.warroomapp.retofit.wsmodel.ForgotPasswordWebserviceResponse;
import com.abyz.infotech.warroomapp.retofit.wsmodel.LoginWebserviceResponse;
import com.abyz.infotech.warroomapp.retofit.wsmodel.StatusWebserviceResponse;
import com.abyz.infotech.warroomapp.retofit.wsmodel.TaskResponse;
import com.abyz.infotech.warroomapp.retofit.wsmodel.TaskStatusResponse;
import com.abyz.infotech.warroomapp.retofit.wsmodel.VolunteerListResponse;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by Android on 04/04/2016.
 */
public interface IRetrofitWebservice {

    @POST("/checklogin")
    LoginWebserviceResponse checkLogin(@Body Map<String, String> body);

    @POST("/forgetpassword")
    ForgotPasswordWebserviceResponse forgotPassword(@Body Map<String, String> body);

    @POST("/changepassword")
    ChangePasswordResponse changePassword(@Body Map<String, String> body);

    @POST("/changemobilenumbers")
    ChangeMobileNoResponse changeMobileNo(@Body Map<String, String> body);

    @POST("/EventSpeaker")
    EventResponse eventSpeaker(@Body Map<String, String> body);

    @POST("/AddTaskAllotment")
    TaskResponse taskSpeaker(@Body Map<String, String> body);

    @POST("/AddTaskAllot")
    EventTaskAllotmentResponse addTaskAllot(@Body Map<String, String> body);

    @POST("/AddTaskstatus")
    TaskStatusResponse AddTaskstatus(@Body Map<String, String> body);

    @POST("/EventFeedback")
    FeedbackResponse feedback(@Body Map<String, String> body);

    @POST("/EventTaskList")
    EventTaskAddResponse eventTaskList(@Body Map<String, String> body);

    @POST("/AddEventTask")
    EventTaskAddsResponse addEventTask(@Body Map<String, String> body);


    @POST("/Volunteerlist")
    void volunteerlist(@Body Map<String, String> body, Callback<VolunteerListResponse> callback);

    @Multipart
    @POST("/upload.php")
    void uploadFile(@Part("file") TypedFile file, Callback<StatusWebserviceResponse> callback);


    // @POST("/EventSpeaker")
   // void eventSpeaker(@Body Map<String, String> body, Callback<TableCategory> callback);


}

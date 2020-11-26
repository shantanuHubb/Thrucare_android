package com.thrucare.healthcare.awsChime

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.services.chime.sdk.meetings.session.CreateAttendeeResponse
import com.amazonaws.services.chime.sdk.meetings.session.CreateMeetingResponse
import com.amazonaws.services.chime.sdk.meetings.session.DefaultMeetingSession
import com.amazonaws.services.chime.sdk.meetings.session.MeetingSessionConfiguration
import com.amazonaws.services.chime.sdk.meetings.utils.logger.ConsoleLogger
import com.google.gson.Gson
import com.thrucare.healthcare.databinding.ActivityBookAppointmentBinding
import com.thrucare.healthcare.databinding.ActivityVideoBinding
import com.thrucare.healthcare.pojo.JoinMeetingResponse
import com.thrucare.healthcare.retrofit.KotlinApiInterface
import com.thrucare.healthcare.utils.BaseUtils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VideoActivity : AppCompatActivity() {

    private var binding: ActivityVideoBinding? = null
    val attendeeName = java.net.URLEncoder.encode("Naman", "utf-8");
    val region = java.net.URLEncoder.encode("us-east-1", "utf-8");
    val title = java.net.URLEncoder.encode("112233", "utf-8");
    val url = "${"Prod/"}join?title=$title&name=$attendeeName&region=$region";


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        val view: View = binding!!.getRoot()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("")
        setContentView(view)

       apiCallingForStartVideoCon();

    }

    private fun apiCallingForStartVideoCon() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://y8j0ukbejk.execute-api.us-east-1.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(KotlinApiInterface::class.java)
        val call = service.getData("Prod")

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("responseObj" , response.toString());
                if (response.isSuccessful) {
                    // Deserialize the response to object.
                    val joinMeetingResponse = Gson().fromJson(
                            response.toString(),
                            JoinMeetingResponse::class.java
                    )

                         // Construct configuration using the meeting response.
                    val configuration = MeetingSessionConfiguration(
                            CreateMeetingResponse(joinMeetingResponse.joinInfo.meetingResponse.meeting),
                            CreateAttendeeResponse(joinMeetingResponse.joinInfo.attendeeResponse.attendee)
                    )

                         // Create a default meeting seesion.
                    val meetingSession = DefaultMeetingSession(configuration, ConsoleLogger(), applicationContext)
                    val audioVideo = meetingSession.audioVideo

                  // Start audio and video clients.
                    audioVideo.start()
                }else{

                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }


}
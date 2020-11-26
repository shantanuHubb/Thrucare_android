package com.thrucare.healthcare.pojo

import com.amazonaws.services.chime.sdk.meetings.session.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

// Data stucture that maps to the HTTP response.
data class JoinMeetingResponse(
        @SerializedName("JoinInfo") val joinInfo: MeetingInfo)

data class MeetingInfo(
        @SerializedName("Meeting") val meetingResponse: MeetingResponse,
        @SerializedName("Attendee") val attendeeResponse: AttendeeResponse)

data class MeetingResponse(
        @SerializedName("Meeting") val meeting: Meeting)

data class AttendeeResponse(
        @SerializedName("Attendee") val attendee: Attendee)


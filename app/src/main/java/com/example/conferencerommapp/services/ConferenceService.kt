package com.example.conferencerommapp.services

import androidx.core.content.res.FontResourcesParserCompat
import com.example.conferencerommapp.Model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ConferenceService  {

    @GET("api/building")
    fun getBuildingList() : Call<List<Building>>

    @POST("api/availablerooms")
    //fun getConferenceRoomList(@Path("id")id: Int) : Call<List<ConferenceRoom>>
    fun getConferenceRoomList(@Body availableRoom: FetchConferenceRoom) : Call<List<ConferenceRoom>>

    @GET("api/UserLogin")
    fun getRequestCode(@Query( "email") email : String?) : Call<Int>


    @GET("api/UserDashboard")
    fun getDashboard(@Query( "email") email : String?) : Call<List<Dashboard>>

    @POST("api/UserLogin")
    fun addEmployee(@Body newEmoployee: Employee) : Call<Int>

    @POST("api/BookMeeting")
    fun addBookingDetails(@Body booking: Booking) : Call<Int>

    //@GET("destination")
    //fun getDestinationList(@Query( "country") country : String?, @Query("counnt") count: Int) : Call<List<Destination>>

    /*@GET("destination")
    fun getDestinationList(@QueryMap filter: HashMap<String, String>) : Call<List<Destination>>

    @GET("destination/{id}")
    fun getDestination(@Path("id") id: Int) : Call<Destination>

    @POST("destination")
    fun addDestination(@Body newDestination: Destination) : Call<Destination>

    @FormUrlEncoded
    @PUT("destination/{id}")
    fun updateDestination(
        @Path("id") id: Int,
        @Field("city") city: String,
        @Field("description") desc: String,
        @Field("country") country: String
    ) : Call<Destination>

    @DELETE("destination/{id}")
    fun deleteDestination(@Path("id") id: Int) : Call<Unit>
*/
}
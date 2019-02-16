package com.example.conferencerommapp.services

import androidx.core.content.res.FontResourcesParserCompat
import com.example.conferencerommapp.Model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ConferenceService  {

    @GET("api/building")
    fun getBuildingList() : Call<List<Building>>

    @GET("api/Values")
    //fun getConferenceRoomList(@Path("id")id: Int) : Call<List<ConferenceRoom>>
    fun getConferenceRoomList(@Body availableRoom: FetchConferenceRoom) : Call<List<ConferenceRoom>>

    @GET("values")
    fun getRequestCode(@Query( "email") email : String?) : Call<Int>


    @POST("Values")
    fun addEmployee(@Body newEmoployee: Employee) : Call<Int>

    @POST("values")
    fun addBookingDetails(@Body Booking: Booking) : Call<Int>

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
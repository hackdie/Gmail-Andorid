package com.skalala.api.response

import com.skalala.model.Email
import retrofit2.Call
import retrofit2.http.GET


/**
 * Created by diegogalindo on 3/9/17.
 *
 */
interface ApiInterface {
    @GET("jsonEcho")
    fun getInbox(): Call<List<Email>>
}
package com.skalala.module.home

import android.content.Context
import com.skalala.api.response.ApiInterface
import com.skalala.gmail.GmailApplication
import com.skalala.model.Email
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import android.content.res.TypedArray
import android.graphics.Color


/**
 * Created by diegogalindo on 3/9/17.
 * /
 */

class HomePresenter(val view: IHome.View,val context: Context) : IHome.Presenter {

    @Inject
    lateinit var retrofit: Retrofit

    init {
        GmailApplication.apiComponent.inject(this)
    }

    override fun fetchData() {

        val apiInterface = retrofit.create(ApiInterface::class.java).getInbox()

        apiInterface.enqueue(object : Callback<List<Email>> {
            override fun onResponse(call: Call<List<Email>>, response: Response<List<Email>>) {

                for (email in response.body()) {
                    email.color = getRandomMaterialColor("400")
                }

                view.presentEmails(response.body() as MutableList<Email>)
            }

            override fun onFailure(call: Call<List<Email>>, t: Throwable) {
                view.showError("Unable to fetch json: " + t.message)
            }
        })
    }


    private fun getRandomMaterialColor(typeColor: String): Int {
        var returnColor = Color.GRAY
        val arrayId = context.resources.getIdentifier("mdcolor_" + typeColor, "array", context.packageName)

        if (arrayId != 0) {
            val colors = context.resources.obtainTypedArray(arrayId)
            val index = (Math.random() * colors.length()).toInt()
            returnColor = colors.getColor(index, Color.GRAY)
            colors.recycle()
        }
        return returnColor
    }


}
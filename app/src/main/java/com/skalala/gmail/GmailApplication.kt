package com.skalala.gmail

import android.app.Application
import com.skalala.dagger.component.ApiComponent
import com.skalala.dagger.component.DaggerApiComponent
import com.skalala.dagger.component.DaggerPicassoComponent
import com.skalala.dagger.component.PicassoComponent
import com.skalala.dagger.model.ContextModule

/**
 * Created by diegogalindo on 3/9/17.
 * /
 */

class GmailApplication : Application() {

    companion object {
        lateinit var apiComponent: ApiComponent
        lateinit var picassoComponent: PicassoComponent
    }


    override fun onCreate() {
        super.onCreate()

        apiComponent = DaggerApiComponent.builder().build()
        picassoComponent = DaggerPicassoComponent.builder().contextModule(ContextModule(applicationContext)).build()

    }
}
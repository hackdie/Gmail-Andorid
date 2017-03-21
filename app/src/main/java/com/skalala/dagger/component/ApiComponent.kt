package com.skalala.dagger.component

import com.skalala.dagger.model.ContextModule
import com.skalala.dagger.model.RestModule
import com.skalala.module.home.HomePresenter
import dagger.Component
import javax.inject.Singleton

/**
 * Created by diegogalindo on 3/9/17.
 * /
 */

@Singleton
@Component(modules = arrayOf(RestModule::class))
interface ApiComponent {
    fun inject(activity: HomePresenter)
}
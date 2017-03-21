package com.skalala.dagger.component


import com.skalala.adapter.HomeAdapter
import com.skalala.dagger.model.PicassoModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by diegogalindo on 3/18/17.
 */
@Singleton
@Component(modules = arrayOf(PicassoModule::class))
interface PicassoComponent {
    fun inject(homeAdapter: HomeAdapter)
}
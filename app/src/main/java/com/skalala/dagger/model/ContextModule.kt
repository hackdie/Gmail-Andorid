package com.skalala.dagger.model

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by diegogalindo on 3/9/17.
 * /
 */

@Module
class ContextModule(val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

}
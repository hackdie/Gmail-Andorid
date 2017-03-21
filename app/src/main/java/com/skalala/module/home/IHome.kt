package com.skalala.module.home

import com.skalala.model.Email

/**
 * Created by diegogalindo on 3/9/17.
 * /
 */

interface IHome {
    interface View {
        fun presentEmails(emails: MutableList<Email>)
        fun showError(error: String)
    }
    interface Presenter {
        fun fetchData()
    }
}
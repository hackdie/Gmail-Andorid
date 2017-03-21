package com.skalala.model

/**
 * Created by diegogalindo on 3/9/17.
 * /
 */

data class Email(val id: Int, val from: String, val subject: String, val message: String, val timestamp: String, val picture: String, var isImportant: Boolean, var isRead: Boolean, var color: Int = -1)

package com.phuoc.domain.form

import com.google.gson.annotations.SerializedName

data class ContactForm(
    @SerializedName("first_name")
    var firstName: String = "",
    @SerializedName("last_name")
    var lasName: String = "",
    @SerializedName("email")
    var email: String = ""
) : IPageForm
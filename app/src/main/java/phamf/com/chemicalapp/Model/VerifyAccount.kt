package com.schoolsupport.app.dmt91.schoolsupport.model

import phamf.com.chemicalapp.supportClass.isContainNumber
import phamf.com.chemicalapp.supportClass.isEmail
import javax.inject.Inject

class VerifyAccount {

    @Inject
    constructor()

    fun verify (email : String, password : String) : Boolean {
        return (verifyEmail(email) && verifyPassword(password))
    }

    fun verifyPassword (password: String) : Boolean {
        return (password.length >= 8 && password.isContainNumber() && password.isNotEmpty())
    }

    fun verifyEmail (email : String) : Boolean {
        return email.isEmail() && email.isNotEmpty()
    }
}
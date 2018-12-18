package com.schoolsupport.app.dmt91.schoolsupport.model

import com.google.firebase.auth.FirebaseAuth

class RegisterAccount {
    private val mAuth : FirebaseAuth

    constructor() {
        mAuth = FirebaseAuth.getInstance()
    }
}


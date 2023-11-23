package com.example.amazonapp.Model

class Users {
    @JvmName("getPhone1")
    fun getPhone() {

    }

    var name: String = ""
    var email: String = ""
    var phone: String = ""
    var password: String = ""

    constructor() {
        // Empty constructor
    }

    constructor(name: String, phone: String, password: String, email: String) {
        this.name = name
        this.phone = phone
        this.password = password
        this.email = email

    }

}
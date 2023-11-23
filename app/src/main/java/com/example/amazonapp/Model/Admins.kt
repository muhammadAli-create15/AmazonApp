package com.example.amazonapp.Model

class Admins {
    var name: String = ""
    var phone: String = ""
    var password: String = ""

    constructor(){

    }
    constructor(name: String, phone: String, password: String) {
        this.name = name
        this.phone = phone
        this.password = password

    }
}
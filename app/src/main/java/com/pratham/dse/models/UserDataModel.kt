package com.pratham.dse.models

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
class UserDataModel {
    constructor(
        userName: String?,
        userMobile: String?,
        userId: String?,
        userType: String?,
        imagePath: String?
    ) {
        this.userName = userName
        this.userMobile = userMobile
        this.userId = userId
        this.userType = userType
        this.imagePath = imagePath
    }

    var userName: String? = null
    var userMobile: String? = null
    var userId: String? = null
    var userType: String? = null
    var imagePath: String? = null


}
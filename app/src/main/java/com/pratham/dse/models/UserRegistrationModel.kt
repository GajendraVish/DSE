package com.pratham.dse.models

import android.os.Parcel
import android.os.Parcelable
import com.bhaskar.utils.ParcelableInterface

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
class UserRegistrationModel() : ParcelableInterface {


    var userType: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var mobileNo: String? = null
    var alternateMobile: String? = null
    var email: String? = null
    var stateId: String? = null
    var cityId: String? = null
    var address: String? = null
    var pincode: String? = null
    var userImageName: String? = null
    var userImagePath: String? = null
    var aadhaarImageName: String? = null
    var aadhaarImagePath: String? = null
    var aadhaarnumber: String? = null

    constructor(parcel: Parcel) : this() {
        userType = parcel.readString()
        firstName = parcel.readString()
        lastName = parcel.readString()
        mobileNo = parcel.readString()
        alternateMobile = parcel.readString()
        email = parcel.readString()
        stateId = parcel.readString()
        cityId = parcel.readString()
        address = parcel.readString()
        pincode = parcel.readString()
        userImageName = parcel.readString()
        userImagePath = parcel.readString()
        aadhaarImageName = parcel.readString()
        aadhaarImagePath = parcel.readString()
        aadhaarnumber = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userType)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(mobileNo)
        parcel.writeString(alternateMobile)
        parcel.writeString(email)
        parcel.writeString(stateId)
        parcel.writeString(cityId)
        parcel.writeString(address)
        parcel.writeString(pincode)
        parcel.writeString(userImageName)
        parcel.writeString(userImagePath)
        parcel.writeString(aadhaarImageName)
        parcel.writeString(aadhaarImagePath)
        parcel.writeString(aadhaarnumber)
    }

    override fun readFromParcel(parcel: Parcel) {
        userType = parcel.readString()
        firstName = parcel.readString()
        lastName = parcel.readString()
        mobileNo = parcel.readString()
        alternateMobile = parcel.readString()
        email = parcel.readString()
        stateId = parcel.readString()
        cityId = parcel.readString()
        address = parcel.readString()
        pincode = parcel.readString()
        userImageName = parcel.readString()
        userImagePath = parcel.readString()
        aadhaarImageName = parcel.readString()
        aadhaarImagePath = parcel.readString()
        aadhaarnumber = parcel.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserRegistrationModel> {
        override fun createFromParcel(parcel: Parcel): UserRegistrationModel {
            return UserRegistrationModel(parcel)
        }

        override fun newArray(size: Int): Array<UserRegistrationModel?> {
            return arrayOfNulls(size)
        }
    }


}
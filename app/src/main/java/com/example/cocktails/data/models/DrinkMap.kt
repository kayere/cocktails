package com.example.cocktails.data.models

import android.os.Parcel
import android.os.Parcelable

data class DrinkMap(val drinkType: String, val drinkPosition: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(drinkType)
        parcel.writeInt(drinkPosition)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DrinkMap> {
        override fun createFromParcel(parcel: Parcel): DrinkMap {
            return DrinkMap(parcel)
        }

        override fun newArray(size: Int): Array<DrinkMap?> {
            return arrayOfNulls(size)
        }
    }
}
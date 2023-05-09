package com.tta.fitnessapplication.data.model

import android.os.Parcel
import android.os.Parcelable

data class Exercise(
    var id: Int,
    var name: String,
    var image: String,
    var title: String,
    var area: String,
    var type: String,
    var number: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeString(title)
        parcel.writeString(area)
        parcel.writeString(type)
        parcel.writeString(number)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Exercise> {
        override fun createFromParcel(parcel: Parcel): Exercise {
            return Exercise(parcel)
        }

        override fun newArray(size: Int): Array<Exercise?> {
            return arrayOfNulls(size)
        }
    }
}

/*
Nếu type = 0 th number tính bằng giây
ngược lại number là số lần thực hiện
 */



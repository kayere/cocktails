package com.example.cocktails.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favourite_drink")
data class FavouriteDrink(
    @SerializedName("dateModified")
    val dateModified: String?,
    @PrimaryKey
    @SerializedName("idDrink")
    val drinkId: String,
    @SerializedName("strAlcoholic")
    val alcoholic: String?,
    @SerializedName("strCategory")
    val category: String?,
    @SerializedName("strCreativeCommonsConfirmed")
    val creativeCommonsConfirmed: String?,
    @SerializedName("strDrink")
    val drinkName: String?,
    @SerializedName("strDrinkAlternate")
    val drinkNameAlternate: String?,
    @SerializedName("strDrinkDE")
    val drinkNameDE: String?,
    @SerializedName("strDrinkES")
    val drinkNameES: String?,
    @SerializedName("strDrinkFR")
    val drinkNameFR: String?,
    @SerializedName("strDrinkThumb")
    val drinkThumb: String?,
    @SerializedName("strDrinkZH-HANS")
    val strDrinkZHHANS: String?,
    @SerializedName("strDrinkZH-HANT")
    val strDrinkZHHANT: String?,
    @SerializedName("strGlass")
    val glass: String?,
    @SerializedName("strIBA")
    val strIBA: String?,
    @SerializedName("strImageAttribution")
    val imageAttribution: String?,
    @SerializedName("strImageSource")
    val imageSource: String?,
    @SerializedName("strIngredient1")
    val ingredient1: String?,
    @SerializedName("strIngredient2")
    val ingredient2: String?,
    @SerializedName("strIngredient3")
    val ingredient3: String?,
    @SerializedName("strIngredient4")
    val ingredient4: String?,
    @SerializedName("strIngredient5")
    val ingredient5: String?,
    @SerializedName("strIngredient6")
    val ingredient6: String?,
    @SerializedName("strIngredient7")
    val ingredient7: String?,
    @SerializedName("strIngredient8")
    val ingredient8: String?,
    @SerializedName("strIngredient9")
    val ingredient9: String?,
    @SerializedName("strIngredient10")
    val ingredient10: String?,
    @SerializedName("strIngredient11")
    val ingredient11: String?,
    @SerializedName("strIngredient12")
    val ingredient12: String?,
    @SerializedName("strIngredient13")
    val ingredient13: String?,
    @SerializedName("strIngredient14")
    val ingredient14: String?,
    @SerializedName("strIngredient15")
    val ingredient15: String?,
    @SerializedName("strInstructions")
    val instructions: String?,
    @SerializedName("strInstructionsDE")
    val instructionsDE: String?,
    @SerializedName("strInstructionsES")
    val instructionsES: String?,
    @SerializedName("strInstructionsFR")
    val instructionsFR: String?,
    @SerializedName("strInstructionsZH-HANS")
    val instructionsZHHANS: String?,
    @SerializedName("strInstructionsZH-HANT")
    val instructionsZHHANT: String?,
    @SerializedName("strMeasure1")
    val measure1: String?,
    @SerializedName("strMeasure10")
    val measure10: String?,
    @SerializedName("strMeasure11")
    val measure11: String?,
    @SerializedName("strMeasure12")
    val measure12: String?,
    @SerializedName("strMeasure13")
    val measure13: String?,
    @SerializedName("strMeasure14")
    val measure14: String?,
    @SerializedName("strMeasure15")
    val measure15: String?,
    @SerializedName("strMeasure2")
    val measure2: String?,
    @SerializedName("strMeasure3")
    val measure3: String?,
    @SerializedName("strMeasure4")
    val measure4: String?,
    @SerializedName("strMeasure5")
    val measure5: String?,
    @SerializedName("strMeasure6")
    val measure6: String?,
    @SerializedName("strMeasure7")
    val measure7: String?,
    @SerializedName("strMeasure8")
    val measure8: String?,
    @SerializedName("strMeasure9")
    val measure9: String?,
    @SerializedName("strTags")
    val tags: String?,
    @SerializedName("strVideo")
    val video: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(dateModified)
        parcel.writeString(drinkId)
        parcel.writeString(alcoholic)
        parcel.writeString(category)
        parcel.writeString(creativeCommonsConfirmed)
        parcel.writeString(drinkName)
        parcel.writeString(drinkNameAlternate)
        parcel.writeString(drinkNameDE)
        parcel.writeString(drinkNameES)
        parcel.writeString(drinkNameFR)
        parcel.writeString(drinkThumb)
        parcel.writeString(strDrinkZHHANS)
        parcel.writeString(strDrinkZHHANT)
        parcel.writeString(glass)
        parcel.writeString(strIBA)
        parcel.writeString(imageAttribution)
        parcel.writeString(imageSource)
        parcel.writeString(ingredient1)
        parcel.writeString(ingredient2)
        parcel.writeString(ingredient3)
        parcel.writeString(ingredient4)
        parcel.writeString(ingredient5)
        parcel.writeString(ingredient6)
        parcel.writeString(ingredient7)
        parcel.writeString(ingredient8)
        parcel.writeString(ingredient9)
        parcel.writeString(ingredient10)
        parcel.writeString(ingredient11)
        parcel.writeString(ingredient12)
        parcel.writeString(ingredient13)
        parcel.writeString(ingredient14)
        parcel.writeString(ingredient15)
        parcel.writeString(instructions)
        parcel.writeString(instructionsDE)
        parcel.writeString(instructionsES)
        parcel.writeString(instructionsFR)
        parcel.writeString(instructionsZHHANS)
        parcel.writeString(instructionsZHHANT)
        parcel.writeString(measure1)
        parcel.writeString(measure10)
        parcel.writeString(measure11)
        parcel.writeString(measure12)
        parcel.writeString(measure13)
        parcel.writeString(measure14)
        parcel.writeString(measure15)
        parcel.writeString(measure2)
        parcel.writeString(measure3)
        parcel.writeString(measure4)
        parcel.writeString(measure5)
        parcel.writeString(measure6)
        parcel.writeString(measure7)
        parcel.writeString(measure8)
        parcel.writeString(measure9)
        parcel.writeString(tags)
        parcel.writeString(video)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Drink> {
        override fun createFromParcel(parcel: Parcel): Drink {
            return Drink(parcel)
        }

        override fun newArray(size: Int): Array<Drink?> {
            return arrayOfNulls(size)
        }
    }
}
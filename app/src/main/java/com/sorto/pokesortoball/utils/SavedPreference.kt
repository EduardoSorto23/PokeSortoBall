package com.sorto.pokesortoball.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object SavedPreference {

    const val EMAIL= "email"
    const val USERNAME="username"
    const val PHOTO="photo"
    const val REGION="region"

    private  fun getSharedPreference(ctx: Context?): SharedPreferences? {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    private fun  editor(context: Context, const:String, string: String){
        getSharedPreference(
            context
        )?.edit()?.putString(const,string)?.apply()
    }

    fun getEmail(context: Context)= getSharedPreference(
        context
    )?.getString(EMAIL,"")

    fun setEmail(context: Context, email: String){
        editor(
            context,
            EMAIL,
            email
        )
    }

    fun setUsername(context: Context, username:String){
        editor(
            context,
            USERNAME,
            username
        )
    }

    fun getUsername(context: Context) = getSharedPreference(
        context
    )?.getString(USERNAME,"")

    fun getPhoto(context: Context)= getSharedPreference(
        context
    )?.getString(PHOTO,"")

    fun setPhoto(context: Context, photo: String){
        editor(
            context,
            PHOTO,
            photo
        )
    }

    fun getRegion(context: Context)= getSharedPreference(
        context
    )?.getString(REGION,"")

    fun setRegion(context: Context, region: String){
        editor(
            context,
            REGION,
            region
        )
    }

}
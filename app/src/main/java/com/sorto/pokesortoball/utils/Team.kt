package com.sorto.pokesortoball.utils

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Team(val name: String? = null, val number: String? = null, val region: String? = null, val description: String? = null, val url: String? = null, @Exclude val key: String? = null) {

}

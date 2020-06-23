package de.hska.musiconnect.models

import java.io.Serializable

data class Artist(val name: String, val rating: String, val city: String, val genre: String, val price: String) : Serializable
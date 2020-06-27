package de.hska.musiconnect.models

import java.io.Serializable

data class ServiceProvider(val name: String, val rating: String, val city: String, val industry: String, val price: String) : Serializable
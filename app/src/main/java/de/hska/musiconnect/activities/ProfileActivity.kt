package de.hska.musiconnect.activities

import android.os.Bundle
import de.hska.musiconnect.R
import de.hska.musiconnect.actionbars.ActionBar
import de.hska.musiconnect.models.Artist
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : ActionBar() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(x_profile_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val artist = intent.getSerializableExtra("artist") as Artist
        x_profile_artist_name.text = artist.name
        x_profile_artist_rating.text = artist.rating
        x_profile_artist_location.text = artist.city
        x_profile_artist_price.text = artist.price
        x_profile_artist_genre.text = artist.genre
    }
}

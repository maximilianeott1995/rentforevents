package de.hska.musiconnect.activities

import android.os.Bundle
import de.hska.musiconnect.R
import de.hska.musiconnect.actionbars.ActionBar
import de.hska.musiconnect.models.ServiceProvider
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : ActionBar() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(x_profile_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val serviceProvider = intent.getSerializableExtra("serviceProvider") as ServiceProvider
        x_profile_serviceProvider_name.text = serviceProvider.name
        x_profile_serviceProvider_rating.text = serviceProvider.rating
        x_profile_serviceProvider_location.text = serviceProvider.city
        x_profile_serviceProvider_price.text = serviceProvider.price
        x_profile_serviceProvider_industry.text = serviceProvider.industry
    }
}

package de.hska.musiconnect.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.hska.musiconnect.R
import de.hska.musiconnect.actionbars.LogoutActionBar
import de.hska.musiconnect.adapter.ArtistAdapter
import de.hska.musiconnect.models.Artist
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : LogoutActionBar(), AdapterView.OnItemSelectedListener {
    private val artists = arrayListOf(
        Artist("Mike", "1.5/10", "Hamburg", "Rock", "150€/Stunde"),
        Artist("Leon", "2.9/10", "Berlin", "Indie", "200€/Stunde"),
        Artist("Andreas", "3.4/10", "Stuttgart", "Rap", "250€/Stunde"),
        Artist("Martin", "4.6/10", "Karlsruhe", "Hip-Hop", "300€/Stunde"),
        Artist("Flo", "5.3/10", "Bochum", "Schlager", "350€/Stunde"),
        Artist("Salva", "6.2/10", "Hamburg", "Indie", "450€/Stunde"),
        Artist("Constantin", "7.5/10", "Heilbronn", "Rap", "500€/Stunde"),
        Artist("Tim", "8.8/10", "Neckarsulm", "Hip-Hop", "100€/Stunde"),
        Artist("Sebastian", "9.8/10", "Abstatt", "Schlager", "125€/Stunde"),
        Artist("Stefan", "10/10", "Düsseldorf", "Rock", "275€/Stunde"),
        Artist("Peter", "1.0/10", "Ettlingen", "Indie", "325€/Stunde"),
        Artist("NOVA", "2.3/10", "Kiel", "Rap", "175€/Stunde"),
        Artist("KEPA", "4.7/10", "Münster", "Hip-Hop", "370€/Stunde"),
        Artist("JessBand", "8.3/10", "München", "Schlager", "500€/Stunde")
    )

    private val genres =
        arrayListOf("Genre auswählen", "Rock", "Indie", "Rap", "Hip-Hop", "Schlager")
    private lateinit var genreAdapter: ArrayAdapter<String>
    private var artistAdapter = ArtistAdapter(artists, this)
    private var selectedGenre = 0
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(x_main_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewManager = LinearLayoutManager(applicationContext)
        x_artist_recycler.apply {
            layoutManager = viewManager
            adapter = artistAdapter
        }

        genreAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genres)
        x_genre_dropdown.adapter = genreAdapter
        genreAdapter.notifyDataSetChanged()

        x_genre_dropdown.onItemSelectedListener = this
    }

    fun onSearch(view: View) {
        val query = x_search_query_text?.text.toString().toLowerCase(Locale.getDefault()).trim()
        if (query.isBlank()) {
            artistAdapter.update(artists)
        }
        if (selectedGenre != 0) {
            artistAdapter.update(artists.filter {
                genres.indexOf(it.genre) == selectedGenre && (it.city.toLowerCase(Locale.getDefault()).contains(
                    query
                ) ||
                        it.name.toLowerCase(Locale.getDefault()).contains(query))
            })
        } else {
            artistAdapter.update(artists.filter {
                it.city.toLowerCase(Locale.getDefault()).contains(
                    query
                ) ||
                        it.name.toLowerCase(Locale.getDefault()).contains(query)
            })
        }
        artistAdapter.notifyDataSetChanged()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedGenre = position
        when (position) {
            //Empty
            0 -> return
            //Rock
            1 -> artistAdapter.update(artists.filter { it.genre == "Rock" })
            //Indie
            2 -> artistAdapter.update(artists.filter { it.genre == "Indie" })
            //Rap
            3 -> artistAdapter.update(artists.filter { it.genre == "Rap" })
            //Hip-Hop
            4 -> artistAdapter.update(artists.filter { it.genre == "Hip-Hop" })
            // Schlager
            5 -> artistAdapter.update(artists.filter { it.genre == "Schlager" })
        }
    }

    override fun onBackPressed() {
        showLogoutDialog()
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout?")
            .setMessage("Bist Du sicher, dass du dich ausloggen willst?")

            // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog transparent_button is clicked.
            .setPositiveButton("JA") { _, _ ->
                val intent = Intent(applicationContext, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }

            // A null listener allows the transparent_button to dismiss the dialog and take no further action.
            .setNegativeButton("Abbrechen", null)
            //                .setIcon(android.R.drawable.ic_dialog_alert)
            .create()
            .show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}

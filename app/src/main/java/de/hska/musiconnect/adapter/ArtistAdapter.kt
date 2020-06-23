package de.hska.musiconnect.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hska.musiconnect.R
import de.hska.musiconnect.activities.ProfileActivity
import de.hska.musiconnect.models.Artist

class ArtistAdapter(var artists: List<Artist>, val context: Context) : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.artist_item, parent, false)
        return ArtistViewHolder(itemView)
    }

    override fun getItemCount() = artists.size

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val currentArtist = artists[position]
        holder.itemView.findViewById<TextView>(R.id.x_artist_name).text = currentArtist.name
        holder.itemView.findViewById<TextView>(R.id.x_artist_rating).text = currentArtist.rating
        holder.itemView.findViewById<TextView>(R.id.x_artist_city).text = currentArtist.city
        holder.itemView.findViewById<TextView>(R.id.x_artist_genre).text = currentArtist.genre
    }

    fun update(newArtists: List<Artist>) {
        artists = newArtists
        this.notifyDataSetChanged()
    }

    inner class ArtistViewHolder(artistViewItem: View) : RecyclerView.ViewHolder(artistViewItem) {

        init {
            artistViewItem.findViewById<Button>(R.id.x_profile_button).setOnClickListener {
                val intent = Intent(context, ProfileActivity::class.java)
                intent.putExtra("artist", artists.find { it.name == artistViewItem.findViewById<TextView>(R.id.x_artist_name).text })
                context.startActivity(intent)
            }
        }

    }
}

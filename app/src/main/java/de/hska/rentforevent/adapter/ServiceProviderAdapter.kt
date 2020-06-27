package de.hska.rentforevent.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hska.rentforevent.R
import de.hska.rentforevent.activities.ProfileActivity
import de.hska.rentforevent.models.ServiceProvider

class ServiceProviderAdapter(var serviceProviders: List<ServiceProvider>, val context: Context) : RecyclerView.Adapter<ServiceProviderAdapter.ServiceProviderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceProviderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.service_provider_item, parent, false)
        return ServiceProviderViewHolder(itemView)
    }

    override fun getItemCount() = serviceProviders.size

    override fun onBindViewHolder(holder: ServiceProviderViewHolder, position: Int) {
        val currentServiceProvider = serviceProviders[position]
        holder.itemView.findViewById<TextView>(R.id.x_serviceProvider_name).text = currentServiceProvider.name
        holder.itemView.findViewById<TextView>(R.id.x_serviceProvider_rating).text = currentServiceProvider.rating
        holder.itemView.findViewById<TextView>(R.id.x_serviceProvider_city).text = currentServiceProvider.city
        holder.itemView.findViewById<TextView>(R.id.x_serviceProvider_industry).text = currentServiceProvider.industry
    }

    fun update(newServiceProviders: List<ServiceProvider>) {
        serviceProviders = newServiceProviders
        this.notifyDataSetChanged()
    }

    inner class ServiceProviderViewHolder(serviceProviderViewItem: View) : RecyclerView.ViewHolder(serviceProviderViewItem) {

        init {
            serviceProviderViewItem.findViewById<Button>(R.id.x_profile_button).setOnClickListener {
                val intent = Intent(context, ProfileActivity::class.java)
                intent.putExtra("serviceProvider", serviceProviders.find { it.name == serviceProviderViewItem.findViewById<TextView>(R.id.x_serviceProvider_name).text })
                context.startActivity(intent)
            }
        }

    }
}

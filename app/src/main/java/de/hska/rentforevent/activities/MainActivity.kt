package de.hska.rentforevent.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.hska.rentforevent.R
import de.hska.rentforevent.actionbars.LogoutActionBar
import de.hska.rentforevent.adapter.ServiceProviderAdapter
import de.hska.rentforevent.models.ServiceProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : LogoutActionBar(), AdapterView.OnItemSelectedListener {
    private val serviceProviders = arrayListOf(
        ServiceProvider("Mike", "1.5/10", "Hamburg", "Unterhaltung", "150€/Stunde"),
        ServiceProvider("Leon", "2.9/10", "Berlin", "Indie", "200€/Stunde"),
        ServiceProvider("Andreas", "3.4/10", "Stuttgart", "Rap", "250€/Stunde"),
        ServiceProvider("Martin", "4.6/10", "Karlsruhe", "Hip-Hop", "300€/Stunde"),
        ServiceProvider("Flo", "5.3/10", "Bochum", "Catering", "350€/Stunde"),
        ServiceProvider("Salva", "6.2/10", "Hamburg", "Indie", "450€/Stunde"),
        ServiceProvider("Constantin", "7.5/10", "Heilbronn", "Rap", "500€/Stunde"),
        ServiceProvider("Tim", "8.8/10", "Neckarsulm", "Hip-Hop", "100€/Stunde"),
        ServiceProvider("Sebastian", "9.8/10", "Abstatt", "Schlager", "125€/Stunde"),
        ServiceProvider("Stefan", "10/10", "Düsseldorf", "Rock", "275€/Stunde"),
        ServiceProvider("Peter", "1.0/10", "Ettlingen", "Indie", "325€/Stunde"),
        ServiceProvider("NOVA", "2.3/10", "Kiel", "Rap", "175€/Stunde"),
        ServiceProvider("KEPA", "4.7/10", "Münster", "Hip-Hop", "370€/Stunde"),
        ServiceProvider("JessBand", "8.3/10", "München", "Schlager", "500€/Stunde")
    )

    private val industries =
        arrayListOf("Branche auswählen", "Unterhaltung", "Reinigung", "Catering", "Transport")
    private lateinit var industryAdapter: ArrayAdapter<String>
    private var serviceProviderAdapter = ServiceProviderAdapter(serviceProviders, this)
    private var selectedIndustry = 0
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(x_main_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewManager = LinearLayoutManager(applicationContext)
        x_serviceProvider_recycler.apply {
            layoutManager = viewManager
            adapter = serviceProviderAdapter
        }

        industryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, industries)
        x_industry_dropdown.adapter = industryAdapter
        industryAdapter.notifyDataSetChanged()

        x_industry_dropdown.onItemSelectedListener = this
    }

    fun onSearch(view: View) {
        val query = x_search_query_text?.text.toString().toLowerCase(Locale.getDefault()).trim()
        if (query.isBlank()) {
            serviceProviderAdapter.update(serviceProviders)
        }
        if (selectedIndustry != 0) {
            serviceProviderAdapter.update(serviceProviders.filter {
                industries.indexOf(it.industry) == selectedIndustry && (it.city.toLowerCase(Locale.getDefault()).contains(
                    query
                ) ||
                        it.name.toLowerCase(Locale.getDefault()).contains(query))
            })
        } else {
            serviceProviderAdapter.update(serviceProviders.filter {
                it.city.toLowerCase(Locale.getDefault()).contains(
                    query
                ) ||
                        it.name.toLowerCase(Locale.getDefault()).contains(query)
            })
        }
        serviceProviderAdapter.notifyDataSetChanged()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedIndustry = position
        when (position) {
            0 -> return
            1 -> serviceProviderAdapter.update(serviceProviders.filter { it.industry == "Unterhaltung" })
            2 -> serviceProviderAdapter.update(serviceProviders.filter { it.industry == "Reinigung" })
            3 -> serviceProviderAdapter.update(serviceProviders.filter { it.industry == "Catering" })
            4 -> serviceProviderAdapter.update(serviceProviders.filter { it.industry == "Transport" })
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

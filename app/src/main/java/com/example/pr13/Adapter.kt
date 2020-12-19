package com.example.pr9

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pr13.Country
import com.example.pr13.MainActivity
import com.example.pr13.R
import kotlinx.android.synthetic.main.country.view.*

class CountriesAdapter(val countriesList: List<Country>, val mainActivity: MainActivity) : RecyclerView.Adapter<CountriesAdapter.CountryHolder>() {

    class CountryHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : CountriesAdapter.CountryHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.country,parent,false)
        return CountryHolder(view)
    }

    override fun onBindViewHolder(holder: CountriesAdapter.CountryHolder, position: Int) {
        val country = countriesList[position]
        holder.view.findViewById<TextView>(R.id.name).text = country.name
        holder.view.findViewById<TextView>(R.id.region).text = country.region
        holder.view.findViewById<TextView>(R.id.capital).text = country.capital
        holder.view.findViewById<TextView>(R.id.subRegion).text = country.subregion
        holder.view.findViewById<TextView>(R.id.population).text = country.population.toString()
       /* holder.view.setOnClickListener{
            val intent = Intent(mainActivity,ShowCountryActivity::class.java)
            intent.putExtra("COUNTRY", countriesList[position])
            intent.putExtra("MODE","edit")
            mainActivity.startActivity(intent)
        }*/
    }

    override fun getItemCount() = countriesList.size

}
package com.example.pr13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.pr9.CountriesAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.load).setOnClickListener {
            val db = Room.databaseBuilder(applicationContext,CountryDatabase::class.java,"Country").build()
            val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_countries)

            CoroutineScope(Dispatchers.Main).launch {

                var list :List<CountryDB>? = null
                withContext(Dispatchers.IO) {
                    list = db.countryDao().getAll()
                }

                var listForRecyclerview = list!!.map {Country().apply {
                    name = it.name
                    capital = it.capital
                    region = it.region
                    subregion = it.subregion
                    population = it.population
                }}
                recyclerView.adapter = CountriesAdapter(listForRecyclerview, this@MainActivity)
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            }

            val currency = findViewById<EditText>(R.id.currency).text.toString()

            val retrofit = Retrofit.Builder().baseUrl("https://restcountries.eu")
                .addConverterFactory(GsonConverterFactory.create()).build()

            val service = retrofit.create(RestCountries::class.java)

            val call = service.getCountriesByCurrency(currency)

            val callEnqueue = call.enqueue(object : Callback<List<Country>> {
                override fun onResponse(
                    call: Call<List<Country>>,
                    response: Response<List<Country>>
                ) {

                    //recyclerView.post {

                    val list = response.body()!!



                    CoroutineScope(Dispatchers.IO).launch {

                        db.countryDao().clear()
                        db.countryDao().insert(
                            list.map {
                                CountryDB(
                                    it.region,
                                    it.name,
                                    it.subregion,
                                    it.capital,
                                    it.population
                                )


                            }
                        )
                    }

                    //}

                }

                override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                    //recyclerView.post {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.error_warn),
                        Toast.LENGTH_SHORT
                    ).show()
                    //}
                }

            })

        }
    }
}

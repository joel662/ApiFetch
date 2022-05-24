package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

const val BASE_URL = "https://jsonplaceholder.typicode.com/"
lateinit var txt : TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        getMyData()
    }

    private fun getMyData() {
        txt = findViewById(R.id.txt)

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory((GsonConverterFactory.create()))
            .baseUrl(BASE_URL).build()
            .create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(
                call: Call<List<MyDataItem>?>,
                response: Response<List<MyDataItem>?>
            ) {
                val responseBody = response.body()!!
                val string = StringBuilder()
                for(myData in responseBody){
                    string.append(myData.id )
                    string.append(myData.title)
                    string.append(myData.body)
                    string.append("\n")
                }
                txt.setText(string.toString())
            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                txt.text = "Error"
            }
        })
    }
}
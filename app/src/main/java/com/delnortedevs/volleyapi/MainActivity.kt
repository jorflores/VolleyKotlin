package com.delnortedevs.volleyapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.delnortedevs.volleyapi.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistro.setOnClickListener{

            val destino = binding.editTextDestino.text.toString()
            val comentarios = binding.editTextComentarios.text.toString()

            altaRegistro(destino,comentarios)
        }

        binding.btnGetdata.setOnClickListener{

            getData()
        }
    }

    fun altaRegistro(destino: String,comentarios: String) {
        val queue = Volley.newRequestQueue(this)

        val url = "yoururl"

        val jsonobj = JSONObject()
        jsonobj.put("destino",destino)
        jsonobj.put("comentarios",comentarios)

        val req = JsonObjectRequest(Request.Method.POST, url, jsonobj,

            {
                    response -> Log.d("API",response.toString())
            },

            {
                Log.d("API",it.toString())
            }
        )
        queue.add(req)
    }

    fun getData() {

        //1 new Queue request
        val queue = Volley.newRequestQueue(this)
        val url = "yoururl"

        // Request a string response from the provided URL.
        // 2
        val stringReq = object: StringRequest(
            Request.Method.GET, url,
            { response ->

                Log.i("API",response.toString())

                var strResp = response.toString()
                val jsonArray = JSONArray(strResp)
                Log.i("API","" + jsonArray)

                for (i in 0 until jsonArray.length()) {
                    var jsonInner: JSONObject = jsonArray.getJSONObject(i)
                    Log.i("API",jsonInner.get("destino").toString())
                }

            },
            {Log.i("API",it.toString())  } )

        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["auth_key"] = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJiYW5jb0FsaW1lbnRvcyI6ImJhbmNvQWxpbWVudG9zIiwiaWF0IjoxNjM2NTkzNTExfQ.c4YfeRTJJ_or3wIOt2MXA7i27Xv6IhSvqEbXhy7k1YU"
                return headers
            }
        }
        queue.add(stringReq)
    }

}
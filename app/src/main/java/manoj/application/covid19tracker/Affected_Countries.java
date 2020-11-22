package manoj.application.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Affected_Countries extends AppCompatActivity {

    RecyclerView recyclerView;
    CountryAdapter adapter;

    List<CountriesModel> countriesModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected__countries);

        recyclerView = findViewById(R.id.countriesRecyclerView);

        getData();
        adapter = new CountryAdapter(this, countriesModels);
        recyclerView.setAdapter(adapter);

       /* countriesModels.add(new CountriesModel("manoj",url)); */


    }

    private void getData() {

        String url1 = "https://disease.sh/v3/covid-19/countries";
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i= 0;i<response.length();i++)
                    {

                        JSONObject jsonObject = jsonArray.getJSONObject((i));

                        String country = jsonObject.getString("country");
                        String cases = jsonObject.getString("cases");
                        String todayCases = jsonObject.getString("todayCases");
                        String deaths = jsonObject.getString("deaths");
                        String todayDeaths = jsonObject.getString("todayDeaths");
                        String recovered = jsonObject.getString("recovered");
                        String todayRecovered = jsonObject.getString("todayRecovered");
                        String active = jsonObject.getString("active");
                        String critical = jsonObject.getString("critical");
                        String population = jsonObject.getString("population");

                        JSONObject object = jsonObject.getJSONObject("countryInfo");
                        String flagUrl = object.getString("flag");

                        countriesModels.add(new CountriesModel(country, cases, todayCases, deaths, todayDeaths, recovered, todayRecovered, active, critical, population, flagUrl));


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Affected_Countries.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(stringRequest);




    }
}
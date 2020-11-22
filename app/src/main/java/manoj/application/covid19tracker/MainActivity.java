

package manoj.application.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    TextView tvCases,tvRecovered,tvCritical,tvActive,tvTodayCases,tvTotalDeaths,tvTodayDeaths,tvAffectedCountries;

    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;

    Button btnCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCases = findViewById(R.id.tv_cases);
        tvRecovered = findViewById(R.id.tv_recoverd);
        tvCritical = findViewById(R.id.tv_critical);
        tvActive = findViewById(R.id.tv_active);
        tvTodayCases = findViewById(R.id.tv_todayCases);
        tvTotalDeaths = findViewById(R.id.tv_totalDeaths);
        tvTodayDeaths = findViewById(R.id.tv_todayDeaths);
        tvAffectedCountries = findViewById(R.id.tv_affectedCountries);

        simpleArcLoader = findViewById(R.id.loader);
        pieChart = findViewById(R.id.pieChart);
        scrollView = findViewById(R.id.scrollStats);
        btnCountries = findViewById(R.id.button);
        
        fetchData();

    }

    private void fetchData() {

        String url = "https://disease.sh/v3/covid-19/all/";
        simpleArcLoader.start();
        StringRequest request = new StringRequest(Request.Method.GET,url,
        new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    tvCases.setText(jsonObject.getString("cases"));
                    tvActive.setText(jsonObject.getString("active"));
                    tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                    tvTotalDeaths.setText(jsonObject.getString("deaths"));
                    tvAffectedCountries.setText(jsonObject.getString("affectedCountries"));
                    tvRecovered.setText(jsonObject.getString("recovered"));
                    tvTodayCases.setText(jsonObject.getString("todayCases"));
                    tvCritical.setText(jsonObject.getString("critical"));

                    pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(tvCases.getText().toString()),Color.parseColor("#FFA726")));
                    pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(tvRecovered.getText().toString()),Color.parseColor("#66BB6A")));
                    pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvTotalDeaths.getText().toString()),Color.parseColor("#EF5350")));
                    pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(tvActive.getText().toString()),Color.parseColor("#29B6F6")));

                    pieChart.startAnimation();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }


            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


        btnCountries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent i = new Intent(getApplicationContext(),Affected_Countries.class);
                startActivity(i);*/

                startActivity(new Intent(getApplicationContext(),Affected_Countries.class));
            }
        });


    }
}
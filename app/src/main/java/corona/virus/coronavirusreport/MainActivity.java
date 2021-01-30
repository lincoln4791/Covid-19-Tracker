package corona.virus.coronavirusreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.virus.coronavirusreport.R;

import corona.virus.coronavirusreport.affectedCountries.AffectedCountries;
import corona.virus.coronavirusreport.common.MySingletonRequestQueue;
import corona.virus.coronavirusreport.common.NodeNamesForJasonData;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView tv_cases,tv_casesToday,tv_deaths,tv_deathsToday,tv_recovered,tv_recoveredToday,tv_active,tv_critical,tv_tests,tv_affectedCountries;
    private PieChart pieChart;
    private ScrollView scrollView;
    private SimpleArcLoader arcLoader,arcLoaderPieChart;
    private Button btn_trackCountry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        tv_cases = findViewById(R.id.tv_CasesValue_GlobalStateDetails_MainActivity);
        tv_casesToday = findViewById(R.id.tv_CasesTodayValue_GlobalStateDetails_MainActivity);
        tv_deaths = findViewById(R.id.tv_CasesDeathValue_GlobalStateDetails_MainActivity);
        tv_deathsToday = findViewById(R.id.tv_CasesDeathsTodayValue_GlobalStateDetails_MainActivity);
        tv_recovered = findViewById(R.id.tv_CasesRecovered_Value_GlobalStateDetails_MainActivity);
        tv_recoveredToday = findViewById(R.id.tv_CasesRecoveredTodayValue_GlobalStateDetails_MainActivity);
        tv_active = findViewById(R.id.tv_CasesActiveValue_GlobalStateDetails_MainActivity);
        tv_critical = findViewById(R.id.tv_CasesCriticalValue_GlobalStateDetails_MainActivity);
        tv_tests = findViewById(R.id.tv_CasesTestsValue_GlobalStateDetails_MainActivity);
        tv_affectedCountries = findViewById(R.id.tv_CasesAffectedCountriesValue_GlobalStateDetails_MainActivity);
        btn_trackCountry = findViewById(R.id.buttonTrackCountry_MainActivity);

        //Action Bar
        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        scrollView=findViewById(R.id.scrollVIew_MainActivity);
        pieChart = findViewById(R.id.pieChart);
        arcLoader = findViewById(R.id.arcLoader);
        arcLoaderPieChart = findViewById(R.id.arcLoader_PieChart_MainActivity);


        //Executions
        fetchData();


        //On click Listeners
        btn_trackCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AffectedCountries.class);
                startActivity(intent);
            }
        });
    }

    private void fetchData() {
        scrollView.setVisibility(View.GONE);
        arcLoader.setVisibility(View.VISIBLE);
        arcLoader.start();

        //pieChart.setVisibility(View.GONE);
        arcLoaderPieChart.setVisibility(View.VISIBLE);
        arcLoaderPieChart.start();

        String url = "https://corona.lmao.ninja/v2/all";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    tv_cases.setText(response.getString(NodeNamesForJasonData.CASES));
                    tv_casesToday.setText(response.getString(NodeNamesForJasonData.CASES_TODAY));
                    tv_deaths.setText(response.getString(NodeNamesForJasonData.DEATHS));
                    tv_deathsToday.setText(response.getString(NodeNamesForJasonData.DEATHS_TODAY));
                    tv_recovered.setText(response.getString(NodeNamesForJasonData.RECOVERED));
                    tv_recoveredToday.setText(response.getString(NodeNamesForJasonData.RECOVERED_TODAY));
                    tv_active.setText(response.getString(NodeNamesForJasonData.ACTIVE));
                    tv_critical.setText(response.getString(NodeNamesForJasonData.CRITICAL));
                    tv_tests.setText(response.getString(NodeNamesForJasonData.TESTS));
                    tv_affectedCountries.setText(response.getString(NodeNamesForJasonData.AFFECTED_COUNTRIES));


                    scrollView.setVisibility(View.VISIBLE);
                    arcLoader.stop();
                    arcLoader.setVisibility(View.GONE);

                    //pieChart.setVisibility(View.VISIBLE);
                    arcLoaderPieChart.stop();
                    arcLoaderPieChart.setVisibility(View.GONE);

                    pieChart.addPieSlice(new PieModel(getString(R.string.cases), Integer.valueOf(tv_cases.getText().toString()),getColor(R.color.caseTotal)));
                    pieChart.addPieSlice(new PieModel(getString(R.string.casesRecovered), Integer.valueOf(tv_recovered.getText().toString()),getColor(R.color.caseRecovered)));
                    pieChart.addPieSlice(new PieModel(getString(R.string.casesActive), Integer.valueOf(tv_active.getText().toString()),getColor(R.color.caseActive)));
                    pieChart.addPieSlice(new PieModel(getString(R.string.casesDeaths), Integer.valueOf(tv_deaths.getText().toString()),getColor(R.color.caseDeath)));
                    pieChart.startAnimation();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Failed"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        MySingletonRequestQueue.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
    }

}
package com.msedek.msedek.testslim;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.msedek.msedek.testslim.Adapter.RecyclerViewAdapter;
import com.msedek.msedek.testslim.Interfaz.iSendCliente;
import com.msedek.msedek.testslim.Models.Cliente;
import com.msedek.msedek.testslim.Models.Vehiculo;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listClientVehActivity extends AppCompatActivity {

    TextView tx_nom;
    TextView tx_id;
    RecyclerView recyclerView;
    RecyclerViewAdapter adp;

    ArrayList<Cliente> clientes;
    ArrayList<Vehiculo> vehiculos;

    Button btn_atras;

    String selector;
    boolean refrescar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_client_veh);

        tx_nom       = findViewById(R.id.tx_nomb);
        tx_id        = findViewById(R.id.tx_id);
        recyclerView = findViewById(R.id.recyclerView);
        btn_atras    = findViewById(R.id.btn_atras);

        clientes  = new ArrayList<>();
        vehiculos = new ArrayList<>();




        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


    private void getselector()
    {
        Intent intent = getIntent();
        selector =  Objects.requireNonNull(intent.getExtras()).getString("selector");
    }

    public void getAllCLientes()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<ArrayList<Cliente>> call = request.getJSONClientes();

        call.enqueue(new Callback<ArrayList<Cliente>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Cliente>> call, @NonNull Response<ArrayList<Cliente>> response)
            {

                if(response.code() == 200)
                {

                    clientes.addAll(response.body());

                    setAdapter(clientes,null);

                }
                else
                {
                    showmessage("NO hay Clientes Registrados");
                }

            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Cliente>> call, @NonNull Throwable t)
            {

                t.printStackTrace();

            }
        });

    }

    public void getAllVehiculos()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<ArrayList<Vehiculo>> call = request.getJSONVehiculos();

        call.enqueue(new Callback<ArrayList<Vehiculo>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Vehiculo>> call, @NonNull Response<ArrayList<Vehiculo>> response)
            {


                if(response.code() == 200)
                {

                    vehiculos.addAll(response.body());

                    setAdapter(null,vehiculos);

                }
                else
                {
                    showmessage("NO hay Vehiculos Registrados");
                }

            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Vehiculo>> call, @NonNull Throwable t)
            {

                t.printStackTrace();

            }
        });

    }

    private void showmessage(String mensaje)
    {
        final Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 1000);

    }

    private void setAdapter(ArrayList<Cliente> cli, ArrayList<Vehiculo> ve) {

        if (ve == null) {
            adp = new RecyclerViewAdapter(getApplicationContext(), cli, null, tx_nom, tx_id, listClientVehActivity.this);
        } else {
            adp = new RecyclerViewAdapter(getApplicationContext(), null, ve, tx_nom, tx_id, listClientVehActivity.this);
        }

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adp);
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerView.setAdapter(null);

        getselector();

        if(selector.equals("clientes"))
        {
            getAllCLientes();
        }
        else
        {
            getAllVehiculos();
        }


    }
}

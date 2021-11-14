package com.msedek.msedek.testslim;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.TestLooperManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.msedek.msedek.testslim.Adapter.RecyclerViewAdapterOrden;
import com.msedek.msedek.testslim.Interfaz.iSendCliente;
import com.msedek.msedek.testslim.Models.Estado;
import com.msedek.msedek.testslim.Models.Orden;
import com.msedek.msedek.testslim.Models.Prorder;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminOrderActivity extends AppCompatActivity {

    TextView nrorden;
    Button   genot;
    Button   eliot;
    Button   cancelot;
    TextView tx_pos;
    TextView tx_ord;

    Spinner  spinnerOrder;
    TextView txt_estado;
    Button   btn_edit;

    ArrayList<Orden> ordenes;

    String config;

    RecyclerView recyclerView;

    RecyclerViewAdapterOrden adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order);


        nrorden = findViewById(R.id.txt_nrorden);
        genot   = findViewById(R.id.btn_genot);
        eliot   = findViewById(R.id.bnt_eliot);
        cancelot = findViewById(R.id.btn_cancelot);
        tx_pos  = findViewById(R.id.txt_pos);
        tx_ord  = findViewById(R.id.txt_ord);
        spinnerOrder = findViewById(R.id.spinnerOrder);
        txt_estado = findViewById(R.id.txt_estorder);
        btn_edit = findViewById(R.id.btn_editorder);


        recyclerView = findViewById(R.id.recyclerView3);


        getdata();

        getAllEstados();



        genot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), OrdenesActivity.class);
                intent.putExtra("config", "boton");
                startActivity(intent);

            }
        });

        cancelot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        eliot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tx_ord.getText().toString().equals("no"))
                {
                    showmessage("SELECCIONE UNA ORDEN");
                }
                else
                {
                    showmessage("ELIMINASTE ORDEN " + tx_ord.getText().toString());

                    int posi = Integer.parseInt(tx_pos.getText().toString());

                    ordenes.remove(posi);
                    adp.notifyDataSetChanged();

                    deleteOrden(Integer.parseInt(tx_ord.getText().toString()));
                    loadProrders();

                }
            }
        });


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tx_ord.getText().toString().equals("no"))
                {
                    showmessage("SELECCIONE UNA ORDEN");
                }
                else
                {

                    int posi = Integer.parseInt(tx_pos.getText().toString());

                    Orden orden = ordenes.get(posi);
                    orden.setEstado_orden(spinnerOrder.getSelectedItem().toString());
                    editOrden(orden);
                    adp.notifyItemChanged(posi);

                }
            }
        });

    }

    private void getdata() {
        Intent intent = getIntent();
        config = intent.getExtras().getString("config");
    }

    private void loadOrdenes() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<ArrayList<Orden>> call = request.getJSONOrdenes();

        call.enqueue(new Callback<ArrayList<Orden>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ArrayList<Orden>> call, @NonNull Response<ArrayList<Orden>> response) {

                if (response.code() == 200)
                {

                    ordenes.addAll(response.body());

                   setAdapter(ordenes);

                }
                else
                {
                    showmessage("NO HAY RESERVAS PENDIENTES");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Orden>> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });
    }

    private void loadProrders() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<ArrayList<Prorder>> call = request.getJSONProrders();

        call.enqueue(new Callback<ArrayList<Prorder>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ArrayList<Prorder>> call, @NonNull Response<ArrayList<Prorder>> response) {

                    for(Prorder prorder : response.body())
                    {

                        String base = tx_ord.getText().toString();
                        String env  = prorder.getOr_id();

                        if(base.equals(env))
                        {
                            deleteProrder(prorder.getPror_id());
                        }
                    }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Prorder>> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });
    }

    private void showmessage(String mensaje) {
        final Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 1000);

    }

    private void setAdapter(ArrayList<Orden> ords) {

        adp = new RecyclerViewAdapterOrden(getApplicationContext(),ords, tx_pos, tx_ord, nrorden, txt_estado);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adp);

    }

    private void deleteOrden(int i) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Orden> call = request.deleteOrden(i);

        call.enqueue(new Callback<Orden>()
        {
            @Override
            public void onResponse(@NonNull Call<Orden> call, @NonNull Response<Orden> response)
            {

                nrorden.setText("");

            }

            @Override
            public void onFailure(@NonNull Call<Orden> call, @NonNull Throwable t)
            {
                t.printStackTrace();
            }

        });

    }

    private void deleteProrder(int i) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Prorder> call = request.deleteProrder(i);

        call.enqueue(new Callback<Prorder>()
        {
            @Override
            public void onResponse(@NonNull Call<Prorder> call, @NonNull Response<Prorder> response)
            {
            }

            @Override
            public void onFailure(@NonNull Call<Prorder> call, @NonNull Throwable t)
            {
                t.printStackTrace();
            }

        });

    }

    private void getAllEstados() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<ArrayList<Estado>> call = request.getJSONEstados();

        call.enqueue(new Callback<ArrayList<Estado>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Estado>> call, @NonNull Response<ArrayList<Estado>> response)
            {

                if (response.code() == 200) {

                    ArrayList<Estado> estllega = response.body();
                    ArrayList<String> est = new ArrayList<>();

                    for (Estado estado : estllega) {

                        est.add(estado.getEst_nombre());
                    }

                    if (est.size() > 0) {

                        String[] varray = new String[est.size()];
                        varray = est.toArray(varray);

                        ArrayAdapter<String> adapterEstado = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_row, varray);
                        spinnerOrder.setAdapter(adapterEstado);

                        spinnerOrder.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                            protected Adapter initializedAdapter = null;

                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                // Always ignore the initial selection performed after setAdapter
                                if( initializedAdapter !=parent.getAdapter() ) {
                                    initializedAdapter = parent.getAdapter();
                                    return;
                                }

                                txt_estado.setText(spinnerOrder.getSelectedItem().toString());
                                //.setEstado_orden(spinnerOrder.getSelectedItem().toString()); TODO SETEAR LA ORDEN PARA MANDAR


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }
                    else
                    {
                        showmessage("NO HAY ESTADOS REGISTRADOS");
                    }

                }


            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Estado>> call, @NonNull Throwable t)
            {

                t.printStackTrace();

            }
        });

    }

    private void editOrden(Orden orden) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Orden> call = request.updateOrden(orden.getOt_id() , orden);

        call.enqueue(new Callback<Orden>()
        {
            @Override
            public void onResponse(@NonNull Call<Orden> call, @NonNull Response<Orden> response)
            {

                //MODO ACA ES RECYCLER
                if (response.code() == 200)
                {
                    showmessage("ACTUALIZACION EXITOSA");

                    nrorden.setText("");
                    txt_estado.setText("");


                }
                else
                {
                    if(response.code() == 500)
                    {
                        showmessage("ERROR DE ACTUALIZACION");
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<Orden> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerView.setAdapter(null);

        ordenes = new ArrayList<>();

        loadOrdenes();

    }
}


package com.msedek.msedek.testslim;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.msedek.msedek.testslim.Adapter.RecyclerViewAdapter2;
import com.msedek.msedek.testslim.Interfaz.iSendCliente;
import com.msedek.msedek.testslim.Models.Cargo;
import com.msedek.msedek.testslim.Models.Categoria;
import com.msedek.msedek.testslim.Models.Empleado;
import com.msedek.msedek.testslim.Models.Estado;
import com.msedek.msedek.testslim.Models.Producto;
import com.msedek.msedek.testslim.Models.Servicio;
import com.msedek.msedek.testslim.Models.Taller;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GestionActivity extends AppCompatActivity {

    TextView txt_dato;
    TextView txt_datoid;
    Button   btn_atrasgestion;
    RecyclerView recyclerView2;
    RecyclerViewAdapter2 adp;

    String setup;

    ArrayList<Taller> loc;
    ArrayList<Empleado> emp;
    ArrayList<Cargo> car;
    ArrayList<Categoria> cat;
    ArrayList<Producto> pro;
    ArrayList<Servicio> ser;
    ArrayList<Estado> est;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);

        txt_dato         = findViewById(R.id.tx_dato);
        txt_datoid       = findViewById(R.id.tx_datoid);
        btn_atrasgestion = findViewById(R.id.btn_atrasgestion);
        recyclerView2    =findViewById(R.id.recyclerView2);



        btn_atrasgestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void getAlllocales() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<ArrayList<Taller>> call = request.getJSONTalleres();

        call.enqueue(new Callback<ArrayList<Taller>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Taller>> call, @NonNull Response<ArrayList<Taller>> response)
            {


                if(response.code() == 200)
                {

                    loc = response.body();

                    setAdapter(loc,null,null,null,null,null,null);


                }
                else
                {
                    showmessage("No hay Locales registrados");
                }

            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Taller>> call, @NonNull Throwable t)
            {

                t.printStackTrace();

            }
        });

    }

    private void getAllempleados() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<ArrayList<Empleado>> call = request.getJSONEmpleados();

        call.enqueue(new Callback<ArrayList<Empleado>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Empleado>> call, @NonNull Response<ArrayList<Empleado>> response)
            {


                if(response.code() == 200)
                {

                    emp = response.body();

                    setAdapter(null,emp,null,null,null,null,null);


                }
                else
                {
                    showmessage("No hay Empleados registrados");
                }


            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Empleado>> call, @NonNull Throwable t)
            {

                t.printStackTrace();

            }
        });

    }

    private void getAllcargos() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<ArrayList<Cargo>> call = request.getJSONCargos();

        call.enqueue(new Callback<ArrayList<Cargo>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Cargo>> call, @NonNull Response<ArrayList<Cargo>> response)
            {

                if(response.code() == 200)
                {

                    car = response.body();

                    setAdapter(null,null,car,null,null,null,null);


                }
                else
                {
                    showmessage("No hay Cargos registrados");
                }


            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Cargo>> call, @NonNull Throwable t)
            {

                t.printStackTrace();

            }
        });

    }

    private void getAllcategorias() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<ArrayList<Categoria>> call = request.getJSONCategorias();

        call.enqueue(new Callback<ArrayList<Categoria>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Categoria>> call, @NonNull Response<ArrayList<Categoria>> response)
            {


                if(response.code() == 200)
                {

                    cat = response.body();

                    setAdapter(null,null,null,cat,null,null,null);


                }
                else
                {
                    showmessage("No hay Categorias registradas");
                }

            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Categoria>> call, @NonNull Throwable t)
            {

                t.printStackTrace();

            }
        });

    }

    private void getAllproductos() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<ArrayList<Producto>> call = request.getJSONProductos();

        call.enqueue(new Callback<ArrayList<Producto>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Producto>> call, @NonNull Response<ArrayList<Producto>> response)
            {


                if(response.code() == 200)
                {

                    pro = response.body();

                    setAdapter(null,null,null,null,pro,null,null);


                }
                else
                {
                    showmessage("No hay Productos registrados");
                }

            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Producto>> call, @NonNull Throwable t)
            {

                t.printStackTrace();

            }
        });

    }

    private void getAllservicios(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<ArrayList<Servicio>> call = request.getJSONServicios();

        call.enqueue(new Callback<ArrayList<Servicio>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Servicio>> call, @NonNull Response<ArrayList<Servicio>> response)
            {


                if(response.code() == 200)
                {

                   ser = response.body();

                    setAdapter(null,null,null,null,null,ser,null);


                }
                else
                {
                    showmessage("No hay Servicios registrados");
                }

            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Servicio>> call, @NonNull Throwable t)
            {

                t.printStackTrace();

            }
        });

    }

    private void getAllestados() {


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


                if(response.code() == 200)
                {

                    est = response.body();

                    setAdapter(null,null,null,null,null,null,est);


                }
                else
                {
                    showmessage("No hay Estados registrados");
                }

            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Estado>> call, @NonNull Throwable t)
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

    private void setAdapter(ArrayList<Taller> loc, ArrayList<Empleado> emp, ArrayList<Cargo> car,
                            ArrayList<Categoria> cat, ArrayList<Producto> pro, ArrayList<Servicio>ser,
                            ArrayList<Estado> est) {

        adp = new RecyclerViewAdapter2(getApplicationContext(), loc, emp, car, cat, pro, ser, est, txt_dato, txt_datoid, setup, GestionActivity.this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(linearLayoutManager);
        recyclerView2.setAdapter(adp);
    }

    private void getconfig()
    {
        Intent intent = getIntent();
        setup = Objects.requireNonNull(intent.getExtras()).getString("selector");
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerView2.setAdapter(null);


        getconfig();


        switch (setup) {

            case "locales":

                recyclerView2.setAdapter(null);
                getAlllocales();

                break;

            case "empleados":

                recyclerView2.setAdapter(null);
                getAllempleados();

                break;

            case "cargos":

                recyclerView2.setAdapter(null);
                getAllcargos();

                break;

            case "categorias":

                recyclerView2.setAdapter(null);
                getAllcategorias();

                break;

            case "productos":

                recyclerView2.setAdapter(null);
                getAllproductos();

                break;

            case "servicios":

                recyclerView2.setAdapter(null);
                getAllservicios();

                break;

            case "estados":

                recyclerView2.setAdapter(null);
                getAllestados();

                break;


        }


    }
}

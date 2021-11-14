package com.msedek.msedek.testslim;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.msedek.msedek.testslim.Interfaz.iSendCliente;
import com.msedek.msedek.testslim.Models.Cliente;
import com.msedek.msedek.testslim.Models.Vehiculo;


import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AgregaVehiculoActivity extends AppCompatActivity {


    EditText edt_marca;
    EditText edt_modelo;
    EditText edt_ano;
    EditText edt_placass;
    Button btn_saveve;
    Button   btn_cancelve;
    EditText edt_dniclive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrega_vehiculo);


        edt_marca       = findViewById(R.id.edt_marca);
        edt_modelo      = findViewById(R.id.edt_modelo);
        edt_ano         = findViewById(R.id.edt_ano);
        edt_placass     = findViewById(R.id.edt_placass);
        btn_saveve      = findViewById(R.id.btn_saveve);
        btn_cancelve    = findViewById(R.id.btn_cancelve);
        edt_dniclive    = findViewById(R.id.edt_dniclive);


        btn_saveve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String test = edt_marca.getText().toString().trim();

                if(test.length() < 2 || test.matches(".*\\d+.*"))
                {
                    showmessage("ESCRIBA MARCA VALIDA");
                }
                else
                {
                    test = edt_modelo.getText().toString().trim();

                    if(test.length() < 2 )
                    {
                        showmessage("ESCRIBA MODELO VALIDO");
                    }
                    else
                    {
                        test = edt_ano.getText().toString().trim();

                        if(test.length() < 4 )
                        {
                            showmessage("ANO DE FABRICACION VALIDO");
                        }
                        else
                        {
                            test = edt_placass.getText().toString().trim();

                            if(test.length() < 6)
                            {
                                showmessage("ESCRIBA PLACA VALIDA");
                            }
                            else
                            {
                                test = edt_dniclive.getText().toString().trim();

                                if(test.length() < 8)
                                {
                                    showmessage("DNI NO VALIDO");
                                }
                                else
                                {

                                    pedirCliente(edt_dniclive.getText().toString());

                                }

                            }
                        }
                    }
                }
            }
        });

        btn_cancelve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    public void sendVehiculo(Vehiculo vehiculo)
    {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Vehiculo> call = request.addVehiculo(vehiculo);

        call.enqueue(new Callback<Vehiculo>()
        {
            @Override
            public void onResponse(@NonNull Call<Vehiculo> call, @NonNull Response<Vehiculo> response)
            {

                if (response.code() == 200)
                {
                    showmessage("Registro Exitoso");

                    finish();

                }
                else
                {
                    if(response.code() == 500)
                    {
                        showmessage("PLACA YA EXISTE");
                        edt_placass.requestFocus();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Vehiculo> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });
    }

    public void pedirCliente(String dni)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Cliente> call = request.getJSONClienteid(dni);

        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(@NonNull Call<Cliente> call, @NonNull Response<Cliente> response) {

                if (response.code() == 200)//VERIFICAMOS RECEPCION CORRECTA DE GET
                {
                    Cliente cliente = response.body();

                    assert cliente != null;

                    Vehiculo vehiculo = new Vehiculo();

                    vehiculo.setVeh_marca(edt_marca.getText().toString());
                    vehiculo.setVeh_modelo(edt_modelo.getText().toString());
                    vehiculo.setVeh_afab(edt_ano.getText().toString());
                    vehiculo.setVeh_placa(edt_placass.getText().toString());
                    vehiculo.setCli_dni(cliente.getCli_dni());

                    sendVehiculo(vehiculo);


                }
                else
                {
                    if(response.code() == 500)
                    {
                        showmessage("DNI NO REGISTRADO");
                        edt_dniclive.requestFocus();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cliente> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });
    }


/*    private void getdata()
    {
        Intent intent = getIntent();
        cliente = (Cliente) Objects.requireNonNull(intent.getExtras()).getSerializable("data");
    }*/



}

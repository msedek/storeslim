package com.msedek.msedek.testslim;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

public class editarVehiculoActivity extends AppCompatActivity {


    Vehiculo vehiculo;

    EditText edt_marca;
    EditText edt_modelo;
    EditText edt_anofab;
    EditText edt_dnicli;
    EditText edt_placasp;
    Button   btn_savep;
    Button   btn_cancelp;
    Button   btn_busveh;


    ConstraintLayout mainlve;

    String selector;
    Boolean swmodo;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_vehiculo);


        edt_marca       = findViewById(R.id.edt_marcavep);
        edt_modelo      = findViewById(R.id.edt_modelovep);
        edt_anofab      = findViewById(R.id.edt_anofabve);
        edt_dnicli      = findViewById(R.id.edt_deniclive);
        edt_placasp     = findViewById(R.id.edt_plasvep);

        btn_savep       = findViewById(R.id.btn_savevepe);
        btn_cancelp     = findViewById(R.id.bnt_cancelvepe);
        btn_busveh      = findViewById(R.id.btn_busveh);


        mainlve         = findViewById(R.id.mainlve);

        getselector();


        if(selector.equals("editar"))
        {
            btn_savep.setText("Guardar");
        }
        else
        {
            btn_savep.setText("Eliminar");
        }



        btn_busveh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edt_placasp.length() < 6)
                {
                    showmessage("PLACA INVALIDA");
                }
                else
                {
                    pedirVehiculo();

                    InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mainlve.getWindowToken(), 0);

                }
            }
        });


        btn_cancelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_savep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selector.equals("editar"))
                {
                    String test = edt_marca.getText().toString().trim();

                    if(test.length() < 3 || test.matches(".*\\d+.*"))
                    {
                        showmessage("ESCRIBA MARCA VALIDA");
                    }
                    else
                    {
                        test = edt_modelo.getText().toString().trim();

                        if(test.length() < 2 )
                        {
                            showmessage("ESCRIBA APELLIDO VALIDO");
                        }
                        else
                        {
                            test = edt_anofab.getText().toString().trim();

                            if (test.length() < 4)
                            {
                                showmessage("ANO INVALIDO");
                            } else
                                {
                                test = edt_dnicli.getText().toString().trim();

                                if (test.length() < 8)
                                {
                                    showmessage("DNI CLIENTE INVALIDO");
                                }
                                else
                                {

                                    pedirCliente();

                                }
                            }

                        }
                    }
                }
                else
                {
                    eliminarVehiculo();
                }


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
        }, 1500);

    }


    public void updateVehiculo(Vehiculo vehiculo)
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Vehiculo> call = request.updateVehiculo(vehiculo.getVeh_placa(), vehiculo);

        call.enqueue(new Callback<Vehiculo>()
        {
            @Override
            public void onResponse(@NonNull Call<Vehiculo> call, @NonNull Response<Vehiculo> response)
            {

                if (response.code() == 200)
                {
                    showmessage("ACTUALIZACION EXITOSA");

                    if(!swmodo)
                    {
                        Intent intent = new Intent(getApplicationContext(), listClientVehActivity.class);
                        intent.putExtra("selector", "vehiculos");
                        startActivity(intent);
                    }

                    finish();

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
            public void onFailure(@NonNull Call<Vehiculo> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });
    }

    public void pedirVehiculo()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Vehiculo> call = request.getJSONVehiculoid(edt_placasp.getText().toString());

        call.enqueue(new Callback<Vehiculo>() {
            @Override
            public void onResponse(@NonNull Call<Vehiculo> call, @NonNull Response<Vehiculo> response) {

                if (response.code() == 200)//VERIFICAMOS RECEPCION CORRECTA DE GET
                {

                    Vehiculo recv = response.body();

                    edt_marca.setText(recv.getVeh_marca());
                    edt_modelo.setText(recv.getVeh_modelo());
                    edt_anofab.setText(recv.getVeh_afab());
                    edt_dnicli.setText(recv.getCli_dni());

                    btn_savep.setEnabled(true);

                    if(selector.equals("editar"))
                    {

                        edt_marca.setEnabled(true);
                        edt_modelo.setEnabled(true);
                        edt_anofab.setEnabled(true);
                        edt_dnicli.setEnabled(true);

                    }
/*                    Vehiculo vehiculo = response.body();

                    assert vehiculo != null;
                    edt_nombrep.setText(cliente.getCli_nombre());
                    edt_apellidop.setText(cliente.getCli_apellido());
                    edt_mailp.setText(cliente.getCli_correo());
                    edt_celp.setText(cliente.getCli_celular());
                    edt_passp.setText(cliente.getCli_contrasena());
                    edt_passcp.setText(cliente.getCli_contrasena());



                    btn_savep.setEnabled(true);

                    if(selector.equals("editar"))
                    {
                        edt_nombrep.setEnabled(true);
                        edt_apellidop.setEnabled(true);
                        edt_mailp.setEnabled(true);
                        edt_celp.setEnabled(true);
                        edt_passp.setEnabled(true);
                        edt_passcp.setEnabled(true);

                    }*/




                }
                else
                {
                    if(response.code() == 500)
                    {
                        showmessage("PLACA NO REGISTRADA");
                        edt_placasp.requestFocus();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Vehiculo> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });
    }

    private void getselector()
    {
        Intent intent = getIntent();
        selector =  Objects.requireNonNull(intent.getExtras()).getString("selector");
        swmodo   = intent.getExtras().getBoolean("swmodo");

        if (!swmodo)
        {
            btn_busveh.setEnabled(false);
            btn_savep.setEnabled(true);
            vehiculo = (Vehiculo) intent.getExtras().getSerializable("vehiculo");

            assert vehiculo != null;
            edt_dnicli.setText(vehiculo.getCli_dni());
            edt_marca.setText(vehiculo.getVeh_marca());
            edt_modelo.setText(vehiculo.getVeh_modelo());
            edt_anofab.setText(vehiculo.getVeh_afab());
            edt_placasp.setText(vehiculo.getVeh_placa());

            edt_modelo.setEnabled(true);
            edt_anofab.setEnabled(true);
            edt_marca.setEnabled(true);
            edt_dnicli.setEnabled(true);
            edt_placasp.setEnabled(false);

        }

    }

    private void eliminarVehiculo()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Vehiculo> call = request.deleteVehiculo(edt_placasp.getText().toString());

        call.enqueue(new Callback<Vehiculo>()
        {
            @Override
            public void onResponse(@NonNull Call<Vehiculo> call, @NonNull Response<Vehiculo> response)
            {
                showmessage("Vehiculo Eliminado");
                finish();
            }

            @Override
            public void onFailure(@NonNull Call<Vehiculo> call, @NonNull Throwable t)
            {
                t.printStackTrace();
            }

        });
    }

    public void pedirCliente()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Cliente> call = request.getJSONClienteid(edt_dnicli.getText().toString());

        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(@NonNull Call<Cliente> call, @NonNull Response<Cliente> response) {

                if (response.code() == 200)//VERIFICAMOS RECEPCION CORRECTA DE GET
                {

                    Vehiculo vehiculo = new Vehiculo();

                    vehiculo.setCli_dni(edt_dnicli.getText().toString());
                    vehiculo.setVeh_afab(edt_anofab.getText().toString());
                    vehiculo.setVeh_marca(edt_marca.getText().toString());
                    vehiculo.setVeh_modelo(edt_modelo.getText().toString());
                    vehiculo.setVeh_placa(edt_placasp.getText().toString());

                    updateVehiculo(vehiculo);


                }
                else
                {
                    if(response.code() == 500)
                    {
                        showmessage("DNI NO REGISTRADO");
                        edt_dnicli.requestFocus();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cliente> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });
    }

}

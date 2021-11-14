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
import android.widget.TextView;
import android.widget.Toast;


import com.msedek.msedek.testslim.Interfaz.iSendCliente;
import com.msedek.msedek.testslim.Models.Empleado;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText edt_dni;
    EditText edt_pass;
    Button btn_ingre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edt_dni    = findViewById(R.id.edt_dni);
        edt_pass   = findViewById(R.id.edt_pass);
        btn_ingre  = findViewById(R.id.btn_ingre);


        btn_ingre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String test = edt_dni.getText().toString().trim();

                if(test.length() < 8)
                {
                    showmessage("DNI INVALIDO");
                }
                else
                {
                    test = edt_pass.getText().toString().trim();

                    if (test.length() < 8) {
                        showmessage("PASSWORD DEBE SER DE 8 CARACTERES");
                    }
                    else
                    {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://www.tecfomatica.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        iSendCliente request = retrofit.create(iSendCliente.class);

                        Call<Empleado> call = request.getJSONEmpleadoid(edt_dni.getText().toString()); //VERIFICAR SI CORREO EXISTE

                        call.enqueue(new Callback<Empleado>() {
                            @Override
                            public void onResponse(@NonNull Call<Empleado> call, @NonNull Response<Empleado> response) {

                                if (response.code() == 200)//VERIFICAMOS RECEPCION CORRECTA DE GET
                                {
                                    Empleado empleado = response.body();

                                    assert empleado != null;
                                    if(empleado.getEmp_pass().equals(edt_pass.getText().toString()))
                                    {
                                        Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                                        intent.putExtra("data", empleado);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        showmessage("PASSWORD NO VALIDO");
                                        edt_pass.requestFocus();
                                    }

                                }
                                else
                                {
                                    if(response.code() == 500)
                                    {
                                        showmessage("DNI NO REGISTRADO");
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Empleado> call, @NonNull Throwable t) {

                                t.printStackTrace();

                            }
                        });
                    }
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
        }, 1000);

    }

}

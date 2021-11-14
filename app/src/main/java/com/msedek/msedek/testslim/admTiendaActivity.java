package com.msedek.msedek.testslim;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.msedek.msedek.testslim.Interfaz.iSendCliente;
import com.msedek.msedek.testslim.Models.Cargo;
import com.msedek.msedek.testslim.Models.Empleado;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class admTiendaActivity extends AppCompatActivity {


    Empleado empleado;

    TextView cargo;

    Button btn_locales;
    Button btn_empleados;
    Button btn_cargos;
    Button btn_servicios;
    Button btn_productos;
    Button btn_categoria;
    Button btn_estados;
    Button btn_atrastienda;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_tienda);

        btn_locales     = findViewById(R.id.btn_locales);
        btn_empleados   = findViewById(R.id.btn_empleados);
        btn_cargos      = findViewById(R.id.btn_cargos);
        btn_servicios   = findViewById(R.id.btn_servicios);
        btn_productos   = findViewById(R.id.btn_productos);
        btn_categoria   = findViewById(R.id.btn_categoria);
        btn_estados     = findViewById(R.id.btn_estados);
        btn_atrastienda = findViewById(R.id.btn_atrastienda);
        cargo           = findViewById(R.id.txt_cargo);


        getEmpleado();


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
                    ArrayList<Cargo> carr = response.body();
                    boolean activador = false;

                    for (Cargo ca : carr)
                    {
                        if (ca.getCar_id() == empleado.getCar_id())
                        {
                            cargo.setText(ca.getCargo());
                            activador = true;
                            break;
                        }

                    }

                    if(activador)
                    {




                        if (cargo.getText().toString().equals("Gerente")) //HABILITAR FUNCIONES DE GERENTE
                        {

                            btn_locales.setEnabled(true);
                            btn_empleados.setEnabled(true);
                            btn_cargos.setEnabled(true);
                            btn_categoria.setEnabled(true);
                            btn_productos.setEnabled(true);
                            btn_servicios.setEnabled(true);
                            btn_estados.setEnabled(true);

                        }
                        else
                        {
                            if(cargo.getText().toString().equals("Recepcionista"))
                            {


                                btn_empleados.setEnabled(false);
                                btn_cargos.setEnabled(false);
                                btn_categoria.setEnabled(false);
                                btn_productos.setEnabled(false);


                                btn_locales.setEnabled(true);
                                btn_servicios.setEnabled(true);
                                btn_estados.setEnabled(true);
                            }
                            else
                            {
                                if(cargo.getText().toString().equals("Vendedor"))
                                {

                                    btn_locales.setEnabled(false);
                                    btn_empleados.setEnabled(false);
                                    btn_cargos.setEnabled(false);
                                    btn_servicios.setEnabled(false);

                                    btn_categoria.setEnabled(true);
                                    btn_productos.setEnabled(true);
                                    btn_estados.setEnabled(false);
                                }
                            }

                        }
                    }


                }
                else
                {
                    showmessage("NO SE PUDO VERIFICAR EL CARGO");
                }

            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Cargo>> call, @NonNull Throwable t)
            {

                t.printStackTrace();

            }
        });



        btn_atrastienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_locales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PreGestionActivity.class);
                intent.putExtra("selector", "locales");
                startActivity(intent);

            }
        });

        btn_empleados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PreGestionActivity.class);
                intent.putExtra("selector", "empleados");
                startActivity(intent);

            }
        });

        btn_cargos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PreGestionActivity.class);
                intent.putExtra("selector", "cargos");
                startActivity(intent);

            }
        });

        btn_categoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PreGestionActivity.class);
                intent.putExtra("selector", "categorias");
                startActivity(intent);

            }
        });

        btn_productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PreGestionActivity.class);
                intent.putExtra("selector", "productos");
                startActivity(intent);

            }
        });

        btn_servicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PreGestionActivity.class);
                intent.putExtra("selector", "servicios");
                startActivity(intent);

            }
        });

        btn_estados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PreGestionActivity.class);
                intent.putExtra("selector", "estados");
                startActivity(intent);

            }
        });

    }

    private void getEmpleado()
    {
        Intent intent = getIntent();
        empleado = (Empleado) Objects.requireNonNull(intent.getExtras()).getSerializable("data");
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

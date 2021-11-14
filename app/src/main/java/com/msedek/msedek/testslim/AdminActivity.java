package com.msedek.msedek.testslim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.msedek.msedek.testslim.Models.Empleado;

import java.util.Objects;


public class AdminActivity extends AppCompatActivity {

    Button bnt_cliente;
    Button btn_administrar;
    Button btn_citas;
    Button btn_ots;
    Button btn_salprin;

    Empleado empleado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        bnt_cliente = findViewById(R.id.btn_cliente);
        btn_administrar = findViewById(R.id.btn_administrar);
        btn_citas = findViewById(R.id.btn_citas);
        btn_ots   = findViewById(R.id.btn_ots);
        btn_salprin = findViewById(R.id.btn_salprin);


        getdata();

        btn_salprin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        bnt_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AdmCliActivity.class);
                intent.putExtra("data", empleado);
                startActivity(intent);

            }
        });


        btn_administrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), admTiendaActivity.class);
                intent.putExtra("data", empleado);
                startActivity(intent);

            }
        });


        btn_citas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ReservasActivity.class);
                intent.putExtra("data", empleado);
                startActivity(intent);

            }
        });


        btn_ots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AdminOrderActivity.class);
                intent.putExtra("config", "boton");
                startActivity(intent);

            }
        });




    }

    private void getdata()
    {
        Intent intent = getIntent();
        empleado = (Empleado) Objects.requireNonNull(intent.getExtras()).getSerializable("data");
    }
}

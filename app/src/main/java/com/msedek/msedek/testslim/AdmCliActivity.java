package com.msedek.msedek.testslim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.View;
import android.widget.Button;


import com.msedek.msedek.testslim.Models.Empleado;

import java.util.Objects;

public class AdmCliActivity extends AppCompatActivity {

    Empleado empleado;

    Button btn_addCli;
    Button btn_ediCli;
    Button btn_listCli;
    Button btn_eliCli;
    Button btn_addVe;
    Button btn_ediVe;
    Button btn_lisVe;
    Button btn_eliVe;
    Button btn_saladm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_cli);

        btn_addCli = findViewById(R.id.btn_addCli);
        btn_ediCli = findViewById(R.id.bnt_ediCli);
        btn_listCli = findViewById(R.id.bnt_listCli);
        btn_eliCli = findViewById(R.id.bnt_delCli);
        btn_addVe = findViewById(R.id.btn_addVe);
        btn_ediVe = findViewById(R.id.btn_ediVe);
        btn_lisVe = findViewById(R.id.btn_listVe);
        btn_eliVe = findViewById(R.id.bnt_eliVe);
        btn_saladm = findViewById(R.id.btn_saladm);

        getdata();


        btn_saladm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_addCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AgregarClienteActivity.class);
                startActivity(intent);

            }
        });

        btn_addVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AgregaVehiculoActivity.class);
                startActivity(intent);

            }
        });





        btn_ediCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), editarClienteActivity.class);
                intent.putExtra("selector", "editar");
                intent.putExtra("swmodo",true);
                startActivity(intent);

            }
        });

        btn_ediVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), editarVehiculoActivity.class);
                intent.putExtra("selector", "editar");
                intent.putExtra("swmodo",true);
                startActivity(intent);

            }
        });






        btn_listCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), listClientVehActivity.class);
                intent.putExtra("selector", "clientes");
                startActivity(intent);

            }
        });

        btn_lisVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), listClientVehActivity.class);
                intent.putExtra("selector", "vehiculos");
                startActivity(intent);

            }
        });






        btn_eliCli.setOnClickListener(new View.OnClickListener() {//TODO PENSAR ACA
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), editarClienteActivity.class);
                intent.putExtra("selector", "eliminar");
                intent.putExtra("swmodo",true);
                startActivity(intent);

            }
        });


        btn_eliVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), editarVehiculoActivity.class);//TODO PENSAR QUE HACER
                intent.putExtra("selector", "eliminar");
                intent.putExtra("swmodo",true);
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

package com.msedek.msedek.testslim;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class PreGestionActivity extends AppCompatActivity {

    String setup;

    TextView txt_pregestion;
    Button btn_agregar;
    Button btn_editar;
    Button btn_listar;
    Button btn_eiliminar;
    Button btn_salir;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_gestion);

        txt_pregestion = findViewById(R.id.txt_pregestion);
        btn_agregar    = findViewById(R.id.btn_agregarpre);
        btn_editar     = findViewById(R.id.btn_editarpre);
        btn_listar     = findViewById(R.id.btn_listarpre);
        btn_eiliminar  = findViewById(R.id.btn_eliminarpre);
        btn_salir      = findViewById(R.id.btn_salpre);

        getconfig();

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), MultiAgregarActivity.class);
                intent.putExtra("selector", setup);
                startActivity(intent);
            }
        });

        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(getApplicationContext(), MultiAdminActivity.class);
                intent.putExtra("selector", "editar");
                intent.putExtra("selector2", setup);
                intent.putExtra("config","boton");

                startActivity(intent);
            }

        });

        btn_listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), GestionActivity.class);
                intent.putExtra("selector", setup);
                startActivity(intent);

            }
        });

        btn_eiliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(getApplicationContext(), MultiAdminActivity.class);
                intent.putExtra("selector", "eliminar");
                intent.putExtra("selector2", setup);
                intent.putExtra("config","boton");

                startActivity(intent);

            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void getconfig()
    {
        Intent intent = getIntent();
        setup = Objects.requireNonNull(intent.getExtras()).getString("selector");
        assert setup != null;
        txt_pregestion.setText("ADMINISTRAR " + setup.toUpperCase());
    }

}

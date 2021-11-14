package com.msedek.msedek.testslim;

import android.annotation.SuppressLint;
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


import com.msedek.msedek.testslim.Adapter.RecyclerViewAdapterReser;
import com.msedek.msedek.testslim.Interfaz.iSendCliente;
import com.msedek.msedek.testslim.Models.Cita;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReservasActivity extends AppCompatActivity {

    TextView tx_clid;
    TextView tx_fecha;
    TextView tx_placas;
    TextView tx_idtal;
    TextView tx_ser;

    TextView txt_posres;
    TextView txt_reser;

    Button btn_salir;
    Button btn_elim;
    Button btn_edireser;


    ArrayList<Cita> reservas;




    RecyclerViewAdapterReser adp;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        tx_clid = findViewById(R.id.txt_dnireser);
        tx_fecha = findViewById(R.id.txt_fechareser);
        tx_placas = findViewById(R.id.txt_placareser);
        tx_idtal = findViewById(R.id.txt_idtalreser);
        tx_ser = findViewById(R.id.txt_servreser);

        txt_posres = findViewById(R.id.txt_posres);
        txt_reser = findViewById(R.id.txt_reser);

        btn_salir = findViewById(R.id.btn_atrareser);
        btn_elim = findViewById(R.id.btn_cancelreser);
        btn_edireser = findViewById(R.id.btn_editreser);
        recyclerView = findViewById(R.id.recyclerViewreser);

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btn_edireser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tx_clid.length() == 0)
                {
                    showmessage("SELECCIONE UNA RESERVA");
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), EditReserActivity.class);
                    intent.putExtra("reserva", reservas.get(Integer.parseInt(txt_posres.getText().toString())));
                    startActivity(intent);
                }
            }
        });


        btn_elim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tx_clid.getText().equals(""))
                {
                    showmessage("SELECCIONE UNA RESERVA");
                }
                else
                {
                    int posi = Integer.parseInt(txt_posres.getText().toString());
                    reservas.remove(posi);
                    adp.notifyDataSetChanged();

                    deleteReserva(Integer.parseInt(txt_reser.getText().toString()));

                    showmessage("ELIMINO LA RESERVA");

                    tx_clid.setText("");
                    tx_fecha.setText("");
                    tx_placas.setText("");
                    tx_idtal.setText("");
                    tx_ser.setText("");

                    txt_posres.setText("");
                    txt_reser.setText("");


                }
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

    private void setAdapter(ArrayList<Cita> reser) {

        adp = new RecyclerViewAdapterReser(getApplicationContext(),reser, tx_clid, tx_fecha,
                tx_placas, tx_idtal, tx_ser, txt_posres, txt_reser);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adp);

    }

    private void loadReservas() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<ArrayList<Cita>> call = request.getJSONCitas();

        call.enqueue(new Callback<ArrayList<Cita>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ArrayList<Cita>> call, @NonNull Response<ArrayList<Cita>> response) {

                if (response.code() == 200)
                {

                    for(Cita cit : response.body())
                    {
                        if(cit.getEstado_orden().equals("pendiente"))
                        {
                            reservas.add(cit);
                        }
                    }


                    setAdapter(reservas);

                }
                else
                {
                    showmessage("NO HAY RESERVAS PENDIENTES");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Cita>> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });
    }

    private void deleteReserva(int i) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Cita> call = request.deleteCita(i);

        call.enqueue(new Callback<Cita>()
        {
            @Override
            public void onResponse(@NonNull Call<Cita> call, @NonNull Response<Cita> response)
            {
            }

            @Override
            public void onFailure(@NonNull Call<Cita> call, @NonNull Throwable t)
            {
                t.printStackTrace();
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        reservas = new ArrayList<>();

        recyclerView.setAdapter(null);

        loadReservas();

    }
}
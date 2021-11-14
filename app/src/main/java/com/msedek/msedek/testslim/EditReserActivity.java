package com.msedek.msedek.testslim;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.msedek.msedek.testslim.Interfaz.iSendCliente;
import com.msedek.msedek.testslim.Models.Cita;
import com.msedek.msedek.testslim.Models.Estado;
import com.msedek.msedek.testslim.Models.Servicio;
import com.msedek.msedek.testslim.Models.Taller;
import com.msedek.msedek.testslim.Models.Vehiculo;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditReserActivity extends AppCompatActivity {

    Cita cita;

    TextView txt_dni;
    TextView txt_placas;
    TextView txt_servicio;
    TextView txt_local;
    TextView txt_estado;


    Spinner spinnerPlacas;
    Spinner spinnerServicio;
    Spinner spinnerLocal;
    Spinner spinnerEstado;


    Button btn_guardar;
    Button btn_atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reser);

        txt_dni         = findViewById(R.id.txt_ediredni);
        txt_placas      = findViewById(R.id.txt_edireveh);
        txt_servicio    = findViewById(R.id.txt_edireser);
        txt_local       = findViewById(R.id.txt_edirestal);
        txt_estado      = findViewById(R.id.txt_edireest);

        spinnerPlacas   = findViewById(R.id.spinnerVeh);
        spinnerServicio = findViewById(R.id.spinnerServ);
        spinnerLocal    = findViewById(R.id.spinnerLoc);
        spinnerEstado   = findViewById(R.id.spinnerEst);

        btn_guardar     = findViewById(R.id.btn_edireser);
        btn_atras       = findViewById(R.id.btn_atrareser);


        getdata();


        getAllVehiculos(cita.getCli_dni());
        getAllServicios();
        getAllTalleres();
        getAllEstados();



        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateReserva(cita);


            }
        });
    }

    private void getdata() {
        Intent intent = getIntent();
        cita = (Cita) Objects.requireNonNull(intent.getExtras()).getSerializable("reserva");

        txt_dni.setText(cita.getCli_dni());
        txt_placas.setText(cita.getVeh_placa());
        txt_servicio.setText(cita.getServicio());
        txt_local.setText(cita.getTal_id());
        txt_estado.setText(cita.getEstado_orden());

    }

    private void getAllVehiculos(final String cli_dni) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<ArrayList<Vehiculo>> call = request.getJSONVehiculos();

        call.enqueue(new Callback<ArrayList<Vehiculo>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Vehiculo>> call, @NonNull Response<ArrayList<Vehiculo>> response)
            {

                if (response.code() == 200) {

                    ArrayList<Vehiculo> vehllega = response.body();
                    ArrayList<String> veh = new ArrayList<>();

                    for (Vehiculo vehiculo : vehllega) {

                        if(vehiculo.getCli_dni().equals(cli_dni))
                        {
                            veh.add(vehiculo.getVeh_placa());
                        }

                    }

                    if (veh.size() > 0) {

                        String[] varray = new String[veh.size()];
                        varray = veh.toArray(varray);

                        ArrayAdapter<String> adapterVehiculo = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_row, varray);
                        spinnerPlacas.setAdapter(adapterVehiculo);

                        spinnerPlacas.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                            protected Adapter initializedAdapter = null;

                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                // Always ignore the initial selection performed after setAdapter
                                if( initializedAdapter !=parent.getAdapter() ) {
                                    initializedAdapter = parent.getAdapter();
                                    return;
                                }

                                txt_placas.setText(spinnerPlacas.getSelectedItem().toString());
                                cita.setVeh_placa(spinnerPlacas.getSelectedItem().toString());


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }
                    else
                    {
                        showmessage("NO HAY VEHICULOS REGISTRADOS");
                    }

                }


            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Vehiculo>> call, @NonNull Throwable t)
            {

                t.printStackTrace();

            }
        });

    }

    private void getAllServicios() {


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

                if (response.code() == 200) {

                    ArrayList<Servicio> serllega = response.body();
                    ArrayList<String> ser = new ArrayList<>();

                    for (Servicio servicio : serllega) {

                        ser.add(servicio.getSer_nombre());
                    }

                    if (ser.size() > 0) {

                        String[] varray = new String[ser.size()];
                        varray = ser.toArray(varray);

                        ArrayAdapter<String> adapterServicio = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_row, varray);
                        spinnerServicio.setAdapter(adapterServicio);

                        spinnerServicio.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                            protected Adapter initializedAdapter = null;

                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                // Always ignore the initial selection performed after setAdapter
                                if( initializedAdapter !=parent.getAdapter() ) {
                                    initializedAdapter = parent.getAdapter();
                                    return;
                                }

                                txt_servicio.setText(spinnerServicio.getSelectedItem().toString());
                                cita.setServicio(spinnerServicio.getSelectedItem().toString());


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    }
                    else
                    {
                        showmessage("NO HAY SERVICIOS REGISTRADOS");
                    }

                }


            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Servicio>> call, @NonNull Throwable t)
            {

                t.printStackTrace();

            }
        });

    }

    private void getAllTalleres() {


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

                if (response.code() == 200) {

                    ArrayList<Taller> tarllega = response.body();
                    ArrayList<String> tall = new ArrayList<>();

                    for (Taller taller : tarllega) {

                        tall.add(taller.getTal_id());
                    }

                    if (tall.size() > 0) {

                        String[] varray = new String[tall.size()];
                        varray = tall.toArray(varray);

                        ArrayAdapter<String> adapterTaller = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_row, varray);
                        spinnerLocal.setAdapter(adapterTaller);

                        spinnerLocal.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                            protected Adapter initializedAdapter = null;

                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                // Always ignore the initial selection performed after setAdapter
                                if( initializedAdapter !=parent.getAdapter() ) {
                                    initializedAdapter = parent.getAdapter();
                                    return;
                                }

                                txt_local.setText(spinnerLocal.getSelectedItem().toString());
                                cita.setTal_id(spinnerLocal.getSelectedItem().toString());


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }
                    else
                    {
                        showmessage("NO HAY LOCALES REGISTRADOS");
                    }

                }


            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Taller>> call, @NonNull Throwable t)
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
                        spinnerEstado.setAdapter(adapterEstado);

                        spinnerEstado.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                            protected Adapter initializedAdapter = null;

                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                // Always ignore the initial selection performed after setAdapter
                                if( initializedAdapter !=parent.getAdapter() ) {
                                    initializedAdapter = parent.getAdapter();
                                    return;
                                }

                                txt_estado.setText(spinnerEstado.getSelectedItem().toString());
                                cita.setEstado_orden(spinnerEstado.getSelectedItem().toString());


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

    private void updateReserva(Cita cita) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Cita> call = request.updateCita(cita.getC_id() , cita);

        call.enqueue(new Callback<Cita>()
        {
            @Override
            public void onResponse(@NonNull Call<Cita> call, @NonNull Response<Cita> response)
            {

                if (response.code() == 200)
                {
                    showmessage("ACTUALIZACION EXITOSA");

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
            public void onFailure(@NonNull Call<Cita> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });

    }

    private void showmessage(String mensaje) {
        final Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
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

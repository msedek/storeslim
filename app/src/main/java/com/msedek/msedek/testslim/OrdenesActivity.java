package com.msedek.msedek.testslim;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.msedek.msedek.testslim.Interfaz.iSendCliente;
import com.msedek.msedek.testslim.Models.Cliente;
import com.msedek.msedek.testslim.Models.Empleado;
import com.msedek.msedek.testslim.Models.Estado;
import com.msedek.msedek.testslim.Models.Orden;
import com.msedek.msedek.testslim.Models.Producto;
import com.msedek.msedek.testslim.Models.Prorder;
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

public class OrdenesActivity extends AppCompatActivity {

    String config;

    Spinner spinnerCli;
    Spinner spinnerVeh;
    Spinner spinnerSer;
    Spinner spinnerEmp;
    Spinner spinnerLoc;
    Spinner spinnerProd;
    Spinner spinnerEst;

    Button btn_addpro;
    Button btn_genOt;
    Button btn_canOt;

    LinearLayout lyo_propadre;

    boolean isFlagCliente;
    boolean isFlagVehiculo;
    boolean isFlagServicio;
    boolean isFlagEmpleado;
    boolean isFlagTaller;
    boolean isFlagProducto;
    boolean isFlagEstado;
    boolean isFlaglista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes);


        spinnerCli   = findViewById(R.id.spinnercli);
        spinnerVeh   = findViewById(R.id.spinnerve);
        spinnerSer   = findViewById(R.id.spinnerser);
        spinnerEmp   = findViewById(R.id.spinneremp);
        spinnerLoc   = findViewById(R.id.spinnerlocal);
        spinnerProd  = findViewById(R.id.spinnerpro);
        spinnerEst   = findViewById(R.id.spinnerest);

        btn_addpro   = findViewById(R.id.btn_addpro);
        btn_genOt    = findViewById(R.id.btn_genot);
        btn_canOt    = findViewById(R.id.btn_canot);

        lyo_propadre = findViewById(R.id.lyo_propadre);


        isFlagCliente  = false;
        isFlagVehiculo = false;
        isFlagServicio = false;
        isFlagEmpleado = false;
        isFlagTaller   = false;
        isFlagProducto = false;
        isFlagEstado   = false;
        isFlaglista   = false;




        btn_canOt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        getdata();

        if(config.equals("boton"))
        {
            getAllClientes();
            getAllservicios();
            getAllempleados();
            getAlltalleres();
            getAllproductos();
            getAllestados();
        }
        else
        {
            //TODO DESDE LA RESERVA
        }



        spinnerCli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String cli_dni = spinnerCli.getSelectedItem().toString();
                spinnerVeh.setAdapter(null);
                Cliente cliente = new Cliente();
                cliente.setCli_dni(cli_dni);
                getAllVehiculos(cliente);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_addpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(spinnerProd.getAdapter().getCount() > 0)
                {
                    TextView textView = new TextView(getApplicationContext());

                    textView.setText(spinnerProd.getSelectedItem().toString());

                    textView.setTextColor(Color.BLACK);

                    lyo_propadre.addView(textView);

                    isFlaglista = true;

                }
                else
                {
                    showmessage("NO HAY PRODUCTOS EN EL CATALOGO");
                }

            }
        });

        btn_genOt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isFlagCliente)
                {
                    showmessage("SELECCIONE UN CLIENTE");
                }
                else
                {
                    if(!isFlagVehiculo)
                    {
                        showmessage("SELECCIONE VEHICULO DE CLIENTE");
                    }
                    else
                    {
                        if(!isFlagServicio)
                        {
                            showmessage("SELECCIONE UN SERVICIO");
                        }
                        else
                        {
                            if(!isFlagEmpleado)
                            {
                                showmessage("SELECCIONE UN EMPLEADO");
                            }
                            else
                            {
                                if(!isFlagTaller)
                                {
                                    showmessage("SELECCIONE LOCAL");

                                }
                                else
                                {
                                    if(!isFlagProducto)
                                    {
                                        showmessage("SELECCIONE PRODUCTO");
                                    }
                                    else
                                    {
                                        if(!isFlaglista)
                                        {
                                            showmessage("AGREGUE PRODUCTOS A LA LISTA");
                                        }
                                        else
                                        {
                                            if (!isFlagEstado)
                                            {
                                                showmessage("SELECCIONE UN ESTADO");
                                            }
                                            else
                                            {

                                                Orden orden = new Orden();
                                                orden.setCli_dni(spinnerCli.getSelectedItem().toString());
                                                orden.setEmp_dni(spinnerEmp.getSelectedItem().toString());
                                                orden.setTal_id(spinnerLoc.getSelectedItem().toString());
                                                orden.setVeh_placa(spinnerVeh.getSelectedItem().toString());
                                                orden.setEstado_orden(spinnerEst.getSelectedItem().toString());
                                                orden.setServicio(spinnerSer.getSelectedItem().toString());

                                                sendOrden(orden);

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private void getdata() {
        Intent intent = getIntent();
        config = intent.getExtras().getString("config");
    }

    private void showmessage(String mensaje) {
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

    private void getAllClientes() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<ArrayList<Cliente>> call = request.getJSONClientes();

        call.enqueue(new Callback<ArrayList<Cliente>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Cliente>> call, @NonNull Response<ArrayList<Cliente>> response) {


                if (response.code() == 200) {

                    ArrayList<Cliente> clillega = response.body();
                    ArrayList<String> clidni = new ArrayList<>();

                    for (Cliente cli : clillega) {

                        clidni.add(cli.getCli_dni());
                    }

                    if (clidni.size() > 0) {

                        String[] varray = new String[clidni.size()];
                        varray = clidni.toArray(varray);

                        ArrayAdapter<String> adapterCliente = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_row, varray);
                        spinnerCli.setAdapter(adapterCliente);
                        spinnerCli.setSelection(0);
                        isFlagCliente = true;

                        getAllVehiculos(clillega.get(0));

                    }
                    else
                    {
                        showmessage("NO HAY CLIENTES REGISTRADOS");
                    }

                }
            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Cliente>> call, @NonNull Throwable t)
            {

                t.printStackTrace();

            }
        });

    }

    public void getAllVehiculos(final Cliente clillega) {

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

                    ArrayList<Vehiculo> vehiculoll = response.body();
                    ArrayList<String> vehiculos = new ArrayList<>();

                    for (Vehiculo vec : vehiculoll) {
                        if (vec.getCli_dni().equals(clillega.getCli_dni())) {
                            vehiculos.add(vec.getVeh_placa());
                        }
                    }

                    if (vehiculos.size() > 0) {

                        String[] varray = new String[vehiculos.size()];
                        varray = vehiculos.toArray(varray);

                        ArrayAdapter<String> adapterVehiculo = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_row, varray);
                        spinnerVeh.setAdapter(adapterVehiculo);
                        spinnerVeh.setSelection(0);
                        isFlagVehiculo = true;


                    } else {
                        showmessage("CLIENTE NO TIENE VEHICULOS REGISTRADOS");
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

    private void getAllservicios() {


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
                    ArrayList<String> serv = new ArrayList<>();

                    for (Servicio ser : serllega) {

                        serv.add(ser.getSer_nombre());
                    }

                    if (serv.size() > 0) {

                        String[] varray = new String[serv.size()];
                        varray = serv.toArray(varray);

                        ArrayAdapter<String> adapterServicio = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_row, varray);
                        spinnerSer.setAdapter(adapterServicio);
                        spinnerSer.setSelection(0);
                        isFlagServicio = true;

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

                if (response.code() == 200) {

                    ArrayList<Empleado> empllega = response.body();
                    ArrayList<String> emple = new ArrayList<>();

                    for (Empleado empleado : empllega) {

                        emple.add(empleado.getEmp_dni());
                    }

                    if (emple.size() > 0) {

                        String[] varray = new String[emple.size()];
                        varray = emple.toArray(varray);

                        ArrayAdapter<String> adapterEmpleado = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_row, varray);
                        spinnerEmp.setAdapter(adapterEmpleado);
                        spinnerEmp.setSelection(0);
                        isFlagEmpleado = true;

                    }
                    else
                    {
                        showmessage("NO HAY EMPLEADOS REGISTRADOS");
                    }

                }


            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Empleado>> call, @NonNull Throwable t)
            {

                t.printStackTrace();

            }
        });

    }

    private void getAlltalleres() {

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

                    ArrayList<Taller> talllega = response.body();
                    ArrayList<String> tal = new ArrayList<>();

                    for (Taller taller : talllega) {

                        tal.add(taller.getTal_id());
                    }

                    if (tal.size() > 0) {

                        String[] varray = new String[tal.size()];
                        varray = tal.toArray(varray);

                        ArrayAdapter<String> adapterTaller = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_row, varray);
                        spinnerLoc.setAdapter(adapterTaller);
                        spinnerLoc.setSelection(0);
                        isFlagTaller = true;

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


                if (response.code() == 200) {

                    ArrayList<Producto> prollega = response.body();
                    ArrayList<String> prod = new ArrayList<>();

                    for (Producto producto : prollega) {

                        prod.add(producto.getPro_nombre());
                    }

                    if (prod.size() > 0) {

                        String[] varray = new String[prod.size()];
                        varray = prod.toArray(varray);

                        ArrayAdapter<String> adapterProducto = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_row, varray);
                        spinnerProd.setAdapter(adapterProducto);
                        spinnerProd.setSelection(0);
                        isFlagProducto = true;

                    }
                    else
                    {
                        showmessage("NO HAY PRODUCTOS REGISTRADOS");
                    }

                }

            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Producto>> call, @NonNull Throwable t)
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
                        spinnerEst.setAdapter(adapterEstado);
                        spinnerEst.setSelection(0);
                        isFlagEstado = true;

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

    public void sendOrden(Orden orden) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Orden> call = request.addOrden(orden);

        call.enqueue(new Callback<Orden>()
        {
            @Override
            public void onResponse(@NonNull Call<Orden> call, @NonNull Response<Orden> response)
            {

                if (response.code() == 200)
                {


                    for (int i = 0; i < lyo_propadre.getChildCount(); i++) {

                        View view = lyo_propadre.getChildAt(i);

                        TextView textView =  (TextView) view;

                        Prorder prorder = new Prorder();

                        prorder.setOr_id(response.body().getOt_id() + "");
                        prorder.setPro_nombre(textView.getText().toString());

                        sendProrder(prorder);


                    }

                    showmessage("ORDEN REGISTRADA EXITOSAMENTE");


                    finish();
                }
                else
                {
                    showmessage("OCURRIO UN ERROR AL REGISTRAR LA ORDEN");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Orden> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });
    }

    public void sendProrder(Prorder prorder) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Prorder> call = request.addProrder(prorder);

        call.enqueue(new Callback<Prorder>()
        {
            @Override
            public void onResponse(@NonNull Call<Prorder> call, @NonNull Response<Prorder> response)
            {

                if (response.code() == 200)
                {

                }
                else
                {
                    showmessage("OCURRIO UN ERROR AL REGISTRAR PRODUCTOS");
                    lyo_propadre.removeAllViews();
                    isFlaglista = false;
                }
            }

            @Override
            public void onFailure(@NonNull Call<Prorder> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });
    }

}

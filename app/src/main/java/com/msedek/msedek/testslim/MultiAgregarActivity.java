package com.msedek.msedek.testslim;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.msedek.msedek.testslim.Interfaz.iSendCliente;
import com.msedek.msedek.testslim.Models.Cargo;
import com.msedek.msedek.testslim.Models.Categoria;
import com.msedek.msedek.testslim.Models.Empleado;
import com.msedek.msedek.testslim.Models.Estado;
import com.msedek.msedek.testslim.Models.Producto;
import com.msedek.msedek.testslim.Models.Servicio;
import com.msedek.msedek.testslim.Models.Taller;


import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MultiAgregarActivity extends AppCompatActivity {

    String selector2;
    TextView txt_gestion;

    Button btn_salir;
    Button btn_agregar;

    Spinner spinner;

    EditText txt_prop1;
    EditText txt_prop2;
    EditText txt_prop3;
    EditText txt_prop4;
    EditText txt_proex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_agregar);


        txt_gestion = findViewById(R.id.txt_actividadagre);
        txt_prop1   = findViewById(R.id.txt_prop1agre);
        txt_prop2   = findViewById(R.id.txt_prop2agre);
        txt_proex   = findViewById(R.id.txt_propexagre);
        txt_prop3   = findViewById(R.id.txt_prop3agre);
        txt_prop4   = findViewById(R.id.txt_prop4agre);
        btn_salir   = findViewById(R.id.btn_multisaliragre);
        btn_agregar = findViewById(R.id.btn_prop1agre);
        spinner     = findViewById(R.id.spinnermultagre);

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getconfig();

        seteo();


        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String test;

                //VALIDACION PARA LOCALES

                if (selector2.equals("locales"))
                {
                    test = txt_prop1.getText().toString().trim();

                    if (test.length() < 8)
                    {
                        showmessage("CODIGO DE LOCAL 8 CARACTERES");
                    }
                    else
                    {
                        test = txt_prop2.getText().toString().trim();

                        if (test.length() == 0)
                        {
                            showmessage("ESCRIBA NOMBRE DEL LOCAL");
                        }
                        else
                        {
                            test = txt_proex.getText().toString().trim();

                            if (test.length() == 0)
                            {
                                showmessage("ESCRIBA DIRECCION DEL LOCAL");
                            }
                            else
                            {
                                Taller talmandar = new Taller();

                                talmandar.setTal_id(txt_prop1.getText().toString());
                                talmandar.setTal_nombre(txt_prop2.getText().toString());
                                talmandar.setTal_direccion(txt_proex.getText().toString());

                                sendTaller(talmandar);
                            }
                        }
                    }
                }


                //VALIDACION PARA EMPLEADOS

                if(selector2.equals("empleados"))
                {
                    test = txt_prop1.getText().toString().trim();

                    if(test.length() < 8)
                    {
                        showmessage("DNI NO VALIDO");
                    }
                    else
                    {
                        test = txt_prop2.getText().toString().trim();

                        if (test.length() == 0)
                        {
                            showmessage("ESCRIBA NOMBRE DEL EMPLEADO");
                        }
                        else
                        {
                            test = txt_proex.getText().toString().trim();

                            if(test.length() == 0)
                            {
                                showmessage("SELECCIONE CARGO");
                            }
                            else
                            {
                                test = txt_prop3.getText().toString().trim();

                                if (test.length() == 0)
                                {
                                    showmessage("ESCRIBA PASSWORD");
                                }
                                else
                                {
                                    test = txt_prop4.getText().toString().trim();

                                    if(test.length() == 0)
                                    {
                                        showmessage("ASIGNE LOCAL DE TRABAJO");
                                    }
                                    else
                                    {
                                        Empleado emplemandar = new Empleado();

                                        emplemandar.setEmp_dni(txt_prop1.getText().toString());
                                        emplemandar.setEmp_nombre(txt_prop2.getText().toString());
                                        emplemandar.setEmp_pass(txt_prop3.getText().toString());
                                        emplemandar.setTal_id(txt_prop4.getText().toString());

                                        String str = txt_proex.getText().toString();
                                        String[] splited = str.split("\\s+");

                                        emplemandar.setCar_id(Integer.parseInt(splited[0]));

                                        sendEmpleado(emplemandar);

                                    }
                                }
                            }
                        }
                    }
                }

                //VALIDACION PARA CARGOS

                if(selector2.equals("cargos"))
                {
                    test = txt_prop2.getText().toString().trim();

                    if(test.length() == 0)
                    {
                        showmessage("ESCRIBA CARGO");
                    }
                    else
                    {

                        Cargo carmanda =  new Cargo();
                        carmanda.setCargo(txt_prop2.getText().toString());


                        sendCargo(carmanda);


                    }
                }

                //VALIDACION PARA CATEGORIAS

                if(selector2.equals("categorias"))
                {
                    test = txt_prop2.getText().toString().trim();

                    if(test.length() == 0)
                    {
                        showmessage("ESCRIBA CATEGORIA");
                    }
                    else
                    {

                        Categoria cateman = new Categoria();

                        cateman.setCat_nombre(txt_prop2.getText().toString());


                        sendCategoria(cateman);


                    }
                }

                //VALIDACION PARA PRODUCTOS

                if(selector2.equals("productos"))
                {
                    test = txt_prop2.getText().toString().trim();

                    if(test.length() == 0)
                    {
                        showmessage("ESCRIBA NOMBRE DEL PRODUCTO");
                    }
                    else
                    {
                        test = txt_proex.getText().toString().trim();

                        if(test.length() == 0)
                        {
                            showmessage("ESCRIBA PRECIO");
                        }
                        else
                        {
                            test = txt_prop3.getText().toString().trim();

                            if(test.length() == 0)
                            {
                                showmessage("ESCRIBA CANTIDAD");
                            }
                            else
                            {

                                test = txt_prop4.getText().toString().trim();

                                if(test.length() == 0)
                                {
                                    showmessage("ELIJA CATEGORIA DEL PRODUCTO");
                                }
                                else
                                {

                                    Producto prodmandar = new Producto();

                                    prodmandar.setPro_nombre(txt_prop2.getText().toString());
                                    prodmandar.setPro_precio(txt_proex.getText().toString());
                                    prodmandar.setPro_cantidad(txt_prop3.getText().toString());

                                    String str = txt_prop4.getText().toString();
                                    String[] splited = str.split("\\s+");

                                    prodmandar.setCat_id(splited[0]);

                                    sendProducto(prodmandar);
                                }
                            }
                        }
                    }
                }

                //VALIDACION PARA SERVICIOS

                if(selector2.equals("servicios"))
                {
                    test = txt_prop2.getText().toString().trim();

                    if(test.length() == 0)
                    {
                        showmessage("ESCRIBA NOMBRE DEL SERVICIO");
                    }
                    else
                    {
                        test = txt_proex.getText().toString().trim();

                        if(test.length() == 0)
                        {
                            showmessage("ESCRIBA DESCRIPCION");
                        }
                        else
                        {
                            test = txt_prop4.getText().toString().trim();

                            if(test.length() == 0)
                            {
                                showmessage("SELECCIONE ESTADO");
                            }
                            else
                            {
                                Servicio servenv = new Servicio();

                                servenv.setSer_nombre(txt_prop2.getText().toString());
                                servenv.setSer_descri(txt_proex.getText().toString());


                                String str = txt_prop4.getText().toString();
                                String[] splited = str.split("\\s+");

                                servenv.setEst_id(splited[0]);

                                sendServicio(servenv);
                            }


                        }

                    }
                }

                //VALIDACION PARA ESTADOS

                if(selector2.equals("estados"))
                {
                    test = txt_prop2.getText().toString().trim();

                    if(test.length() == 0)
                    {
                        showmessage("ESCRIBA ESTADO");
                    }
                    else
                    {

                        Estado estenv = new Estado();

                        estenv.setEst_nombre(txt_prop2.getText().toString());

                        sendEstado(estenv);
                    }
                }


            }
        });


    }


    @SuppressLint("SetTextI18n")
    private void getconfig() {
        Intent intent = getIntent();
        selector2 = Objects.requireNonNull(intent.getExtras()).getString("selector");
        assert selector2 != null;
        txt_gestion.setText("AGREGAR " + selector2.toUpperCase());
    }

    @SuppressLint("SetTextI18n")
    private void seteo() {

        switch (selector2.toLowerCase()) // CONFIGURAMOS PRIMERO QUE SE VA VER Y QUE NO BASADO EN EL BOTON
        {
            case "locales":

                txt_prop1.setVisibility(View.VISIBLE);
                txt_prop2.setVisibility(View.VISIBLE);
                txt_proex.setVisibility(View.VISIBLE);
                txt_prop3.setVisibility(View.INVISIBLE);
                txt_prop4.setVisibility(View.INVISIBLE);

                txt_prop1.setHint("ID DEL LOCAL");
                txt_prop2.setHint("NOMBRE DEL LOCAL");
                txt_proex.setHint("DIRECCION DEL LOCAL");

                txt_prop1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});
                txt_prop2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});
                txt_proex.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});

                txt_prop1.setInputType(InputType.TYPE_CLASS_TEXT);
                txt_prop2.setInputType(InputType.TYPE_CLASS_TEXT);
                txt_proex.setInputType(InputType.TYPE_CLASS_TEXT);


                txt_prop1.setEnabled(true);

                txt_prop2.setEnabled(true);
                txt_proex.setEnabled(true);


                break;

            case "empleados":


                txt_prop1.setVisibility(View.VISIBLE);
                txt_prop2.setVisibility(View.VISIBLE);
                txt_proex.setVisibility(View.VISIBLE);
                txt_proex.setFocusable(false);
                txt_proex.setEnabled(true);
                txt_prop3.setVisibility(View.VISIBLE);
                txt_prop4.setVisibility(View.VISIBLE);
                txt_prop4.setFocusable(false);
                txt_prop4.setEnabled(true);


                txt_proex.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        spinner.setAdapter(null);

                        getAllcargos();

                        spinner.setVisibility(View.VISIBLE);
                    }
                });

                txt_prop4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        spinner.setAdapter(null);

                        getAllTalleres();

                        spinner.setVisibility(View.VISIBLE);

                    }
                });


                txt_prop1.setHint("DNI");
                txt_prop2.setHint("NOMBRE Y APELLIDO EMPLEADO");
                txt_proex.setHint("CARGO");
                txt_prop3.setHint("PASSWORD");
                txt_prop4.setHint("LOCAL DE TRABAJO");

                txt_prop1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});
                txt_prop2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(40)});
                txt_proex.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});
                txt_prop3.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});
                txt_prop4.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});

                txt_prop1.setInputType(InputType.TYPE_CLASS_NUMBER);
                txt_prop2.setInputType(InputType.TYPE_CLASS_TEXT);
                txt_proex.setInputType(InputType.TYPE_CLASS_TEXT);
                txt_prop3.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                txt_prop4.setInputType(InputType.TYPE_CLASS_TEXT);



                txt_prop1.setEnabled(true);
                txt_prop2.setEnabled(true);
                txt_prop3.setEnabled(true);



                break;

            case "cargos":

                txt_prop1.setVisibility(View.INVISIBLE);
                txt_prop2.setVisibility(View.VISIBLE);
                txt_proex.setVisibility(View.INVISIBLE);
                txt_prop3.setVisibility(View.INVISIBLE);
                txt_prop4.setVisibility(View.INVISIBLE);

                txt_prop2.setHint("NOMBRE DEL CARGO");

                txt_prop2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});


                txt_prop2.setInputType(InputType.TYPE_CLASS_TEXT);

                txt_prop2.setEnabled(true);

                break;

            case "categorias":

                txt_prop1.setVisibility(View.INVISIBLE);
                txt_prop2.setVisibility(View.VISIBLE);
                txt_proex.setVisibility(View.INVISIBLE);
                txt_prop3.setVisibility(View.INVISIBLE);
                txt_prop4.setVisibility(View.INVISIBLE);

                txt_prop2.setHint("NOMBRE DE LA CATEGORIA");

                txt_prop2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});


                txt_prop2.setInputType(InputType.TYPE_CLASS_TEXT);


                txt_prop2.setEnabled(true);




                break;

            case "productos":

                txt_prop1.setVisibility(View.INVISIBLE);
                txt_prop2.setVisibility(View.VISIBLE);
                txt_proex.setVisibility(View.VISIBLE);
                txt_prop3.setVisibility(View.VISIBLE);
                txt_prop4.setVisibility(View.VISIBLE);
                txt_prop4.setFocusable(false);
                txt_prop4.setEnabled(true);


                txt_prop4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        spinner.setAdapter(null);

                        getAllCategorias();

                        spinner.setVisibility(View.VISIBLE);

                    }
                });

                txt_prop2.setHint("NOMBRE PRODUCTO");
                txt_proex.setHint("PRECIO PRODUCTO");
                txt_prop3.setHint("CANTIDAD PRODUCTO");
                txt_prop4.setHint("CATEGORIA PRODUCTO");

                txt_prop2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});
                txt_proex.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});
                txt_prop3.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});
                txt_prop4.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});


                txt_prop2.setInputType(InputType.TYPE_CLASS_TEXT);
                txt_proex.setInputType(InputType.TYPE_CLASS_NUMBER);
                txt_prop3.setInputType(InputType.TYPE_CLASS_NUMBER);
                txt_prop4.setInputType(InputType.TYPE_CLASS_TEXT);


                txt_prop2.setEnabled(true);
                txt_proex.setEnabled(true);
                txt_prop3.setEnabled(true);

                break;

            case "servicios":

                txt_prop1.setVisibility(View.INVISIBLE);
                txt_prop2.setVisibility(View.VISIBLE);
                txt_proex.setVisibility(View.VISIBLE);
                txt_prop3.setVisibility(View.INVISIBLE);
                txt_prop4.setVisibility(View.VISIBLE);
                txt_prop4.setFocusable(false);
                txt_prop4.setEnabled(true);

                txt_prop4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        spinner.setAdapter(null);

                        getAllEstados();

                        spinner.setVisibility(View.VISIBLE);

                    }
                });

                txt_prop2.setHint("NOMBRE SERVICIO");
                txt_proex.setHint("DESCRIPCION SERVICIO");
                txt_prop4.setHint("ESTADO DEL SERVICIO");

                txt_prop2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});
                txt_proex.setFilters(new InputFilter[] {new InputFilter.LengthFilter(40)});
                txt_prop4.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});

                txt_prop2.setInputType(InputType.TYPE_CLASS_TEXT);
                txt_proex.setInputType(InputType.TYPE_CLASS_TEXT);
                txt_prop4.setInputType(InputType.TYPE_CLASS_TEXT);

                txt_prop2.setEnabled(true);
                txt_proex.setEnabled(true);
                txt_prop3.setEnabled(true);

                break;

            case "estados":


                txt_prop1.setVisibility(View.INVISIBLE);
                txt_prop2.setVisibility(View.VISIBLE);
                txt_proex.setVisibility(View.INVISIBLE);
                txt_prop3.setVisibility(View.INVISIBLE);
                txt_prop4.setVisibility(View.INVISIBLE);

                txt_prop2.setHint("NOMBRE DEL ESTADO");

                txt_prop2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});


                txt_prop2.setInputType(InputType.TYPE_CLASS_TEXT);


                txt_prop2.setEnabled(true);


                break;
        }
    }

    private void sendTaller(Taller talmandar) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Taller> call = request.addTaller(talmandar);

        call.enqueue(new Callback<Taller>()
        {
            @Override
            public void onResponse(@NonNull Call<Taller> call, @NonNull Response<Taller> response)
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
                        showmessage("TALLER YA EXISTE");
                        txt_prop1.requestFocus();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Taller> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });
    }

    private void sendEmpleado(Empleado empleado) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Empleado> call = request.addEmpleado(empleado);

        call.enqueue(new Callback<Empleado>()
        {
            @Override
            public void onResponse(@NonNull Call<Empleado> call, @NonNull Response<Empleado> response)
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
                        showmessage("EMPLEADO YA EXISTE");
                        txt_prop1.requestFocus();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Empleado> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });
    }

    private void sendCargo(Cargo cargo) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Cargo> call = request.addCargo(cargo);

        call.enqueue(new Callback<Cargo>()
        {
            @Override
            public void onResponse(@NonNull Call<Cargo> call, @NonNull Response<Cargo> response)
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
                        showmessage("CARGO YA EXISTE");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cargo> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });
    }

    private void sendCategoria(Categoria categoria) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Categoria> call = request.addCategoria(categoria);

        call.enqueue(new Callback<Categoria>()
        {
            @Override
            public void onResponse(@NonNull Call<Categoria> call, @NonNull Response<Categoria> response)
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
                        showmessage("CATEGORIA YA EXISTE");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Categoria> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });
    }

    private void sendProducto(Producto producto) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Producto> call = request.addProducto(producto);

        call.enqueue(new Callback<Producto>()
        {
            @Override
            public void onResponse(@NonNull Call<Producto> call, @NonNull Response<Producto> response)
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
                        showmessage("PRODUCTO YA EXISTE");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Producto> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });
    }

    private void sendServicio(Servicio servicio) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Servicio> call = request.addServicio(servicio);

        call.enqueue(new Callback<Servicio>()
        {
            @Override
            public void onResponse(@NonNull Call<Servicio> call, @NonNull Response<Servicio> response)
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
                        showmessage("SERVICIO YA EXISTE");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Servicio> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });
    }

    private void sendEstado(Estado estado) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Estado> call = request.addEstado(estado);

        call.enqueue(new Callback<Estado>()
        {
            @Override
            public void onResponse(@NonNull Call<Estado> call, @NonNull Response<Estado> response)
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
                        showmessage("ESTADO YA EXISTE");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Estado> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });
    }



    private void getAllcargos() {


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

                if (response.code() == 200) {

                    ArrayList<Cargo> carllega = response.body();
                    ArrayList<String> carg = new ArrayList<>();

                    for (Cargo cargo : carllega) {

                        carg.add(cargo.getCar_id() + " " + cargo.getCargo());
                    }

                    if (carg.size() > 0) {

                        String[] varray = new String[carg.size()];
                        varray = carg.toArray(varray);

                        ArrayAdapter<String> adapterCargo = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_row, varray);
                        spinner.setAdapter(adapterCargo);
                        spinner.setSelection(0);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                txt_proex.setText(spinner.getSelectedItem().toString());

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    }
                    else
                    {
                        showmessage("NO HAY CARGOS REGISTRADOS");
                    }

                }


            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Cargo>> call, @NonNull Throwable t)
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
                        spinner.setAdapter(adapterTaller);
                        spinner.setSelection(0);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                txt_prop4.setText(spinner.getSelectedItem().toString());

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

    private void getAllCategorias() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<ArrayList<Categoria>> call = request.getJSONCategorias();

        call.enqueue(new Callback<ArrayList<Categoria>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Categoria>> call, @NonNull Response<ArrayList<Categoria>> response)
            {

                if (response.code() == 200) {

                    ArrayList<Categoria> catllega = response.body();
                    ArrayList<String> cate = new ArrayList<>();

                    for (Categoria categoria : catllega) {

                        cate.add(categoria.getCat_id() + " " + categoria.getCat_nombre());
                    }

                    if (cate.size() > 0) {

                        String[] varray = new String[cate.size()];
                        varray = cate.toArray(varray);

                        ArrayAdapter<String> adapterCategoria = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_row, varray);
                        spinner.setAdapter(adapterCategoria);
                        spinner.setSelection(0);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                txt_prop4.setText(spinner.getSelectedItem().toString());

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    }
                    else
                    {
                        showmessage("NO HAY CATEGORIAS REGISTRADAS");
                    }

                }


            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Categoria>> call, @NonNull Throwable t)
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

                        est.add(estado.getEst_id() + " " + estado.getEst_nombre());
                    }

                    if (est.size() > 0) {

                        String[] varray = new String[est.size()];
                        varray = est.toArray(varray);

                        ArrayAdapter<String> adapterEstado = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_row, varray);
                        spinner.setAdapter(adapterEstado);
                        spinner.setSelection(0);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                txt_prop4.setText(spinner.getSelectedItem().toString());

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


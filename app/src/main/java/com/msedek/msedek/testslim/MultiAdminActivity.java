package com.msedek.msedek.testslim;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.msedek.msedek.testslim.Adapter.RecyclerViewAdaptermin;
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


public class MultiAdminActivity extends AppCompatActivity {

    Taller local;
    Empleado empleado;
    Cargo cargo;
    Categoria categoria;
    Producto producto;
    Servicio servicio;
    Estado estado;

    String selector;
    String selector2;
    String config;

    String titu1;
    String titu2;

    TextView txt_actividad;
    EditText txt_prop1;
    EditText txt_prop2;
    EditText txt_prop3;
    EditText txt_prop4;
    EditText txt_proex;
    Button   btn_prop1;
    Button   btn_salir;
    Button   btn_buscar;

    TextView empid;
    TextView talid;

    RecyclerViewAdaptermin adp;
    RecyclerView recyclerView;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_admin);

        txt_actividad = findViewById(R.id.txt_actividad);
        txt_prop1     = findViewById(R.id.txt_prop1agre);
        txt_prop2     = findViewById(R.id.txt_prop2agre);
        txt_prop3     = findViewById(R.id.txt_prop3agre);
        txt_prop4     = findViewById(R.id.txt_prop4agre);
        txt_proex     = findViewById(R.id.txt_propexagre);
        empid         = findViewById(R.id.empid);
        talid         = findViewById(R.id.talid);
        btn_prop1     = findViewById(R.id.btn_prop1agre);
        btn_buscar    = findViewById(R.id.btn_buscmul);
        btn_salir     = findViewById(R.id.btn_multisaliragre);
        recyclerView  = findViewById(R.id.recyclerViewmini);

        recyclerView.setAdapter(null);

        getselector();

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if(config.equals("recycler"))
        {
            seteo();
        }



        //DETECTAR TIPO DE OPERACION

        switch (selector.toLowerCase())
        {
            case "agregar":

                titu2 = "AGREGAR";

                btn_prop1.setText("AGREGAR");



                break;

            case "editar":

                titu2 = "EDITAR";

                btn_prop1.setText("EDITAR");




                break;

            case "eliminar":

                titu2 = "ELIMINAR";

                btn_prop1.setText("ELIMINAR");

                txt_prop2.setEnabled(false);
                txt_prop3.setEnabled(false);
                txt_prop4.setEnabled(false);


                break;
        }





        btn_prop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(btn_prop1.getText().toString().toLowerCase().equals("editar")) //CONFIGURACION PARA EDITAR
                {
                    String test;

                    //VALIDACION PARA LOCALES

                    if(selector2.equals("locales"))
                    {
                        test = txt_prop2.getText().toString().trim();

                        if(test.length() == 0)
                        {
                            showmessage("ESCRIBA NOMBRE DEL LOCAL");
                        }
                        else
                        {
                            test = txt_proex.getText().toString().trim();

                            if(test.length() == 0)
                            {
                                showmessage("ESCRIBA DIRECCION DEL LOCAL");
                            }
                            else
                            {
                                Taller talmandar = new Taller();

                                talmandar.setTal_id(txt_prop1.getText().toString());
                                talmandar.setTal_nombre(txt_prop2.getText().toString());
                                talmandar.setTal_direccion(txt_proex.getText().toString());


                                editarTaller(talmandar);


                            }
                        }
                    }


                    //VALIDACION PARA EMPLEADOS

                    if(selector2.equals("empleados"))
                    {
                        test = txt_prop2.getText().toString().trim();

                        if(test.length() == 0)
                        {
                            showmessage("ESCRIBA NOMBRE DEL EMPLEADO");
                        }
                        else
                        {
                            test = txt_prop3.getText().toString().trim();

                            if(test.length() == 0)
                            {
                                showmessage("ESCRIBA PASSWORD");
                            }
                            else
                            {
                                Empleado emplemandar= new Empleado();

                                emplemandar.setEmp_dni(txt_prop1.getText().toString());
                                emplemandar.setEmp_nombre(txt_prop2.getText().toString());



                                emplemandar.setCar_id(empleado.getCar_id());


                                emplemandar.setEmp_pass(txt_prop3.getText().toString());
                                emplemandar.setTal_id(talid.getText().toString());

                                editarEmpleado(emplemandar);

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

                            carmanda.setCar_id(Integer.parseInt(txt_prop1.getText().toString()));
                            carmanda.setCargo(txt_prop2.getText().toString());


                            editarCargo(carmanda);


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

                            cateman.setCat_id(Integer.parseInt(txt_prop1.getText().toString()));
                            cateman.setCat_nombre(txt_prop2.getText().toString());


                            editarCategoria(cateman);


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
                                    Producto prodmandar = new Producto();

                                    prodmandar.setPro_id(Integer.parseInt(txt_prop1.getText().toString()));
                                    prodmandar.setPro_nombre(txt_prop2.getText().toString());
                                    prodmandar.setPro_precio(txt_proex.getText().toString());
                                    prodmandar.setPro_cantidad(txt_prop3.getText().toString());
                                    prodmandar.setCat_id(talid.getText().toString());

                                    editarProducto(prodmandar);

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
                                Servicio servenv = new Servicio();

                                servenv.setSer_id(Integer.parseInt(txt_prop1.getText().toString()));
                                servenv.setSer_nombre(txt_prop2.getText().toString());
                                servenv.setSer_descri(txt_proex.getText().toString());
                                servenv.setEst_id(talid.getText().toString());

                                editarServicio(servenv);

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

                            estenv.setEst_id(Integer.parseInt(txt_prop1.getText().toString()));
                            estenv.setEst_nombre(txt_prop2.getText().toString());

                            editarEstado(estenv);
                        }
                    }
                }

                if(btn_prop1.getText().toString().toLowerCase().equals("eliminar")) //CONFIGURACION PARA ELIMINAR
                {
                    switch (selector2)
                    {
                        case "locales":

                            deleteLocal(txt_prop1.getText().toString());

                            break;

                        case "empleados":

                            deleteEmpleado(txt_prop1.getText().toString());

                            break;


                        case "cargos":

                            deleteCargo(Integer.parseInt(txt_prop1.getText().toString()));

                            break;


                        case "categorias":


                            deleteCategoria(Integer.parseInt(txt_prop1.getText().toString()));


                            break;



                        case "productos":

                            deleteProducto(Integer.parseInt(txt_prop1.getText().toString()));

                            break;


                        case "servicios":

                            deleteServicio(Integer.parseInt(txt_prop1.getText().toString()));

                            break;



                        case "estados":

                            deleteEstado(Integer.parseInt(txt_prop1.getText().toString()));

                            break;

                    }
                }
            }
        });
    }

    private void deleteEstado(int i) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Estado> call = request.deleteEstado(i);

        call.enqueue(new Callback<Estado>()
        {
            @Override
            public void onResponse(@NonNull Call<Estado> call, @NonNull Response<Estado> response)
            {
                showmessage("Estado Eliminado");
                finish();
            }

            @Override
            public void onFailure(@NonNull Call<Estado> call, @NonNull Throwable t)
            {
                t.printStackTrace();
            }

        });

    }

    private void deleteServicio(int i) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Servicio> call = request.deleteServicio(i);

        call.enqueue(new Callback<Servicio>()
        {
            @Override
            public void onResponse(@NonNull Call<Servicio> call, @NonNull Response<Servicio> response)
            {
                showmessage("Servicio Eliminado");
                finish();
            }

            @Override
            public void onFailure(@NonNull Call<Servicio> call, @NonNull Throwable t)
            {
                t.printStackTrace();
            }

        });


    }

    private void deleteProducto(int i) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Producto> call = request.deleteProducto(i);

        call.enqueue(new Callback<Producto>()
        {
            @Override
            public void onResponse(@NonNull Call<Producto> call, @NonNull Response<Producto> response)
            {
                showmessage("Estado Eliminado");
                finish();
            }

            @Override
            public void onFailure(@NonNull Call<Producto> call, @NonNull Throwable t)
            {
                t.printStackTrace();
            }

        });


    }

    private void deleteCategoria(int i) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Categoria> call = request.deleteCategoria(i);

        call.enqueue(new Callback<Categoria>()
        {
            @Override
            public void onResponse(@NonNull Call<Categoria> call, @NonNull Response<Categoria> response)
            {
                showmessage("Estado Eliminado");
                finish();
            }

            @Override
            public void onFailure(@NonNull Call<Categoria> call, @NonNull Throwable t)
            {
                t.printStackTrace();
            }

        });


    }

    private void deleteCargo(int i) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Cargo> call = request.deleteCargo(i);

        call.enqueue(new Callback<Cargo>()
        {
            @Override
            public void onResponse(@NonNull Call<Cargo> call, @NonNull Response<Cargo> response)
            {
                showmessage("Estado Eliminado");
                finish();
            }

            @Override
            public void onFailure(@NonNull Call<Cargo> call, @NonNull Throwable t)
            {
                t.printStackTrace();
            }

        });


    }

    private void deleteEmpleado(String s) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Empleado> call = request.deleteEmpleado(s);

        call.enqueue(new Callback<Empleado>()
        {
            @Override
            public void onResponse(@NonNull Call<Empleado> call, @NonNull Response<Empleado> response)
            {
                showmessage("Estado Eliminado");
                finish();
            }

            @Override
            public void onFailure(@NonNull Call<Empleado> call, @NonNull Throwable t)
            {
                t.printStackTrace();
            }

        });


    }

    private void deleteLocal(String s) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Taller> call = request.deleteTaller(s);

        call.enqueue(new Callback<Taller>()
        {
            @Override
            public void onResponse(@NonNull Call<Taller> call, @NonNull Response<Taller> response)
            {
                showmessage("Estado Eliminado");
                finish();
            }

            @Override
            public void onFailure(@NonNull Call<Taller> call, @NonNull Throwable t)
            {
                t.printStackTrace();
            }

        });


    }

    private void editarEstado(Estado estenv) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Estado> call = request.updateEstado(estenv.getEst_id() + "", estenv);

        call.enqueue(new Callback<Estado>()
        {
            @Override
            public void onResponse(@NonNull Call<Estado> call, @NonNull Response<Estado> response)
            {

                //MODO ACA ES RECYCLER
                if (response.code() == 200)
                {
                    showmessage("ACTUALIZACION EXITOSA");

                    if(config.equals("recycler"))
                    {
                        Intent intent = new Intent(getApplicationContext(), GestionActivity.class);
                        intent.putExtra("selector", "estados");
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
            public void onFailure(@NonNull Call<Estado> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });

    }

    private void editarServicio(Servicio servenv) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Servicio> call = request.updateServicio(servenv.getSer_id() + "", servenv);

        call.enqueue(new Callback<Servicio>()
        {
            @Override
            public void onResponse(@NonNull Call<Servicio> call, @NonNull Response<Servicio> response)
            {

                //MODO ACA ES RECYCLER
                if (response.code() == 200)
                {
                    showmessage("ACTUALIZACION EXITOSA");

                    if(config.equals("recycler"))
                    {
                        Intent intent = new Intent(getApplicationContext(), GestionActivity.class);
                        intent.putExtra("selector", "servicios");
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
            public void onFailure(@NonNull Call<Servicio> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });


    }

    private void editarProducto(Producto prodmandar) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Producto> call = request.updateProducto(prodmandar.getPro_id() + "", prodmandar);

        call.enqueue(new Callback<Producto>()
        {
            @Override
            public void onResponse(@NonNull Call<Producto> call, @NonNull Response<Producto> response)
            {

                //MODO ACA ES RECYCLER
                if (response.code() == 200)
                {
                    showmessage("ACTUALIZACION EXITOSA");

                    if(config.equals("recycler"))
                    {
                        Intent intent = new Intent(getApplicationContext(), GestionActivity.class);
                        intent.putExtra("selector", "productos");
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
            public void onFailure(@NonNull Call<Producto> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });

    }

    private void editarCategoria(Categoria cateman) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Categoria> call = request.updateCategoria(cateman.getCat_id() + "", cateman);

        call.enqueue(new Callback<Categoria>()
        {
            @Override
            public void onResponse(@NonNull Call<Categoria> call, @NonNull Response<Categoria> response)
            {

                //MODO ACA ES RECYCLER
                if (response.code() == 200)
                {
                    showmessage("ACTUALIZACION EXITOSA");

                    if(config.equals("recycler"))
                    {
                        Intent intent = new Intent(getApplicationContext(), GestionActivity.class);
                        intent.putExtra("selector", "categorias");
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
            public void onFailure(@NonNull Call<Categoria> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });


    }

    private void editarCargo(Cargo carmanda) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Cargo> call = request.updateCargo(carmanda.getCar_id() + "", carmanda);

        call.enqueue(new Callback<Cargo>()
        {
            @Override
            public void onResponse(@NonNull Call<Cargo> call, @NonNull Response<Cargo> response)
            {

                //MODO ACA ES RECYCLER
                if (response.code() == 200)
                {
                    showmessage("ACTUALIZACION EXITOSA");

                    if(config.equals("recycler"))
                    {
                        Intent intent = new Intent(getApplicationContext(), GestionActivity.class);
                        intent.putExtra("selector", "cargos");
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
            public void onFailure(@NonNull Call<Cargo> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });

    }

    private void editarEmpleado(Empleado emplemandar) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Empleado> call = request.updateEmpleado(emplemandar.getEmp_dni(), emplemandar);

        call.enqueue(new Callback<Empleado>()
        {
            @Override
            public void onResponse(@NonNull Call<Empleado> call, @NonNull Response<Empleado> response)
            {

                //MODO ACA ES RECYCLER
                if (response.code() == 200)
                {
                    showmessage("ACTUALIZACION EXITOSA");

                    if(config.equals("recycler"))
                    {
                        Intent intent = new Intent(getApplicationContext(), GestionActivity.class);
                        intent.putExtra("selector", "empleados");
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
            public void onFailure(@NonNull Call<Empleado> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });


    }

    private void editarTaller(Taller talmandar) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Taller> call = request.updateTaller(talmandar.getTal_id(), talmandar);

        call.enqueue(new Callback<Taller>()
        {
            @Override
            public void onResponse(@NonNull Call<Taller> call, @NonNull Response<Taller> response)
            {

                //MODO ACA ES RECYCLER
                if (response.code() == 200)
                {
                    showmessage("ACTUALIZACION EXITOSA");

                    if(config.equals("recycler"))
                    {
                        Intent intent = new Intent(getApplicationContext(), GestionActivity.class);
                        intent.putExtra("selector", "locales");
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
            public void onFailure(@NonNull Call<Taller> call, @NonNull Throwable t)
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


                if(response.code() == 200)
                {


                    recyclerView.setAdapter(null);

                    setAdapter(response.body(),null, null, null);


                }
                else
                {
                    showmessage("No hay Locales registrados");
                }

            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Taller>> call, @NonNull Throwable t)
            {

                t.printStackTrace();

            }
        });
    }

    private void getAllempleos() {


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


                    recyclerView.setAdapter(null);
                    setAdapter(null,response.body(),null, null);


                }
                else
                {
                    showmessage("No hay Cargos registrados");
                }


            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Cargo>> call, @NonNull Throwable t)
            {

                t.printStackTrace();

            }
        });

    }

    private void getAllcategorias() {

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


                if(response.code() == 200)
                {


                    recyclerView.setAdapter(null);

                    setAdapter(null,null, response.body(),null);


                }
                else
                {
                    showmessage("No hay categorias registradas");
                }

            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Categoria>> call, @NonNull Throwable t)
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


                if(response.code() == 200)
                {


                    recyclerView.setAdapter(null);

                    setAdapter(null,null, null, response.body());


                }
                else
                {
                    showmessage("No hay categorias registradas");
                }

            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Estado>> call, @NonNull Throwable t)
            {

                t.printStackTrace();

            }
        });

    }

    private void deterempleo(int id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Cargo> call = request.getJSONCargoid(id); //VERIFICAR SI CORREO EXISTE

        call.enqueue(new Callback<Cargo>() {
            @Override
            public void onResponse(@NonNull Call<Cargo> call, @NonNull Response<Cargo> response) {

                if (response.code() == 200)//VERIFICAMOS RECEPCION CORRECTA DE GET
                {

                    assert response.body() != null;
                    txt_proex.setText(response.body().getCargo());
                }
                else
                {
                    showmessage("NO ESTA DEFINIDO EL CARGO");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cargo> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });
    }

    private void detercatego(int cat_id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Categoria> call = request.getJSONCategoriaid(cat_id); //VERIFICAR SI CORREO EXISTE

        call.enqueue(new Callback<Categoria>() {
            @Override
            public void onResponse(@NonNull Call<Categoria> call, @NonNull Response<Categoria> response) {

                if (response.code() == 200)//VERIFICAMOS RECEPCION CORRECTA DE GET
                {

                    assert response.body() != null;
                    txt_prop4.setText(response.body().getCat_nombre());
                }
                else
                {
                    showmessage("NO ESTA DEFINIDA LA CATEGORIA");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Categoria> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });

    }

    private void deterestado(int i) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Estado> call = request.getJSONEstadoid(i); //VERIFICAR SI CORREO EXISTE

        call.enqueue(new Callback<Estado>() {
            @Override
            public void onResponse(@NonNull Call<Estado> call, @NonNull Response<Estado> response) {

                if (response.code() == 200)//VERIFICAMOS RECEPCION CORRECTA DE GET
                {

                    assert response.body() != null;
                    txt_prop4.setText(response.body().getEst_nombre());
                }
                else
                {
                    showmessage("NO ESTA DEFINIDA LA CATEGORIA");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Estado> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });

    }

    private void getTallerid(String s) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Taller> call = request.getJSONTallerid(s); //VERIFICAR SI CORREO EXISTE

        call.enqueue(new Callback<Taller>() {
            @Override
            public void onResponse(@NonNull Call<Taller> call, @NonNull Response<Taller> response) {

                if (response.code() == 200)//VERIFICAMOS RECEPCION CORRECTA DE GET
                {

                    local = response.body();

                    seteo();

                    btn_prop1.setVisibility(View.VISIBLE);

                    if(selector.equals("eliminar"))
                    {
                        txt_prop1.setEnabled(false);
                        txt_prop2.setEnabled(false);
                        txt_proex.setEnabled(false);
                        txt_prop3.setEnabled(false);
                        txt_prop4.setEnabled(false);
                        recyclerView.setVisibility(View.INVISIBLE);
                    }


                }
                else
                {
                    showmessage("LOCAL NO REGISTRADO");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Taller> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });
    }

    private void getEmpleadoid(String s) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Empleado> call = request.getJSONEmpleadoid(s); //VERIFICAR SI CORREO EXISTE

        call.enqueue(new Callback<Empleado>() {
            @Override
            public void onResponse(@NonNull Call<Empleado> call, @NonNull Response<Empleado> response) {

                if (response.code() == 200)//VERIFICAMOS RECEPCION CORRECTA DE GET
                {

                    empleado = response.body();

                    seteo();

                    btn_prop1.setVisibility(View.VISIBLE);

                    if(selector.equals("eliminar"))
                    {
                        txt_prop1.setEnabled(false);
                        txt_prop2.setEnabled(false);
                        txt_proex.setEnabled(false);
                        txt_prop3.setEnabled(false);
                        txt_prop4.setEnabled(false);
                        recyclerView.setVisibility(View.INVISIBLE);
                    }

                }
                else
                {
                    showmessage("EMPLEADO NO REGISTRADO");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Empleado> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });
    }

    private void getCargoid(int s) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Cargo> call = request.getJSONCargoid(s); //VERIFICAR SI CORREO EXISTE

        call.enqueue(new Callback<Cargo>() {
            @Override
            public void onResponse(@NonNull Call<Cargo> call, @NonNull Response<Cargo> response) {

                if (response.code() == 200)//VERIFICAMOS RECEPCION CORRECTA DE GET
                {

                    cargo = response.body();

                    seteo();

                    btn_prop1.setVisibility(View.VISIBLE);

                    if(selector.equals("eliminar"))
                    {
                        txt_prop1.setEnabled(false);
                        txt_prop2.setEnabled(false);
                        txt_proex.setEnabled(false);
                        txt_prop3.setEnabled(false);
                        txt_prop4.setEnabled(false);
                        recyclerView.setVisibility(View.INVISIBLE);
                    }

                }
                else
                {
                    showmessage("CARGO NO REGISTRADO");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cargo> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });
    }

    private void getCategoriaid(int s) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Categoria> call = request.getJSONCategoriaid(s); //VERIFICAR SI CORREO EXISTE

        call.enqueue(new Callback<Categoria>() {
            @Override
            public void onResponse(@NonNull Call<Categoria> call, @NonNull Response<Categoria> response) {

                if (response.code() == 200)//VERIFICAMOS RECEPCION CORRECTA DE GET
                {

                    categoria = response.body();

                    seteo();

                    btn_prop1.setVisibility(View.VISIBLE);

                    if(selector.equals("eliminar"))
                    {
                        txt_prop1.setEnabled(false);
                        txt_prop2.setEnabled(false);
                        txt_proex.setEnabled(false);
                        txt_prop3.setEnabled(false);
                        txt_prop4.setEnabled(false);
                        recyclerView.setVisibility(View.INVISIBLE);
                    }

                }
                else
                {
                    showmessage("CATEGORIA NO REGISTRADA");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Categoria> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });
    }

    private void getProductoid(int s) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Producto> call = request.getJSONProductoid(s); //VERIFICAR SI CORREO EXISTE

        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(@NonNull Call<Producto> call, @NonNull Response<Producto> response) {

                if (response.code() == 200)//VERIFICAMOS RECEPCION CORRECTA DE GET
                {

                    producto = response.body();

                    seteo();

                    btn_prop1.setVisibility(View.VISIBLE);

                    if(selector.equals("eliminar"))
                    {
                        txt_prop1.setEnabled(false);
                        txt_prop2.setEnabled(false);
                        txt_proex.setEnabled(false);
                        txt_prop3.setEnabled(false);
                        txt_prop4.setEnabled(false);
                        recyclerView.setVisibility(View.INVISIBLE);
                    }

                }
                else
                {
                    showmessage("PRODUCTO NO REGISTRADO");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Producto> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });
    }

    private void getServicioid(int s) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Servicio> call = request.getJSONServicioid(s); //VERIFICAR SI CORREO EXISTE

        call.enqueue(new Callback<Servicio>() {
            @Override
            public void onResponse(@NonNull Call<Servicio> call, @NonNull Response<Servicio> response) {

                if (response.code() == 200)//VERIFICAMOS RECEPCION CORRECTA DE GET
                {

                    servicio = response.body();

                    seteo();

                    btn_prop1.setVisibility(View.VISIBLE);

                    if(selector.equals("eliminar"))
                    {
                        txt_prop1.setEnabled(false);
                        txt_prop2.setEnabled(false);
                        txt_proex.setEnabled(false);
                        txt_prop3.setEnabled(false);
                        txt_prop4.setEnabled(false);
                        recyclerView.setVisibility(View.INVISIBLE);
                    }

                }
                else
                {
                    showmessage("SERVICIO NO REGISTRADO");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Servicio> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });
    }

    private void getEstadoid(int s) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Estado> call = request.getJSONEstadoid(s); //VERIFICAR SI CORREO EXISTE

        call.enqueue(new Callback<Estado>() {
            @Override
            public void onResponse(@NonNull Call<Estado> call, @NonNull Response<Estado> response) {

                if (response.code() == 200)//VERIFICAMOS RECEPCION CORRECTA DE GET
                {

                    estado = response.body();

                    seteo();

                    btn_prop1.setVisibility(View.VISIBLE);

                    if(selector.equals("eliminar"))
                    {
                        txt_prop1.setEnabled(false);
                        txt_prop2.setEnabled(false);
                        txt_proex.setEnabled(false);
                        txt_prop3.setEnabled(false);
                        txt_prop4.setEnabled(false);
                        recyclerView.setVisibility(View.INVISIBLE);
                    }

                }
                else
                {
                    showmessage("ESTADO NO REGISTRADO");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Estado> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void getselector() {
        Intent intent = getIntent();
        selector  =  Objects.requireNonNull(intent.getExtras()).getString("selector"); //FUNCION DEL BOTON AGREGAR/EDITAR/ELIMINAR
        selector2 = intent.getExtras().getString("selector2");
        config    = intent.getExtras().getString("config");//PARA EL RECYCLER


        assert config != null;
        if(!config.equals("recycler"))
        {

            txt_prop1.setEnabled(true);
            txt_prop1.requestFocus();

            btn_prop1.setVisibility(View.INVISIBLE);
            txt_prop2.setVisibility(View.INVISIBLE);
            txt_proex.setVisibility(View.INVISIBLE);
            txt_prop3.setVisibility(View.INVISIBLE);
            txt_prop4.setVisibility(View.INVISIBLE);

            if(selector.equals("editar") || selector.equals("eliminar"))
            {

                btn_buscar.setVisibility(View.VISIBLE);


                switch (selector2)
                {
                    case "locales":

                        txt_prop1.setHint("ID DEL LOCAL");

                        txt_prop1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});
                        txt_prop1.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        txt_prop1.setInputType(InputType.TYPE_CLASS_TEXT);

                        break;

                    case "empleados":

                        txt_prop1.setHint("DNI EMPLEADO");

                        txt_prop1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});
                        txt_prop1.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        txt_prop1.setInputType(InputType.TYPE_CLASS_NUMBER);

                        break;


                    case "cargos":

                        txt_prop1.setHint("ID DEL CARGO");

                        txt_prop1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
                        txt_prop1.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        txt_prop1.setInputType(InputType.TYPE_CLASS_NUMBER);

                        break;


                    case "categorias":

                        txt_prop1.setHint("ID CATEGORIA");

                        txt_prop1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
                        txt_prop1.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        txt_prop1.setInputType(InputType.TYPE_CLASS_NUMBER);


                        break;



                    case "productos":

                        txt_prop1.setHint("ID PRODUCTO");

                        txt_prop1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
                        txt_prop1.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        txt_prop1.setInputType(InputType.TYPE_CLASS_NUMBER);


                        break;


                    case "servicios":

                        txt_prop1.setHint("ID SERVICIO");

                        txt_prop1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
                        txt_prop1.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        txt_prop1.setInputType(InputType.TYPE_CLASS_NUMBER);


                        break;



                    case "estados":


                        txt_prop1.setHint("ID ESTADO");

                        txt_prop1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
                        txt_prop1.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        txt_prop1.setInputType(InputType.TYPE_CLASS_NUMBER);

                        break;


                }

                btn_buscar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String test;

                        switch (selector2)
                        {
                            case "locales":

                                test = txt_prop1.getText().toString();

                                if(test.length() < 8)
                                {
                                    showmessage("CODIGO DE LOCAL INVALIDO");
                                }
                                else
                                {
                                    getTallerid(txt_prop1.getText().toString());
                                }


                                break;

                            case "empleados":

                                test = txt_prop1.getText().toString();

                                if(test.length() < 8)
                                {
                                    showmessage("DNI INVALIDO");
                                }
                                else
                                {
                                    getEmpleadoid(txt_prop1.getText().toString());
                                }


                                break;


                            case "cargos":

                                test = txt_prop1.getText().toString();

                                if(test.length() == 0)
                                {
                                    showmessage("ESCRIBA CODIGO DE CARGO");
                                }
                                else
                                {
                                    getCargoid(Integer.parseInt(txt_prop1.getText().toString()));
                                }

                                break;


                            case "categorias":


                                test = txt_prop1.getText().toString();

                                if(test.length() == 0)
                                {
                                    showmessage("ESCRIBA CODIGO DE CATEGORIA");
                                }
                                else
                                {
                                    getCategoriaid(Integer.parseInt(txt_prop1.getText().toString()));
                                }


                                break;

                            case "productos":


                                test = txt_prop1.getText().toString();

                                if(test.length() == 0)
                                {
                                    showmessage("ESCRIBA CODIGO DE PRODUCTO");
                                }
                                else
                                {
                                    getProductoid(Integer.parseInt(txt_prop1.getText().toString()));
                                }


                                break;


                            case "servicios":


                                test = txt_prop1.getText().toString();

                                if(test.length() == 0)
                                {
                                    showmessage("ESCRIBA CODIGO DE SERVICIO");
                                }
                                else
                                {
                                    getServicioid(Integer.parseInt(txt_prop1.getText().toString()));
                                }


                                break;



                            case "estados":

                                test = txt_prop1.getText().toString();

                                if(test.length() == 0)
                                {
                                    showmessage("ESCRIBA CODIGO DEL ESTADO");
                                }
                                else
                                {
                                    getEstadoid(Integer.parseInt(txt_prop1.getText().toString()));
                                }

                                break;
                        }
                    }
                });
            }
        }
        else
        {

            btn_buscar.setVisibility(View.INVISIBLE);

            local     = (Taller) intent.getExtras().getSerializable("local");
            empleado  = (Empleado) intent.getExtras().getSerializable("empleado");
            cargo     = (Cargo) intent.getExtras().getSerializable("cargo");
            categoria = (Categoria) intent.getExtras().getSerializable("categoria");
            producto  = (Producto) intent.getExtras().getSerializable("producto");
            servicio  = (Servicio) intent.getExtras().getSerializable("servicio");
            estado    = (Estado) intent.getExtras().getSerializable("estado");

            if(selector2.equals("empleados"))
            {
                empid.setText(empleado.getCar_id() + "");
                talid.setText(empleado.getTal_id());
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private void seteo(){

        switch (selector2.toLowerCase()) // CONFIGURAMOS PRIMERO QUE SE VA VER Y QUE NO BASADO EN EL BOTON
        {
            case "locales":


                titu1 = " LOCALES";

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


                txt_prop1.setText(local.getTal_id());
                txt_prop1.setEnabled(false);
                txt_prop2.setText(local.getTal_nombre());
                txt_proex.setText(local.getTal_direccion());

                txt_prop2.setEnabled(true);
                txt_proex.setEnabled(true);


                break;

            case "empleados":


                titu1 = " EMPLEADOS";

                txt_prop1.setVisibility(View.VISIBLE);
                txt_prop2.setVisibility(View.VISIBLE);
                txt_proex.setVisibility(View.VISIBLE);
                txt_proex.setFocusable(false);
                txt_proex.setEnabled(true);
                txt_prop3.setVisibility(View.VISIBLE);
                txt_prop4.setVisibility(View.VISIBLE);
                txt_prop4.setFocusable(false);
                txt_prop4.setEnabled(true);
                recyclerView.setVisibility(View.VISIBLE);

                txt_proex.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getAllempleos();
                    }
                });

                txt_prop4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getAlltalleres();
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


                txt_prop1.setText(empleado.getEmp_dni());
                txt_prop1.setEnabled(false);

                txt_prop2.setText(empleado.getEmp_nombre());
                deterempleo(empleado.getCar_id());
                txt_prop3.setText(empleado.getEmp_pass());
                txt_prop4.setText(empleado.getTal_id());


                txt_prop2.setEnabled(true);
                txt_prop3.setEnabled(true);



                break;

            case "cargos":

                titu1 = " CARGOS";

                txt_prop1.setVisibility(View.VISIBLE);
                txt_prop2.setVisibility(View.VISIBLE);
                txt_proex.setVisibility(View.INVISIBLE);
                txt_prop3.setVisibility(View.INVISIBLE);
                txt_prop4.setVisibility(View.INVISIBLE);

                txt_prop2.setHint("NOMBRE DEL CARGO");

                txt_prop2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});


                txt_prop2.setInputType(InputType.TYPE_CLASS_TEXT);


                txt_prop1.setText(cargo.getCar_id() + "");
                txt_prop1.setEnabled(false);

                txt_prop2.setText(cargo.getCargo());

                txt_prop2.setEnabled(true);




                break;

            case "categorias":

                titu1 = " CATEGORIAS";

                txt_prop1.setVisibility(View.VISIBLE);
                txt_prop2.setVisibility(View.VISIBLE);
                txt_proex.setVisibility(View.INVISIBLE);
                txt_prop3.setVisibility(View.INVISIBLE);
                txt_prop4.setVisibility(View.INVISIBLE);

                txt_prop1.setHint("ID DE LA CATEGORIA");
                txt_prop2.setHint("NOMBRE DE LA CATEGORIA");

                txt_prop2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});


                txt_prop1.setInputType(InputType.TYPE_CLASS_TEXT);


                txt_prop1.setText(categoria.getCat_id() + "");
                txt_prop1.setEnabled(false);

                txt_prop2.setText(categoria.getCat_nombre());

                txt_prop2.setEnabled(true);




                break;

            case "productos":

                titu1 = " PRODUCTOS";


                txt_prop1.setVisibility(View.VISIBLE);
                txt_prop2.setVisibility(View.VISIBLE);
                txt_proex.setVisibility(View.VISIBLE);
                txt_prop3.setVisibility(View.VISIBLE);
                txt_prop4.setVisibility(View.VISIBLE);
                txt_prop4.setFocusable(false);
                txt_prop4.setEnabled(true);
                recyclerView.setVisibility(View.VISIBLE);

                txt_prop4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getAllcategorias();
                    }
                });

                txt_prop1.setHint("ID PRODUCTO");
                txt_prop2.setHint("NOMBRE PRODUCTO");
                txt_proex.setHint("PRECIO PRODUCTO");
                txt_prop3.setHint("CANTIDAD PRODUCTO");
                txt_prop4.setHint("CATEGORIA PRODUCTO");

                txt_prop1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});
                txt_prop2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});
                txt_proex.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});
                txt_prop3.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});
                txt_prop4.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});

                txt_prop1.setInputType(InputType.TYPE_CLASS_NUMBER);
                txt_prop2.setInputType(InputType.TYPE_CLASS_TEXT);
                txt_proex.setInputType(InputType.TYPE_CLASS_NUMBER);
                txt_prop3.setInputType(InputType.TYPE_CLASS_NUMBER);
                txt_prop4.setInputType(InputType.TYPE_CLASS_TEXT);


                txt_prop1.setText(producto.getPro_id() + "");
                txt_prop1.setEnabled(false);

                txt_prop2.setText(producto.getPro_nombre());
                txt_proex.setText(producto.getPro_precio());
                txt_prop3.setText(producto.getPro_cantidad());
                detercatego(Integer.parseInt(producto.getCat_id()));


                txt_prop2.setEnabled(true);
                txt_proex.setEnabled(true);
                txt_prop3.setEnabled(true);



                break;

            case "servicios":

                titu1 = " SERVICIOS";

                txt_prop1.setVisibility(View.VISIBLE);
                txt_prop2.setVisibility(View.VISIBLE);
                txt_proex.setVisibility(View.VISIBLE);
                txt_prop3.setVisibility(View.INVISIBLE);
                txt_prop4.setVisibility(View.VISIBLE);
                txt_prop4.setFocusable(false);
                txt_prop4.setEnabled(true);
                recyclerView.setVisibility(View.VISIBLE);

                txt_prop4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getAllestados();
                    }
                });

                txt_prop1.setHint("ID SERVICIO");
                txt_prop2.setHint("NOMBRE SERVICIO");
                txt_proex.setHint("DESCRIPCION SERVICIO");
                txt_prop4.setHint("ESTADO DEL SERVICIO");

                txt_prop1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});
                txt_prop2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});
                txt_proex.setFilters(new InputFilter[] {new InputFilter.LengthFilter(40)});
                txt_prop4.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});

                txt_prop1.setInputType(InputType.TYPE_CLASS_NUMBER);
                txt_prop2.setInputType(InputType.TYPE_CLASS_TEXT);
                txt_proex.setInputType(InputType.TYPE_CLASS_TEXT);
                txt_prop4.setInputType(InputType.TYPE_CLASS_TEXT);



                txt_prop1.setText(servicio.getSer_id() + "");
                txt_prop1.setEnabled(false);

                txt_prop2.setText(servicio.getSer_nombre());
                txt_proex.setText(servicio.getSer_descri());

                deterestado(Integer.parseInt(servicio.getEst_id()));

                txt_prop2.setEnabled(true);
                txt_proex.setEnabled(true);
                txt_prop3.setEnabled(true);



                break;

            case "estados":

                titu1 = " ESTADOS";


                txt_prop1.setVisibility(View.VISIBLE);
                txt_prop2.setVisibility(View.VISIBLE);
                txt_proex.setVisibility(View.INVISIBLE);
                txt_prop3.setVisibility(View.INVISIBLE);
                txt_prop4.setVisibility(View.INVISIBLE);

                txt_prop2.setHint("NOMBRE DEL ESTADO");

                txt_prop2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});


                txt_prop2.setInputType(InputType.TYPE_CLASS_TEXT);


                txt_prop1.setText(estado.getEst_id() + "");
                txt_prop1.setEnabled(false);

                txt_prop2.setText(estado.getEst_nombre());

                txt_prop2.setEnabled(true);





                break;

        }

        txt_actividad.setText(titu2 + titu1);

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

    private void setAdapter(ArrayList<Taller> tal, ArrayList<Cargo> car, ArrayList<Categoria> cate, ArrayList<Estado> est) {

        adp = new RecyclerViewAdaptermin(getApplicationContext(),tal, car, cate, est, txt_prop4, txt_proex, talid, empid);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adp);

    }

}

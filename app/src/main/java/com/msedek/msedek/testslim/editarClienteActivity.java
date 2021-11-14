package com.msedek.msedek.testslim;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.msedek.msedek.testslim.Interfaz.iSendCliente;
import com.msedek.msedek.testslim.Models.Cliente;


import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class editarClienteActivity extends AppCompatActivity {

    Cliente cliente;

    EditText edt_nombrep;
    EditText edt_apellidop;
    EditText edt_mailp;
    EditText edt_celp;
    EditText edt_passp;
    EditText edt_passcp;
    EditText edt_placasp;
    Button   btn_savep;
    Button   btn_cancelp;
    Button   btn_buscli;
    EditText edt_edtcli;

    ConstraintLayout mainl;

    String selector;
    Boolean swmodo;




    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);

        edt_nombrep   = findViewById(R.id.edt_marcavep);
        edt_apellidop = findViewById(R.id.edt_modelovep);
        edt_mailp     = findViewById(R.id.edt_anofabve);
        edt_celp      = findViewById(R.id.edt_deniclive);
        edt_passp     = findViewById(R.id.edt_passp);
        edt_passcp    = findViewById(R.id.edt_passcp);
        btn_savep     = findViewById(R.id.btn_savevepe);
        btn_cancelp   = findViewById(R.id.bnt_cancelvepe);
        btn_buscli    = findViewById(R.id.btn_busveh);
        edt_edtcli    = findViewById(R.id.edt_edtveh);

        mainl        = findViewById(R.id.mainl);

        getselector();


        if(selector.equals("editar"))
        {
            btn_savep.setText("Guardar");
        }
        else
        {
            btn_savep.setText("Eliminar");
        }



        btn_buscli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edt_edtcli.length() < 8)
                {
                    showmessage("DNI INVALIDO");
                }
                else
                {
                    pedirCliente();

                    InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mainl.getWindowToken(), 0);

                }
            }
        });


        btn_cancelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_savep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selector.equals("editar"))
                {
                    String test = edt_nombrep.getText().toString().trim();

                    if(test.length() < 3 || test.matches(".*\\d+.*"))
                    {
                        showmessage("ESCRIBA NOMBRE VALIDO");
                    }
                    else
                    {
                        test = edt_apellidop.getText().toString().trim();

                        if(test.length() < 3 || test.matches(".*\\d+.*"))
                        {
                            showmessage("ESCRIBA APELLIDO VALIDO");
                        }
                        else
                        {
                            test = edt_mailp.getText().toString().trim();

                            if(!isValidEmail(test))
                            {
                                showmessage("E-MAIL INVALIDO");
                            }
                            else
                            {
                                test = edt_celp.getText().toString().trim();

                                if(test.length() < 9)
                                {
                                    showmessage("NUMERO CELULAR INVALIDO");
                                }
                                else
                                {
                                    test = edt_passp.getText().toString().trim();

                                    if(test.length() < 8)
                                    {
                                        showmessage("PASSWORD DEBE SER DE 8 CARACTERES");
                                    }
                                    else
                                    {
                                        test = edt_passcp.getText().toString().trim();

                                        if(!test.equals(edt_passp.getText().toString()))
                                        {
                                            showmessage("PASSWORD NO COINCIDE");
                                        }
                                        else

                                        {
                                            showmessage("ACTUALIZANDO USUARIO");

                                            Cliente clientem = new Cliente();

                                            clientem.setCli_nombre(edt_nombrep.getText().toString());
                                            clientem.setCli_apellido(edt_apellidop.getText().toString());
                                            clientem.setCli_correo(edt_mailp.getText().toString());
                                            clientem.setCli_celular(edt_celp.getText().toString());
                                            clientem.setCli_dni(edt_edtcli.getText().toString());
                                            clientem.setCli_contrasena(edt_passp.getText().toString());

                                            updateCliente(clientem);
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
                else
                {
                    eliminarCliente();
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
        }, 1500);

    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void updateCliente(Cliente clientem)
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Cliente> call = request.updateCliente(clientem.getCli_dni(), clientem);

        call.enqueue(new Callback<Cliente>()
        {
            @Override
            public void onResponse(@NonNull Call<Cliente> call, @NonNull Response<Cliente> response)
            {

                if (response.code() == 200)
                {
                    showmessage("ACTUALIZACION EXITOSA");

                    if(!swmodo)
                    {
                        Intent intent = new Intent(getApplicationContext(), listClientVehActivity.class);
                        intent.putExtra("selector", "clientes");
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
            public void onFailure(@NonNull Call<Cliente> call, @NonNull Throwable t)
            {

                t.printStackTrace();
            }
        });
    }

    public void pedirCliente()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Cliente> call = request.getJSONClienteid(edt_edtcli.getText().toString());

        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(@NonNull Call<Cliente> call, @NonNull Response<Cliente> response) {

                if (response.code() == 200)//VERIFICAMOS RECEPCION CORRECTA DE GET
                {
                    Cliente cliente = response.body();

                    assert cliente != null;
                    edt_nombrep.setText(cliente.getCli_nombre());
                    edt_apellidop.setText(cliente.getCli_apellido());
                    edt_mailp.setText(cliente.getCli_correo());
                    edt_celp.setText(cliente.getCli_celular());
                    edt_passp.setText(cliente.getCli_contrasena());
                    edt_passcp.setText(cliente.getCli_contrasena());



                    btn_savep.setEnabled(true);

                    if(selector.equals("editar"))
                    {
                        edt_nombrep.setEnabled(true);
                        edt_apellidop.setEnabled(true);
                        edt_mailp.setEnabled(true);
                        edt_celp.setEnabled(true);
                        edt_passp.setEnabled(true);
                        edt_passcp.setEnabled(true);

                    }




                }
                else
                {
                    if(response.code() == 500)
                    {
                        showmessage("DNI NO REGISTRADO");
                        edt_edtcli.requestFocus();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cliente> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });
    }

    private void getselector()
    {
        Intent intent = getIntent();
        selector =  Objects.requireNonNull(intent.getExtras()).getString("selector");
        swmodo   = intent.getExtras().getBoolean("swmodo");

        if (!swmodo)
        {
            btn_buscli.setEnabled(false);
            btn_savep.setEnabled(true);
            cliente = (Cliente) intent.getExtras().getSerializable("cliente");

            assert cliente != null;
            edt_nombrep.setText(cliente.getCli_nombre());
            edt_apellidop.setText(cliente.getCli_apellido());
            edt_mailp.setText(cliente.getCli_correo());
            edt_celp.setText(cliente.getCli_celular());
            edt_passp.setText(cliente.getCli_contrasena());
            edt_passcp.setText(cliente.getCli_contrasena());
            edt_edtcli.setText(cliente.getCli_dni());

            edt_nombrep.setEnabled(true);
            edt_apellidop.setEnabled(true);
            edt_mailp.setEnabled(true);
            edt_celp.setEnabled(true);
            edt_passp.setEnabled(true);
            edt_passcp.setEnabled(true);
            edt_edtcli.setEnabled(false);

        }

    }

    private void eliminarCliente()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tecfomatica.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iSendCliente request = retrofit.create(iSendCliente.class);

        Call<Cliente> call = request.deleteCliente(edt_edtcli.getText().toString());

        call.enqueue(new Callback<Cliente>()
        {
            @Override
            public void onResponse(@NonNull Call<Cliente> call, @NonNull Response<Cliente> response)
            {
                showmessage("Cliente Eliminado");
                finish();
            }

            @Override
            public void onFailure(@NonNull Call<Cliente> call, @NonNull Throwable t)
            {
                t.printStackTrace();
            }

        });
    }


}

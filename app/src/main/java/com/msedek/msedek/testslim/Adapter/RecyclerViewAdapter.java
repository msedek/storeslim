package com.msedek.msedek.testslim.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.msedek.msedek.testslim.Models.Cliente;
import com.msedek.msedek.testslim.Models.Vehiculo;
import com.msedek.msedek.testslim.R;
import com.msedek.msedek.testslim.editarClienteActivity;
import com.msedek.msedek.testslim.editarVehiculoActivity;
import com.msedek.msedek.testslim.listClientVehActivity;


import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.mHolderViewHolder>{

    private ArrayList<Cliente> clientes;
    private ArrayList<Vehiculo> vehiculos;
    private Context context;
    private listClientVehActivity acti;

    @SuppressLint("SetTextI18n")
    public RecyclerViewAdapter(Context context, ArrayList<Cliente> clien, ArrayList<Vehiculo> vehi, TextView tx_no, TextView tx_i, listClientVehActivity activity){

        this.clientes = clien;
        this.vehiculos = vehi;
        this.context = context;
        this.acti    = activity;

        if(vehiculos == null)
        {
            tx_i.setText("DNI");
            tx_no.setText("Nombre del Cliente");
        }
        else
        {
            tx_i.setText("PLACAS");
            tx_no.setText("Marca y Modelo");
        }

    }


    @Override
    public RecyclerViewAdapter.mHolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        return new RecyclerViewAdapter.mHolderViewHolder(LayoutInflater.from(context).inflate(R.layout.clientrow,parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.mHolderViewHolder holder, int position){


        if(vehiculos == null)
        {
            Cliente cliente = clientes.get(position);
            holder.setdata(cliente, null);
        }
        else
        {
            Vehiculo vehiculo = vehiculos.get(position);
            holder.setdata(null, vehiculo);
        }


    }

    @Override
    public int getItemCount(){

        if(vehiculos == null)
        {
            return clientes.size();
        }
        else
        {
            return vehiculos.size();
        }

    }


    public class mHolderViewHolder extends RecyclerView.ViewHolder{

        TextView txt_nombre;
        TextView txt_idem;
        CardView lyo_elpadre;

        public mHolderViewHolder(View itemView){

            super(itemView);

            txt_nombre= itemView.findViewById(R.id.txt_ncli);
            txt_idem= itemView.findViewById(R.id.txt_dncli);
            lyo_elpadre = itemView.findViewById(R.id.lyo_elpadre);

        }

        @SuppressLint("SetTextI18n")
        public void setdata(final Cliente cliente, final Vehiculo vehiculo) {


            if(vehiculo == null)
            {
                txt_nombre.setText(cliente.getCli_nombre() + " " + cliente.getCli_apellido());
                txt_idem.setText(cliente.getCli_dni());

                lyo_elpadre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, editarClienteActivity.class);
                        intent.putExtra("cliente",cliente);
                        intent.putExtra("swmodo", false);
                        intent.putExtra("selector","editar");
                        context.startActivity(intent);
                        acti.finish();
                    }
                });

            }
            else
            {
                txt_nombre.setText(vehiculo.getVeh_marca() + " " + vehiculo.getVeh_modelo());
                txt_idem.setText(vehiculo.getVeh_placa());

                lyo_elpadre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, editarVehiculoActivity.class);
                        intent.putExtra("vehiculo",vehiculo);
                        intent.putExtra("swmodo", false);
                        intent.putExtra("selector","editar");
                        context.startActivity(intent);
                    }
                });

            }

        }
    }


}
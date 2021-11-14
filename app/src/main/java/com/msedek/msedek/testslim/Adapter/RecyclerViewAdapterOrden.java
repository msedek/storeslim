package com.msedek.msedek.testslim.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.msedek.msedek.testslim.Models.Orden;
import com.msedek.msedek.testslim.R;

import java.util.ArrayList;

public class RecyclerViewAdapterOrden extends RecyclerView.Adapter<RecyclerViewAdapterOrden.mHolderViewHolder> {


    private Context context;

    private ArrayList<Orden> ordenes;

    private TextView nrorden;
    private TextView txt_pos;
    private TextView txt_ord;
    private TextView txt_estado;



    @SuppressLint("SetTextI18n")
    public RecyclerViewAdapterOrden(Context con, ArrayList<Orden> ord, TextView txpos, TextView txord, TextView nro, TextView tx_est) {

        this.ordenes = ord;
        this.context = con;
        this.txt_pos = txpos;
        this.txt_ord = txord;
        this.nrorden = nro;
        this.txt_estado = tx_est;

    }


    @Override
    public RecyclerViewAdapterOrden.mHolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new RecyclerViewAdapterOrden.mHolderViewHolder(LayoutInflater.from(context).inflate(R.layout.clientrow, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterOrden.mHolderViewHolder holder, int position) {

        Orden orden = ordenes.get(position);

        holder.setdata(orden, position);

    }

    @Override
    public int getItemCount() {

        return ordenes.size();

    }


    public class mHolderViewHolder extends RecyclerView.ViewHolder {

        TextView txt_nombre;
        TextView txt_idem;
        CardView lyo_elpadre;


        public mHolderViewHolder(View itemView) {

            super(itemView);

            txt_nombre = itemView.findViewById(R.id.txt_ncli);
            txt_idem = itemView.findViewById(R.id.txt_dncli);
            lyo_elpadre = itemView.findViewById(R.id.lyo_elpadre);


        }

        @SuppressLint("SetTextI18n")
        public void setdata(final Orden orden, final int position) {


            txt_nombre.setText(orden.getCli_dni());
            txt_idem.setText(orden.getTal_id());

            lyo_elpadre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    nrorden.setText(orden.getOt_id() + "");
                    txt_ord.setText(orden.getOt_id() + "");
                    txt_pos.setText(position + "");
                    txt_estado.setText(orden.getEstado_orden());


                }
            });

        }
    }

}
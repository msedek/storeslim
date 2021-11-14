package com.msedek.msedek.testslim.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.msedek.msedek.testslim.Models.Cita;
import com.msedek.msedek.testslim.R;

import java.util.ArrayList;

public class RecyclerViewAdapterReser extends RecyclerView.Adapter<RecyclerViewAdapterReser.mHolderViewHolder> {


    private Context context;

    private ArrayList<Cita> reservas;

    private TextView tx_cliDni;
    private TextView tx_fechaReserva;
    private TextView tx_placasVeh;
    private TextView tx_idTaller;
    private TextView tx_servicio;

    private TextView txt_posres;
    private TextView txt_reser;


    @SuppressLint("SetTextI18n")
    public RecyclerViewAdapterReser(Context con, ArrayList<Cita> res, TextView tx_clid, TextView tx_fecha,
                                    TextView tx_placas, TextView tx_idtal, TextView tx_ser, TextView tx_posres, TextView tx_reser) {

        this.reservas = res;
        this.context = con;
        this.tx_cliDni = tx_clid;
        this.tx_fechaReserva = tx_fecha;
        this.tx_placasVeh = tx_placas;
        this.tx_idTaller = tx_idtal;
        this.tx_servicio = tx_ser;
        this.txt_posres = tx_posres;
        this.txt_reser = tx_reser;
    }


    @Override
    public RecyclerViewAdapterReser.mHolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new RecyclerViewAdapterReser.mHolderViewHolder(LayoutInflater.from(context).inflate(R.layout.clientrow, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterReser.mHolderViewHolder holder, int position) {

        Cita cita = reservas.get(position);

        holder.setdata(cita, position);

    }

    @Override
    public int getItemCount() {

        return reservas.size();

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
        public void setdata(final Cita cita, final int position) {


            txt_nombre.setText(cita.getCli_dni());
            txt_idem.setText(cita.getServicio());

            lyo_elpadre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tx_cliDni.setText(cita.getCli_dni());
                    tx_fechaReserva.setText(cita.getFecha());
                    tx_placasVeh.setText(cita.getVeh_placa());
                    tx_idTaller.setText(cita.getTal_id());
                    tx_servicio.setText(cita.getServicio());

                    txt_posres.setText(position + "");
                    txt_reser.setText(cita.getC_id() + "");


                }
            });


        }
    }
}
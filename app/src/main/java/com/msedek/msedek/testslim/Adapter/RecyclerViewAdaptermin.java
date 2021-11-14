package com.msedek.msedek.testslim.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.msedek.msedek.testslim.Models.Cargo;
import com.msedek.msedek.testslim.Models.Categoria;
import com.msedek.msedek.testslim.Models.Estado;
import com.msedek.msedek.testslim.Models.Taller;
import com.msedek.msedek.testslim.R;

import java.util.ArrayList;

public class RecyclerViewAdaptermin extends RecyclerView.Adapter<RecyclerViewAdaptermin.mHolderViewHolder> {

    private ArrayList<Taller> talleres;
    private ArrayList<Cargo> cargos;
    private ArrayList<Categoria> categorias;
    private ArrayList<Estado> estados;
    private Context context;
    private EditText local_id;
    private EditText txt_proex;
    private TextView talid;
    private TextView carid;


    @SuppressLint("SetTextI18n")
    public RecyclerViewAdaptermin(Context context, ArrayList<Taller> tal, ArrayList<Cargo> car, ArrayList<Categoria> cate, ArrayList<Estado> est, EditText txprop,
                                  EditText txex, TextView tid, TextView cid) {

        this.talleres = tal;
        this.context = context;
        this.local_id = txprop;
        this.cargos = car;
        this.estados = est;
        this.categorias = cate;
        this.txt_proex = txex;
        this.talid = tid;
        this.carid = cid;

    }


    @Override
    public RecyclerViewAdaptermin.mHolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new RecyclerViewAdaptermin.mHolderViewHolder(LayoutInflater.from(context).inflate(R.layout.clientrow, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerViewAdaptermin.mHolderViewHolder holder, int position) {

        if(cargos != null)
        {
            Cargo cargo = cargos.get(position);
            holder.setdata(null, cargo, null, null);


        }

        if(talleres != null)
        {


            Taller taller = talleres.get(position);
            holder.setdata(taller, null, null, null);

        }

        if(categorias !=null)
        {
            Categoria categoria = categorias.get(position);
            holder.setdata(null,null, categoria, null);
        }

        if(estados !=null)
        {
            Estado estado = estados.get(position);
            holder.setdata(null,null, null, estado);
        }

    }

    @Override
    public int getItemCount() {

        if(talleres != null)
        {
            return talleres.size();
        }
        if(cargos != null)
        {
            return cargos.size();
        }
        if(categorias != null)
        {
            return categorias.size();
        }
        if(estados != null)
        {
            return estados.size();
        }

        return 0;

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
        public void setdata(final Taller taller, final Cargo cargo, final Categoria categoria, final Estado estado) {


            if(cargo != null)
            {
                txt_idem.setText(cargo.getCar_id() + "");
                txt_nombre.setText(cargo.getCargo());

                lyo_elpadre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_proex.setText(cargo.getCargo());
                        carid.setText(cargo.getCar_id() + "");
                    }
                });
            }


            if(taller != null)
            {
                txt_idem.setText(taller.getTal_id());
                txt_nombre.setText(taller.getTal_nombre());

                lyo_elpadre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        local_id.setText(taller.getTal_id());
                        talid.setText(taller.getTal_id());
                    }
                });
            }

            if(categoria != null)
            {
                txt_idem.setText(categoria.getCat_id() + "");
                txt_nombre.setText(categoria.getCat_nombre());

                lyo_elpadre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        local_id.setText(categoria.getCat_nombre());
                        talid.setText(categoria.getCat_id() + "");
                    }
                });
            }

            if(estado != null)
            {
                txt_idem.setText(estado.getEst_id() + "");
                txt_nombre.setText(estado.getEst_nombre());

                lyo_elpadre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        local_id.setText(estado.getEst_nombre());
                        talid.setText(estado.getEst_id() + "");
                    }
                });
            }
        }
    }
}
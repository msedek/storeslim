package com.msedek.msedek.testslim.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.msedek.msedek.testslim.GestionActivity;
import com.msedek.msedek.testslim.Models.Cargo;
import com.msedek.msedek.testslim.Models.Categoria;
import com.msedek.msedek.testslim.Models.Empleado;
import com.msedek.msedek.testslim.Models.Estado;
import com.msedek.msedek.testslim.Models.Producto;
import com.msedek.msedek.testslim.Models.Servicio;
import com.msedek.msedek.testslim.Models.Taller;
import com.msedek.msedek.testslim.MultiAdminActivity;
import com.msedek.msedek.testslim.R;

import java.io.Serializable;
import java.util.ArrayList;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.mHolderViewHolder>{


    private TextView tx_dato;
    private TextView tx_datoid;
    private Context  context;
    private ArrayList<Taller> locales;
    private ArrayList<Empleado> empleados;
    private ArrayList<Cargo> cargos;
    private ArrayList<Categoria> categorias;
    private ArrayList<Producto> productos;
    private ArrayList<Servicio> servicios;
    private ArrayList<Estado> estados;
    private String setup;
    private GestionActivity gestionActivity;

    @SuppressLint("SetTextI18n")
    public RecyclerViewAdapter2(Context con, ArrayList<Taller> loc, ArrayList<Empleado> emp, ArrayList<Cargo> car, ArrayList<Categoria> cat,
                                ArrayList<Producto> pro, ArrayList<Servicio> ser, ArrayList<Estado> est, TextView txt_dato, TextView txt_datoid, String set, GestionActivity activity){

        this.locales    = loc;
        this.empleados  = emp;
        this.cargos     = car;
        this.categorias = cat;
        this.productos  = pro;
        this.servicios  = ser;
        this. estados   = est;
        this.context    = con;
        this.setup      = set;
        this.tx_dato    = txt_dato;
        this.tx_datoid  = txt_datoid;
        this.gestionActivity = activity;

        switch (setup.toLowerCase())
        {
            case "locales":

                tx_dato.setText("LOCALES");
                tx_datoid.setText("ID  LOCAL");

                break;

            case "empleados":

                tx_dato.setText("EMPLEADOS");
                tx_datoid.setText("DNI EMPLEADO");

                break;

            case "cargos":

                tx_dato.setText("CARGOS");
                tx_datoid.setText("ID CARGO");

                break;

            case "categorias":

                tx_dato.setText("CATEGORIAS");
                tx_datoid.setText("ID CATEGORIA");

                break;

            case "productos":

                tx_dato.setText("PRODUCTOS");
                tx_datoid.setText("ID PRODUCTO");

                break;

            case "servicios":

                tx_dato.setText("SERVICIOS");
                tx_datoid.setText("ID SERVICIO");

                break;

            case "estados":

                tx_dato.setText("ESTADOS");
                tx_datoid.setText("ID ESTADO");

                break;

        }

    }


    private void showmessage(String mensaje)
    {
        final Toast toast = Toast.makeText(context, mensaje, Toast.LENGTH_LONG);
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


    @Override
    public RecyclerViewAdapter2.mHolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        return new RecyclerViewAdapter2.mHolderViewHolder(LayoutInflater.from(context).inflate(R.layout.clientrow,parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter2.mHolderViewHolder holder, int position){


        switch (setup.toLowerCase())
        {
            case "locales":

                Taller taller = locales.get(position);
                holder.setdata(taller,null,null,null,null,null,null);

                break;

            case "empleados":

                Empleado empleado = empleados.get(position);
                holder.setdata(null, empleado,null,null,null,null,null);

                break;

            case "cargos":

                Cargo cargo = cargos.get(position);
                holder.setdata(null,null, cargo,null,null,null,null);

                break;

            case "categorias":

                Categoria categoria = categorias.get(position);
                holder.setdata(null,null,null, categoria,null,null,null);

                break;

            case "productos":

                Producto producto = productos.get(position);
                holder.setdata(null,null,null,null, producto,null,null);

                break;

            case "servicios":

                Servicio servicio =  servicios.get(position);
                holder.setdata(null,null,null,null, null, servicio,null);

                break;

            case "estados":

                Estado estado =  estados.get(position);
                holder.setdata(null,null,null,null, null, null, estado);

                break;

        }


    }

    @Override
    public int getItemCount(){

        switch (setup.toLowerCase())
        {
            case "locales":

                return locales.size();

            case "empleados":

                return empleados.size();

            case "cargos":

                return cargos.size();

            case "categorias":

                return categorias.size();

            case "productos":

                return productos.size();

            case "servicios":

                return servicios.size();

            case "estados":

                return estados.size();

            default:

                return 0;
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
        public void setdata(final Taller taller, final Empleado empleado, final Cargo cargo, final Categoria categoria,
                            final Producto producto, final Servicio servicio, final Estado estado) {

            switch (setup.toLowerCase())
            {
                case "locales":

                    txt_nombre.setText(taller.getTal_nombre());
                    txt_idem.setText(taller.getTal_id());

                    lyo_elpadre.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, MultiAdminActivity.class);
                            intent.putExtra("config","recycler");
                            intent.putExtra("selector", "editar");
                            intent.putExtra("selector2","locales");
                            intent.putExtra("local", taller);
                            context.startActivity(intent);
                            gestionActivity.finish();
                        }
                    });

                    break;

                case "empleados":

                    txt_nombre.setText(empleado.getEmp_nombre());
                    txt_idem.setText(empleado.getEmp_dni());

                    lyo_elpadre.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, MultiAdminActivity.class);
                            intent.putExtra("config","recycler");
                            intent.putExtra("selector", "editar");
                            intent.putExtra("selector2","empleados");
                            intent.putExtra("empleado", empleado);
                            context.startActivity(intent);
                            gestionActivity.finish();

                        }
                    });
                    break;

                case "cargos":

                    txt_nombre.setText(cargo.getCargo());
                    txt_idem.setText(cargo.getCar_id() + "");

                    lyo_elpadre.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, MultiAdminActivity.class);
                            intent.putExtra("config","recycler");
                            intent.putExtra("selector", "editar");
                            intent.putExtra("selector2","cargos");
                            intent.putExtra("cargo", cargo);
                            context.startActivity(intent);
                            gestionActivity.finish();

                        }
                    });
                    break;

                case "categorias":

                    txt_nombre.setText(categoria.getCat_nombre());
                    txt_idem.setText(categoria.getCat_id() + "");

                    lyo_elpadre.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, MultiAdminActivity.class);
                            intent.putExtra("config","recycler");
                            intent.putExtra("selector", "editar");
                            intent.putExtra("selector2","categorias");
                            intent.putExtra("categoria", categoria);
                            context.startActivity(intent);
                            gestionActivity.finish();

                        }
                    });
                    break;

                case "productos":

                    txt_nombre.setText(producto.getPro_nombre());
                    txt_idem.setText(producto.getPro_id() + "");

                    lyo_elpadre.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, MultiAdminActivity.class);
                            intent.putExtra("config","recycler");
                            intent.putExtra("selector", "editar");
                            intent.putExtra("selector2","productos");
                            intent.putExtra("producto", producto);
                            context.startActivity(intent);
                            gestionActivity.finish();

                        }
                    });


                    break;

                case "servicios":


                    txt_nombre.setText(servicio.getSer_nombre());
                    txt_idem.setText(servicio.getSer_id() + "");

                    lyo_elpadre.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, MultiAdminActivity.class);
                            intent.putExtra("config","recycler");
                            intent.putExtra("selector", "editar");
                            intent.putExtra("selector2","servicios");
                            intent.putExtra("servicio", servicio);
                            context.startActivity(intent);
                            gestionActivity.finish();

                        }
                    });

                    break;

                case "estados":


                    txt_nombre.setText(estado.getEst_nombre());
                    txt_idem.setText(estado.getEst_id() + "");

                    lyo_elpadre.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, MultiAdminActivity.class);
                            intent.putExtra("config","recycler");
                            intent.putExtra("selector", "editar");
                            intent.putExtra("selector2","estados");
                            intent.putExtra("estado", estado);
                            context.startActivity(intent);
                            gestionActivity.finish();

                        }
                    });

                    break;
            }
        }
    }
}
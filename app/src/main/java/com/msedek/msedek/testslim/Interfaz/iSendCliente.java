package com.msedek.msedek.testslim.Interfaz;




import com.msedek.msedek.testslim.Models.Cargo;
import com.msedek.msedek.testslim.Models.Categoria;
import com.msedek.msedek.testslim.Models.Cita;
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

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface iSendCliente
{

    @GET("api/talleres")
    Call<ArrayList<Taller>> getJSONTalleres();

    @GET("api/clientes")
    Call<ArrayList<Cliente>> getJSONClientes();

    @GET("api/vehiculos")
    Call<ArrayList<Vehiculo>> getJSONVehiculos();

    @GET("api/empleados")
    Call<ArrayList<Empleado>> getJSONEmpleados();

    @GET("api/cargos")
    Call<ArrayList<Cargo>> getJSONCargos();

    @GET("api/categorias")
    Call<ArrayList<Categoria>> getJSONCategorias();

    @GET("api/productos")
    Call<ArrayList<Producto>> getJSONProductos();

    @GET("api/servicios")
    Call<ArrayList<Servicio>> getJSONServicios();

    @GET("api/estados")
    Call<ArrayList<Estado>> getJSONEstados();

    //@Headers({"CONNECT_TIMEOUT:10000", "READ_TIMEOUT:10000", "WRITE_TIMEOUT:10000"})
    @GET("api/citas")
    Call<ArrayList<Cita>> getJSONCitas();

    @GET("api/ordenes")
    Call<ArrayList<Orden>> getJSONOrdenes();

    @GET("api/prorders")
    Call<ArrayList<Prorder>> getJSONProrders();




    @GET("api/cliente/{cli_dni}")
    Call<Cliente> getJSONClienteid(@Path("cli_dni") String cli_dni);

    @GET("api/vehiculo/{placa}")
    Call<Vehiculo> getJSONVehiculoid(@Path("placa") String placa);

    @GET("api/empleado/{emp_dni}")
    Call<Empleado> getJSONEmpleadoid(@Path("emp_dni") String emp_dni);

    @GET("api/cita/{c_id}")
    Call<Cita> getJSONCitaid(@Path("c_id") String c_id);

    @GET("api/cargo/{car_id}")
    Call<Cargo> getJSONCargoid(@Path("car_id") int car_id);

    @GET("api/categoria/{cat_id}")
    Call<Categoria> getJSONCategoriaid(@Path("cat_id") int cat_id);

    @GET("api/estado/{est_id}")
    Call<Estado> getJSONEstadoid(@Path("est_id") int est_id);

    @GET("api/taller/{tal_id}")
    Call<Taller> getJSONTallerid(@Path("tal_id") String tal_id);

    @GET("api/servicio/{ser_id}")
    Call<Servicio> getJSONServicioid(@Path("ser_id") int ser_id);

    @GET("api/producto/{pro_id}")
    Call<Producto> getJSONProductoid(@Path("pro_id") int pro_id);

    @GET("api/orden/{ot_id}")
    Call<Orden> getJSONOrdenid(@Path("ot_id") int or_id);




    @POST("api/cliente/agregar")
    Call<Cliente> addCliente(@Body Cliente cliente);

    @POST("api/servicio/agregar")
    Call<Cita> addCita(@Body Cita Cita);

    @POST("api/vehiculo/agregar")
    Call<Vehiculo> addVehiculo(@Body Vehiculo vehiculo);

    @POST("api/taller/agregar")
    Call<Taller> addTaller(@Body Taller taller);

    @POST("api/orden/agregar")
    Call<Orden> addOrden(@Body Orden orden);

    @POST("api/prorder/agregar")
    Call<Prorder> addProrder(@Body Prorder prorder);

    @POST("api/empleado/agregar")
    Call<Empleado> addEmpleado(@Body Empleado empleado);

    @POST("api/cargo/agregar")
    Call<Cargo> addCargo(@Body Cargo cargo);

    @POST("api/categoria/agregar")
    Call<Categoria> addCategoria(@Body Categoria categoria);

    @POST("api/producto/agregar")
    Call<Producto> addProducto(@Body Producto producto);

    @POST("api/servicio/agregar")
    Call<Servicio> addServicio(@Body Servicio servicio);

    @POST("api/estado/agregar")
    Call<Estado> addEstado(@Body Estado estado);




    @PUT("api/cita/actualizar/{c_id}")
    Call<Cita> updateCita(@Path("c_id") int c_id, @Body Cita cita);

    @PUT("api/cliente/actualizar/{cli_dni}")
    Call<Cliente> updateCliente(@Path("cli_dni") String cli_dni, @Body Cliente cliente);

    @PUT("api/vehiculo/actualizar/{placa}")
    Call<Vehiculo> updateVehiculo(@Path("placa") String placa, @Body Vehiculo vehiculo);

    @PUT("api/taller/actualizar/{tal_id}")
    Call<Taller> updateTaller(@Path("tal_id") String tal_id, @Body Taller taller);

    @PUT("api/empleado/actualizar/{emp_dni}")
    Call<Empleado> updateEmpleado(@Path("emp_dni") String emp_dni, @Body Empleado empleado);

    @PUT("api/cargo/actualizar/{car_id}")
    Call<Cargo> updateCargo(@Path("car_id") String car_id, @Body Cargo cargo);

    @PUT("api/categoria/actualizar/{cat_id}")
    Call<Categoria> updateCategoria(@Path("cat_id") String cat_id, @Body Categoria categoria);

    @PUT("api/producto/actualizar/{pro_id}")
    Call<Producto> updateProducto(@Path("pro_id") String pro_id, @Body Producto producto);

    @PUT("api/servicio/actualizar/{ser_id}")
    Call<Servicio> updateServicio(@Path("ser_id") String ser_id, @Body Servicio servicio);

    @PUT("api/estado/actualizar/{est_id}")
    Call<Estado> updateEstado(@Path("est_id") String est_id, @Body Estado estado);

    @PUT("api/orden/actualizar/{ot_id}")
    Call<Orden> updateOrden(@Path("ot_id") int ot_id, @Body Orden orden);





    @DELETE("api/cliente/borrar/{cli_dni}")
    Call<Cliente> deleteCliente(@Path("cli_dni") String cli_dni);

    @DELETE("api/vehiculo/borrar/{placa}")
    Call<Vehiculo> deleteVehiculo(@Path("placa") String placa);

    @DELETE("api/taller/borrar/{tal_id}")
    Call<Taller> deleteTaller(@Path("tal_id") String tal_id);

    @DELETE("api/empleado/borrar/{emp_dni}")
    Call<Empleado> deleteEmpleado(@Path("emp_dni") String emp_dni);

    @DELETE("api/cargo/borrar/{car_id}")
    Call<Cargo> deleteCargo(@Path("car_id") int car_id);

    @DELETE("api/categoria/borrar/{cat_id}")
    Call<Categoria> deleteCategoria(@Path("cat_id") int cat_id);

    @DELETE("api/producto/borrar/{pro_id}")
    Call<Producto> deleteProducto(@Path("pro_id") int pro_id);

    @DELETE("api/servicio/borrar/{ser_id}")
    Call<Servicio> deleteServicio(@Path("ser_id") int ser_id);

    @DELETE("api/estado/borrar/{est_id}")
    Call<Estado> deleteEstado(@Path("est_id") int est_id);

    @DELETE("api/orden/borrar/{ot_id}")
    Call<Orden> deleteOrden(@Path("ot_id") int ot_id);

    @DELETE("api/prorder/borrar/{pror_id}")
    Call<Prorder> deleteProrder(@Path("pror_id") int pror_id);

    @DELETE("api/cita/borrar/{c_id}")
    Call<Cita> deleteCita(@Path("c_id") int c_id);



}

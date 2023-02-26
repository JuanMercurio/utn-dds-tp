package utn.ddsG8.impacto_ambiental.domain.services.distancia;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface DistanciaAPI {

    @GET("api/paises")
    Call<List<Pais>> paises(@Header("authorization") String token, @Query("offset") int offset);


    @GET("api/provincias")
    Call<List<Provincia>> provincias(@Header("authorization") String token, @Query("offset") int offset);

    @GET("api/provincias")
    Call<List<Provincia>> provincias(@Header("authorization") String token,
                                     @Query("offset") int offset,
                                     @Query("paisId") int paidId);


    @GET("api/localidades")
    Call<List<Localidad>> localidades(@Header("authorization") String token, @Query("offset") int offset);

    @GET("api/localidades")
    Call<List<Localidad>> localidades(@Header("authorization") String token,
                                      @Query("offset") int offset,
                                      @Query("municipioId") int municipioId);


    @GET("api/municipios")
    Call<List<Municipio>> municipios(@Header("authorization") String token, @Query("offset") int offset);

    @GET("api/municipios")
    Call<List<Municipio>> municipios(@Header("authorization") String token,
                                     @Query("offset") int offset,
                                     @Query("provinciaId") int provinciaId);



    @GET("api/distancia")
    Call<Distancia> distancia(@Header("authorization") String token,
                              @Query("localidadOrigenId") int localidadOrigenId,
                              @Query("calleOrigen") String calleOrigen,
                              @Query("alturaOrigen") String alturaOrigen,
                              @Query("localidadDestinoId") int localidadDestinoId,
                              @Query("calleDestino") String calleDestino,
                              @Query("alturaDestino") String alturaDestino);

    @POST("api/user")
    Call<UserRespuesta> user(@Body UserCrear user);

    public <T> T obtener(T objeto);
}

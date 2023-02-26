package utn.ddsG8.impacto_ambiental.domain.services.distancia;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;

import java.io.IOException;
import java.util.List;

public class DistanciaServicio {
    private static DistanciaServicio instancia = null;
    private static final String URL= "https://ddstpa.com.ar/"; //agregar para obtener de archivo de config y set desde el instanciador
    private final Retrofit retrofit;
    private final String token = "Bearer E8iN6xBPXQsUI+M72MfPdVhM/o3axkzywqKZjjOyhe0=";

    private DistanciaServicio() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static DistanciaServicio getInstancia() {
        if (instancia == null) {
            instancia = new DistanciaServicio();
        }
        return instancia;
    }

    public List<Pais> paises(int offset) {
        DistanciaAPI distanciaAPI = this.retrofit.create(DistanciaAPI.class);
        Call<List<Pais>> requestPaises= distanciaAPI.paises(token, offset);
        Response<List<Pais>> responsePaises = null;
        try {
            responsePaises = requestPaises.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responsePaises.body();
    }


    public List<Provincia> provincias(int offset) {
        DistanciaAPI distanciaAPI = this.retrofit.create(DistanciaAPI.class);
        Call<List<Provincia>> requestProvincia= distanciaAPI.provincias(token, offset);
        Response<List<Provincia>> responseProvincia = null;
        try {
            responseProvincia = requestProvincia.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseProvincia.body();
    }

    public List<Provincia> provincias(int offset, int pais) {
        DistanciaAPI distanciaAPI = this.retrofit.create(DistanciaAPI.class);
        Call<List<Provincia>> requestProvincia= distanciaAPI.provincias(token, offset, pais);
        Response<List<Provincia>> responseProvincia = null;
        try {
            responseProvincia = requestProvincia.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseProvincia.body();
    }


    public List<Localidad> localidades(int offset) {
        DistanciaAPI distanciaAPI = this.retrofit.create(DistanciaAPI.class);
        Call<List<Localidad>> requestLocalidad = distanciaAPI.localidades(token, offset);
        Response<List<Localidad>> responseLocalidad = null;
        try {
            responseLocalidad = requestLocalidad.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseLocalidad.body();
    }

    public List<Localidad> localidades(int offset, int municipio) {
        DistanciaAPI distanciaAPI = this.retrofit.create(DistanciaAPI.class);
        Call<List<Localidad>> requestLocalidad = distanciaAPI.localidades(token, offset, municipio);
        Response<List<Localidad>> responseLocalidad = null;
        try {
            responseLocalidad = requestLocalidad.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseLocalidad.body();
    }


    public List<Municipio> municipios(int offset) {
        DistanciaAPI distanciaAPI = this.retrofit.create(DistanciaAPI.class);
        Call<List<Municipio>> requestMunicipio = distanciaAPI.municipios(token, offset);
        Response<List<Municipio>> responseMunicipio = null;
        try {
            responseMunicipio = requestMunicipio.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseMunicipio.body();
    }

    public List<Municipio> municipios(int offset, int provincia)  {
        DistanciaAPI distanciaAPI = this.retrofit.create(DistanciaAPI.class);
        Call<List<Municipio>> requestMunicipio = distanciaAPI.municipios(token, offset, provincia);
        Response<List<Municipio>> responseMunicipio = null;
        try {
            responseMunicipio = requestMunicipio.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseMunicipio.body();
    }

    public Distancia distancia(Direccion inicio, Direccion fin)  {
        return distancia(inicio.getLocalidad().getId(),
                                inicio.getCalle(),
                                inicio.getAltura(),
                                fin.getLocalidad().getId(),
                                fin.getCalle(),
                                fin.getAltura()
                                );

    }

    public Distancia distancia(int localidadOrigenId,
                               String calleOrigen,
                               String alturaOrigen,
                               int localidadDestinoId,
                               String calleDestino,
                               String alturaDestino)
                                {

        DistanciaAPI distanciaAPI = this.retrofit.create(DistanciaAPI.class);
        Call<Distancia> requestDistancia = distanciaAPI.distancia(token, localidadOrigenId, calleOrigen, alturaOrigen,
                                                                  localidadDestinoId, calleDestino, alturaDestino);
                                    Response<Distancia> responseDistancia = null;
                                    try {
                                        responseDistancia = requestDistancia.execute();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return responseDistancia.body();
    }


    public UserRespuesta crearUsuario(UserCrear user) throws IOException {
        DistanciaAPI distanciaAPI = this.retrofit.create(DistanciaAPI.class);
        Call<UserRespuesta> requestUser = distanciaAPI.user(user);
        Response<UserRespuesta> responseUser = requestUser.execute();
        return responseUser.body();
    }
}

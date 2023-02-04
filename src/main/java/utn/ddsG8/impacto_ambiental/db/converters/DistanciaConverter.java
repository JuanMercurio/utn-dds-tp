package utn.ddsG8.impacto_ambiental.db.converters;

import utn.ddsG8.impacto_ambiental.domain.services.distancia.Distancia;

import javax.persistence.AttributeConverter;

public class DistanciaConverter implements AttributeConverter<Distancia, String> {
    @Override
    public String convertToDatabaseColumn(Distancia distancia) {
        return String.valueOf(distancia.valor) + " " +  distancia.unidad;
    }

    @Override
    public Distancia convertToEntityAttribute(String s) {
        String strings[] = s.split(" ");
        return new Distancia(Double.parseDouble(strings[0]), strings[1]);
    }
}

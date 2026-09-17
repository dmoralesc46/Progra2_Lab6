package lab_api.demo.reservas.dto.request;

public record ReservaHotelRequest(String nombreCliente, String habitacion, String fechaEntrada, String fechaSalida, String estado) {
    
}

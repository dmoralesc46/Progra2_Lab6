package lab_api.demo.reservas.dto.response;

public record ReservaHotelResponse(Long id, String nombreCliente, String habitacion, String fechaEntrada, String fechaSalida, String estado) {
    
}

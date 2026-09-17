package lab_api.demo.reservas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lab_api.demo.reservas.dto.request.ReservaHotelRequest;
import lab_api.demo.reservas.dto.response.ReservaHotelResponse;

@Service 
public class ReservaHotelService {
    
    private final List<ReservaHotelResponse> reservas = new ArrayList<>(
        List.of(
            new ReservaHotelResponse(1L, "Juan Perez", "Habitación 101", "2023-06-01", "2023-06-05", "Confirmada"),
            new ReservaHotelResponse(2L, "Maria Lopez", "Habitación 202", "2023-06-02", "2023-06-06", "Confirmada"),
            new ReservaHotelResponse(3L, "Carlos Sanchez", "Habitación 303", "2023-06-03", "2023-06-07", "Cancelada")
        )
    );

    public List<ReservaHotelResponse> getAllReservas() {
        //Retorna la lista de reservas
        return reservas;
    }

    public ReservaHotelResponse getReservaById(Long id) {
        //Busca una reserva por su id
        return reservas.stream()
                .filter(reserva -> reserva.id().equals(id))
                .findFirst()
                .orElse(null);
    }

    public ReservaHotelResponse createReserva(ReservaHotelRequest reserva) {
        //se verrifica si la reserva ya existe en la lista
        for (ReservaHotelResponse r : reservas) {
            if (r.nombreCliente().equalsIgnoreCase(reserva.nombreCliente()) 
                && r.habitacion().equalsIgnoreCase(reserva.habitacion())
                && r.fechaEntrada().equals(reserva.fechaEntrada())) {
                return null; //si la reserva para el mismo cliente en la misma habitación y fecha ya existe, retorna null
            }
        }

        ReservaHotelResponse newReserva = new ReservaHotelResponse(
            (long) (reservas.size() + 1),
            reserva.nombreCliente(),
            reserva.habitacion(),
            reserva.fechaEntrada(),
            reserva.fechaSalida(),
            reserva.estado()
        );
        reservas.add(newReserva);
        return newReserva;
    }

    public boolean deleteReserva(Long id) {
        //Elimina una reserva de la lista por su id
        return reservas.removeIf(reserva -> reserva.id().equals(id));
    }

    public ReservaHotelResponse updateReserva(Long id, ReservaHotelRequest reserva) {
        //Actualiza una reserva existente en la lista
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).id().equals(id)) {
                ReservaHotelResponse updatedReserva = new ReservaHotelResponse(
                    id,
                    reserva.nombreCliente(),
                    reserva.habitacion(),
                    reserva.fechaEntrada(),
                    reserva.fechaSalida(),
                    reserva.estado()
                );
                reservas.set(i, updatedReserva);
                return updatedReserva;
            }
        }
        return null; //si no se encuentra la reserva, retorna null
    }

}

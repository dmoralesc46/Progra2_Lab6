package lab_api.demo.reservas.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lab_api.demo.reservas.dto.request.ReservaHotelRequest;
import lab_api.demo.reservas.dto.response.ReservaHotelResponse;
import lab_api.demo.reservas.service.ReservaHotelService;


@RestController 
@RequestMapping("/api/reservas")
public class ReservaHotelController {
    private final ReservaHotelService reservaHotelService;

    public ReservaHotelController(ReservaHotelService reservaHotelService) {
        this.reservaHotelService = reservaHotelService;
    }
    
    @GetMapping 
    public ResponseEntity<?> getAllReservas() {
        //Retorna la lista de reservas
        return ResponseEntity.ok(reservaHotelService.getAllReservas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservaById(@PathVariable Long id) {
        //Retorna una reserva por su id
        ReservaHotelResponse reserva = reservaHotelService.getReservaById(id);
        if (reserva == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("Mensaje", "Reserva con ID " + id + " no encontrada",
                    "codigo", 404
                )
            );
        }
        return ResponseEntity.ok(reserva);
    }

    @PostMapping 
    public ResponseEntity<?> createReserva(@RequestBody ReservaHotelRequest reserva) {
        //Crea una nueva reserva
        ReservaHotelResponse newReserva = reservaHotelService.createReserva(reserva);
        if (newReserva == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of("Mensaje", "Reserva para el cliente " + reserva.nombreCliente() + " en la habitación " + reserva.habitacion() + " para la fecha " + reserva.fechaEntrada() + " ya existe",
                    "codigo", 409
                )
            );
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(newReserva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReserva(@PathVariable Long id, @RequestBody ReservaHotelRequest reserva) {
        //Actualiza una reserva existente
        ReservaHotelResponse updatedReserva = reservaHotelService.updateReserva(id, reserva);
        if (updatedReserva == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("Mensaje", "Reserva con ID " + id + " no encontrada",
                    "codigo", 404
                )
            );
        }
        return ResponseEntity.ok(updatedReserva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReserva(@PathVariable Long id) {
        //Elimina una reserva existente
        boolean deleted = reservaHotelService.deleteReserva(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("Mensaje", "Reserva con ID " + id + " no encontrada",
                    "codigo", 404
                )
            );
        }
        return ResponseEntity.ok(Map.of("Mensaje", "Reserva con ID " + id + " eliminada correctamente"));
    }

}

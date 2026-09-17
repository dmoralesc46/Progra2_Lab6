package lab_api.demo.Cursos.controller;

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

import lab_api.demo.Cursos.dto.response.CursoResponse;
import lab_api.demo.Cursos.service.CursoService;

@RestController 
@RequestMapping("/api/cursos")
public class CursoController {
    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping 
    public ResponseEntity<?> getAllCursos() {
        //Retorna la lista de cursos
        return ResponseEntity.ok(cursoService.getAllCursos());
    }

    @GetMapping("/{codigo}") 
    public ResponseEntity<?> getCursoByCodigo(@PathVariable String codigo) {
        CursoResponse curso = cursoService.getCursoByCodigo(codigo);
        if (curso == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("mensaje", "Curso con codigo " + codigo + " no encontrado",
                "codigo", 404                    
                ));
        }
        return ResponseEntity.ok(curso);
    }

    @PostMapping
    public ResponseEntity<?> createCurso(@RequestBody CursoResponse cursoRequest) {
        CursoResponse newCurso = cursoService.createCurso(cursoRequest);
        if (newCurso == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of("mensaje", "Curso con codigo " + cursoRequest.codigo() + " ya existe",
                "codigo", 409                    
                ));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(newCurso);
    }

    @PutMapping("/{id}") 
    public ResponseEntity<?> updateCurso(@PathVariable Long id, @RequestBody CursoResponse cursoRequest) {
        CursoResponse updatedCurso = cursoService.updateCurso(id, cursoRequest);
        if (updatedCurso == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("mensaje", "Curso con id " + id + " no encontrado",
                "codigo", 404                    
                ));
        }
        return ResponseEntity.ok(updatedCurso);
    }

    @DeleteMapping("/{id}") 
    public ResponseEntity<?> deleteCurso(@PathVariable Long id) {
        boolean deleted = cursoService.deleteCurso(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("mensaje", "Curso con id " + id + " no encontrado",
                "codigo", 404                    
                ));
        }
        return ResponseEntity.ok(Map.of("mensaje", "Curso con id " + id + " eliminado correctamente"));
    }

    

}

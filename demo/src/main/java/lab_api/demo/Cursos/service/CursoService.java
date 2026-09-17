package lab_api.demo.Cursos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lab_api.demo.Cursos.dto.response.CursoResponse;

@Service 
public class CursoService {
    
    private final List<CursoResponse> cursos = new ArrayList<>(
        List.of(
            new CursoResponse(1L, "Programación II", "PROG2", 4, "Activo"),
            new CursoResponse(2L, "Base de Datos", "BD", 3, "Activo"),
            new CursoResponse(3L, "Sistemas Operativos", "SO", 4, "Inactivo")
        )
    );

    public List<CursoResponse> getAllCursos() {
        //Retorna la lista de cursos
        return cursos;
    }

    public CursoResponse getCursoByCodigo(String codigo) {
        //Busca un curso por su código
        return cursos.stream()
                .filter(curso -> curso.codigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }

    public CursoResponse createCurso(CursoResponse cursoRequest) {
        //se verrifica si el curso ya existe en la lista
        for (CursoResponse curso : cursos) {
            if (curso.codigo().equalsIgnoreCase(cursoRequest.codigo())) {
                return null; //si el curso ya existe, retorna null
            }
        }

        CursoResponse newCurso = new CursoResponse(
            (long) (cursos.size() + 1),
            cursoRequest.nombre(),
            cursoRequest.codigo(),
            cursoRequest.creditos(),
            cursoRequest.estado()
        );
        cursos.add(newCurso);
        return newCurso;
    }

    public boolean deleteCurso(Long id) {
        //Elimina un curso de la lista por su id
        return cursos.removeIf(curso -> curso.id().equals(id));
    }

    public CursoResponse updateCurso(Long id, CursoResponse cursoRequest) {
        //Actualiza un curso existente en la lista
        for (int i = 0; i < cursos.size(); i++) {
            if (cursos.get(i).id().equals(id)) {
                CursoResponse updatedCurso = new CursoResponse(
                    id,
                    cursoRequest.nombre(),
                    cursoRequest.codigo(),
                    cursoRequest.creditos(),
                    cursoRequest.estado()
                );
                cursos.set(i, updatedCurso);
                return updatedCurso;
            }
        }
        return null; //si no se encuentra el curso, retorna null
    }

}

package lab_api.demo.libros.dto.response;

public record BookResponse(Long id, String title, String author, int publicationYear, String status, String isbn) {
    
}

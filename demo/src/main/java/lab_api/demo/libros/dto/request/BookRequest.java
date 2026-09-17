package lab_api.demo.libros.dto.request;

public record BookRequest(String title, String author, int publicationYear, String status, String isbn) {
    
}

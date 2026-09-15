package lab_api.demo.dto.response;

public record BookResponse(Long id, String title, String author, int publicationYear, String status, String isbn) {
    
}

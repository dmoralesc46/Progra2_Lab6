package lab_api.demo.dto.request;

public record BookRequest(String title, String author, int publicationYear, String status, String isbn) {
    
}

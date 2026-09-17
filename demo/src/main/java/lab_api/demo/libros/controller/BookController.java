package lab_api.demo.libros.controller;

import java.util.List;
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

import lab_api.demo.libros.dto.request.BookRequest;
import lab_api.demo.libros.dto.response.BookResponse;
import lab_api.demo.libros.service.BookService;


@RestController 
@RequestMapping("api/libros")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {

        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{title}")
    public ResponseEntity<?> getBookById(@PathVariable String title) {
        BookResponse book = bookService.getBookByTitle(title);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "El libro con título " + title + " no fue encontrado",
                    "codigo", 404
                )
            ); 
        }
    }

    @PostMapping
    public ResponseEntity<?>  createBook(@RequestBody BookRequest bookRequest) {
        BookResponse createdBook = bookService.createBook(bookRequest);

        //se verifica si el libro ya existe en la lista, si es así se retorna un mensaje de error
        if (createdBook == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of(
                    "mensaje", "El libro "+ bookRequest.title() + " ya existe",
                    "codigo", 409
                )
            ); 
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        BookResponse updatedBook = bookService.updateBook(id, bookRequest);
        if (updatedBook == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "El libro con ID " + id + " no fue encontrado",
                    "codigo", 404
                )
            );
        }
        return ResponseEntity.ok(updatedBook);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                    "mensaje", "El libro con ID " + id + " no fue encontrado",
                    "codigo", 404
                )
            );
        }
        return ResponseEntity.ok(Map.of("mensaje", "Libro con ID " + id + " eliminado correctamente"));
    }
}

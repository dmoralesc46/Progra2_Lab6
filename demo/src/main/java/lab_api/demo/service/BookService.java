package lab_api.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lab_api.demo.dto.request.BookRequest;
import lab_api.demo.dto.response.BookResponse;

@Service 
public class BookService {


    private List<BookResponse> books = new ArrayList<>(
        List.of(
            new BookResponse(1L, "El Gran Gatsby", "F. Scott Fitzgerald", 1925, "Disponible", "978-0-7432-7356-5"),
            new BookResponse(2L, "Matar a un ruiseñor", "Harper Lee", 1960, "Disponible", "978-0-06-112008-4"),
            new BookResponse(3L, "1984", "George Orwell", 1949, "Disponible", "978-0-452-28423-4")
        )
    );

    public List<BookResponse> getAllBooks() {
        //Retorna la lista de libros
        return books;
    }

    public BookResponse getBookByTitle(String title) {
        //Busca un libro por su título
        return books.stream()
                .filter(book -> book.title().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }



    public BookResponse createBook(BookRequest bookRequest) {
        //se verrifica si el libro ya existe en la lista
        for (BookResponse book : books) {
            if (book.title().equalsIgnoreCase(bookRequest.title())) {
                return null; //si el libro ya existe, retorna null
            }
        }

        BookResponse newBook = new BookResponse(
            (long) (books.size() + 1),
            bookRequest.title(),
            bookRequest.author(),
            bookRequest.publicationYear(),
            bookRequest.status(),
            bookRequest.isbn()
        );
        books.add(newBook);
        return newBook;
    }

    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        //Actualiza un libro existente en la lista
        for (int i = 0; i < books.size(); i++) {
            BookResponse book = books.get(i);
            if (book.id().equals(id)) {
                BookResponse updatedBook = new BookResponse(
                    id,
                    bookRequest.title(),
                    bookRequest.author(),
                    bookRequest.publicationYear(),
                    bookRequest.status(),
                    bookRequest.isbn()
                );
                books.set(i, updatedBook);
                return updatedBook;
            }
        }
        return null;
    }
}

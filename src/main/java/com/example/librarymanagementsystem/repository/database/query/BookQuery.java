package com.example.librarymanagementsystem.repository.database.query;

public class BookQuery {

    public static final String INSERT_BOOK = """
            INSERT INTO FREDRICK_LIBRARY_BOOKS
                (bookTitle, bookAuthor, bookIsbn, bookGenre, bookQuantity)
            VALUES(:bookTitle, :bookAuthor, :bookIsbn, :bookGenre, :bookQuantity)
            """;

    public static final String UPDATE_BOOK = """
            UPDATE FREDRICK_LIBRARY_BOOKS
            SET bookTitle     = COALESCE(NULLIF(:bookTitle, ''), bookTitle),
                bookAuthor    = COALESCE(NULLIF(:bookAuthor, ''), bookAuthor),
                bookIsbn      = COALESCE(NULLIF(:bookIsbn, ''), bookIsbn),
                bookGenre     = COALESCE(NULLIF(:bookGenre, ''), bookGenre),
                bookQuantity  = COALESCE(NULLIF(:bookQuantity, ''), bookQuantity),
                bookUpdatedAt = GETDATE()
            WHERE bookId = :bookId
                AND bookStatus <> 'DELETED'
            """;

    public static final String GET_ALL_BOOKS = """
            SELECT bookId, bookTitle, bookAuthor, bookIsbn, bookGenre, bookQuantity, bookStatus
            FROM FREDRICK_LIBRARY_BOOKS
            WHERE bookStatus <> 'DELETED'
            """;

    public static final String GET_ALL_AVAILABLE_BOOKS = """
            SELECT bookId, bookTitle, bookAuthor, bookIsbn, bookGenre, bookQuantity, bookStatus
            FROM FREDRICK_LIBRARY_BOOKS
            WHERE bookStatus = 'AVAILABLE'
            """;

    public static final String GET_BOOKS_BY_QUERY = """
            SELECT bookId, bookTitle, bookAuthor, bookIsbn, bookGenre, bookQuantity, bookStatus
            FROM FREDRICK_LIBRARY_BOOKS
            WHERE bookStatus <> 'DELETED'
            AND (
                 LOWER(bookTitle)  LIKE LOWER(:query) OR
                 LOWER(bookAuthor) LIKE LOWER(:query) OR
                 LOWER(bookIsbn)   LIKE LOWER(:query) OR
                 LOWER(bookGenre)  LIKE LOWER(:query)
                )
            """;

    public static final String GET_BOOKS_BY_ID = """
            SELECT bookId, bookTitle, bookAuthor, bookIsbn, bookGenre, bookQuantity, bookStatus
            FROM FREDRICK_LIBRARY_BOOKS
            WHERE bookId = :bookId
                AND bookStatus <> 'DELETED'
            """;

    public static final String DELETE_BOOK_BY_ID = """
            UPDATE FREDRICK_LIBRARY_BOOKS
            SET bookStatus = 'DELETED'
            WHERE bookId = :bookId
    """;
}

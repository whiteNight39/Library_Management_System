package com.example.librarymanagementsystem.repository.database.query;

public class BorrowRecordQuery {

    public static final String BORROW_BOOK = """
            INSERT INTO FREDRICK_LIBRARY_BORROW_RECORD
                (borrowUserId, borrowBookId, borrowBorrowDate, borrowDuration, borrowDueDate, borrowStatus)
            VALUES (:borrowUserId, :borrowBookId, GETDATE(), :borrowDuration, DATEADD(DAY, :borrowDuration, GETDATE()), 'BORROWED')
            """;

    public static final String DECREASE_BOOK_QUANTITY = """
            UPDATE FREDRICK_LIBRARY_BOOKS
            SET bookQuantity = bookQuantity - 1
            WHERE bookId = :borrowBookId
            """;

    public static final String RETURN_BOOK_BY_BORROW_ID = """
            UPDATE FREDRICK_LIBRARY_BORROW_RECORD
            SET borrowReturnDate = GETDATE(),
                borrowStatus = 'RETURNED'
            WHERE borrowId = :borrowId
            """;

    public static final String INCREASE_BOOK_QUANTITY = """
            UPDATE FREDRICK_LIBRARY_BOOKS
            SET bookQuantity = bookQuantity + 1
            WHERE bookId = :borrowBookId
            """;

    public static final String GET_ALL_BORROW_RECORDS = """
            SELECT * FROM FREDRICK_LIBRARY_BORROW_RECORD
            """;

    public static final String GET_ALL_ACTIVE_BORROW_RECORDS = """
            SELECT * FROM FREDRICK_LIBRARY_BORROW_RECORD
            WHERE borrowStatus = 'BORROWED'
            """;

    public static final String GET_BORROW_RECORDS_BY_USER_ID = """
            SELECT * FROM FREDRICK_LIBRARY_BORROW_RECORD
            WHERE borrowUserId = :userId
            """;

    public static final String GET_ALL_OVERDUE_BORROW_RECORDS = """
            SELECT * FROM FREDRICK_LIBRARY_BORROW_RECORD
            WHERE
                (borrowStatus = 'OVERDUE')
                OR (borrowStatus = 'BORROWED' AND borrowDueDate < GETDATE())
            """;


}

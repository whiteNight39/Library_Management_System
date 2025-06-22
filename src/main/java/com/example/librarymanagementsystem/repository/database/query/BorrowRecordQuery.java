package com.example.librarymanagementsystem.repository.database.query;

public class BorrowRecordQuery {

    public static final String BORROW_BOOK = """
            INSERT INTO FREDRICK_LIBRARY_BORROW_RECORD
                (borrowUserId, borrowBookId, borrowBorrowDate, borrowDuration, borrowDueDate, borrowStatus)
            SELECT :borrowUserId, :borrowBookId, GETDATE(), :borrowDuration, DATEADD(DAY, :borrowDuration, GETDATE()), 'BORROWED'
            WHERE EXISTS (
                SELECT 1 FROM FREDRICK_LIBRARY_USERS
                WHERE userId = :borrowUserId AND userStatus NOT IN ('BANNED', 'DELETED')
            )
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

    public static final String INCREMENT_USER_CREDIT = """
            UPDATE u
            SET u.userCreditScore = u.userCreditScore + 1
            FROM FREDRICK_LIBRARY_USERS u
            JOIN FREDRICK_LIBRARY_BORROW_RECORD b ON u.userId = b.borrowUserId
            WHERE b.borrowId = :borrowId
                AND b.borrowDueDate >= GETDATE();
            """;

    public static final String GET_ALL_BORROW_RECORDS = """
            SELECT borrowId, borrowUserId, borrowBookId, borrowBorrowDate, borrowReturnDate, borrowDueDate, borrowStatus, borrowDuration
            FROM FREDRICK_LIBRARY_BORROW_RECORD
            """;

    public static final String GET_ALL_ACTIVE_BORROW_RECORDS = """
            SELECT borrowId, borrowUserId, borrowBookId, borrowBorrowDate, borrowReturnDate, borrowDueDate, borrowStatus, borrowDuration
            FROM FREDRICK_LIBRARY_BORROW_RECORD
            WHERE borrowStatus = 'BORROWED'
            """;

    public static final String GET_BORROW_RECORDS_BY_USER_ID = """
            SELECT borrowId, borrowUserId, borrowBookId, borrowBorrowDate, borrowReturnDate, borrowDueDate, borrowStatus, borrowDuration
            FROM FREDRICK_LIBRARY_BORROW_RECORD
            WHERE borrowUserId = :userId
            """;

    public static final String GET_ALL_OVERDUE_BORROW_RECORDS = """
            SELECT borrowId, borrowUserId, borrowBookId, borrowBorrowDate, borrowReturnDate, borrowDueDate, borrowStatus, borrowDuration
            FROM FREDRICK_LIBRARY_BORROW_RECORD
            WHERE
                (borrowStatus = 'OVERDUE')
                OR (borrowStatus = 'BORROWED' AND borrowDueDate < GETDATE())
            """;

    public static final String GET_BORROW_RECORD_BY_ID = """
            SELECT borrowId, borrowUserId, borrowBookId, borrowBorrowDate, borrowReturnDate, borrowDueDate, borrowStatus, borrowDuration
            FROM FREDRICK_LIBRARY_BORROW_RECORD
            WHERE borrowId = :borrowId
            """;

    public static final String GET_BORROW_RECORDS_BY_BOOK_ID = """
            SELECT borrowId, borrowUserId, borrowBookId, borrowBorrowDate, borrowReturnDate, borrowDueDate, borrowStatus, borrowDuration
            FROM FREDRICK_LIBRARY_BORROW_RECORD
            WHERE borrowBookId = :bookId
            """;

    public static final String MARK_OVERDUE_BORROW_RECORDS = """
            UPDATE FREDRICK_LIBRARY_BORROW_RECORD
            SET
                borrowStatus = 'OVERDUE',
                penaltyStage = 'STAGE_1',
                borrowUpdatedAt = GETDATE()
            WHERE 
                borrowStatus = 'BORROWED' 
                AND borrowDueDate < GETDATE()
            """;

    public static final String SET_PENALTY_STAGE = """
            UPDATE FREDRICK_LIBRARY_BORROW_RECORD
            SET
                borrowPenaltyStage = :borrowPenaltyStage
            WHERE borrowId = :borrowId
            """;

    public static final String SET_BORROW_PENALIZED = """
            UPDATE FREDRICK_LIBRARY_BORROW_RECORD
            SET
                borrowPenalized = :borrowPenalized
            WHERE borrowId = :borrowId
            """;

}

package com.example.librarymanagementsystem.repository.database.query;

public class UserQuery {
    
    public static final String INSERT_USER = """
            INSERT INTO FREDRICK_LIBRARY_USERS
                (userFirstName, userLastName, userEmail)
            VALUES(:userFirstName, :userLastName, :userEmail)
            """;

    public static final String UPDATE_USER = """
            UPDATE FREDRICK_LIBRARY_USERS
            SET userFirstName   = COALESCE(NULLIF(:userFirstName, ''), userFirstName),
                userLastName    = COALESCE(NULLIF(:userLastName, ''), userLastName),
                userEmail       = COALESCE(NULLIF(:userEmail, ''), userEmail),
                userUpdatedAt   = GETDATE()
            WHERE userId = :userId
            """;


    public static final String GET_ALL_USERS = """
            SELECT userId, userFirstName, userLastName, userEmail, userCreditScore, userStatus
            FROM FREDRICK_LIBRARY_USERS
            WHERE userStatus <> 'BANNED'
                AND userStatus <> 'DELETED'
            """;

    public static final String GET_USERS_BY_ID = """
            SELECT userId, userFirstName, userLastName, userEmail, userCreditScore, userStatus
            FROM FREDRICK_LIBRARY_USERS
            WHERE userId = :userId
            """;

    public static final String GET_USERS_BY_STATUS = """
            SELECT userId, userFirstName, userLastName, userEmail, userCreditScore, userStatus
            FROM FREDRICK_LIBRARY_USERS
            WHERE userStatus = COALESCE(:status, 'ACTIVE')
            """;

    public static final String GET_USERS_WITH_LOW_CREDIT = """
            SELECT userId, userFirstName, userLastName, userEmail, userCreditScore, userStatus
            FROM FREDRICK_LIBRARY_USERS
            WHERE userCreditScore <= -5
            """;

    public static final String GET_USERS_BY_QUERY = """
            SELECT userId, userFirstName, userLastName, userEmail, userCreditScore, userStatus
            FROM FREDRICK_LIBRARY_USERS
            WHERE userStatus <> 'DELETED'
              AND (
                  LOWER(userFirstName) LIKE LOWER(:query) OR
                  LOWER(userLastName)  LIKE LOWER(:query) OR
                  LOWER(userEmail)     LIKE LOWER(:query)
              )
            """;

    public static final String BAN_USER_BY_ID = """
            UPDATE FREDRICK_LIBRARY_USERS
            SET userStatus = 'BANNED'
            WHERE userId = :userId
                AND userStatus = 'ACTIVE'
    """;

    public static final String UNBAN_USER_BY_ID = """
            UPDATE FREDRICK_LIBRARY_USERS
            SET userStatus = 'ACTIVE'
            WHERE userId = :userId
                AND userStatus = 'BANNED'
    """;

    public static final String DELETE_USER_BY_ID = """
            UPDATE FREDRICK_LIBRARY_USERS
            SET userStatus = 'DELETED'
            WHERE userId = :userId
    """;

    public static final String DECREMENT_USER_CREDIT = """
            UPDATE FREDRICK_LIBRARY_USERS
            SET userCreditScore = userCreditScore - :amount
            WHERE userId = :userId
            """;
}

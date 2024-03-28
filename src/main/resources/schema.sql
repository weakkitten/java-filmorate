CREATE TABLE IF NOT EXISTS PUBLIC.FILMS (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	NAME VARCHAR_IGNORECASE(100) NOT NULL,
	DESCRIPTION VARCHAR_IGNORECASE(200) NOT NULL,
	RELEASEDATE VARCHAR_IGNORECASE(12) NOT NULL,
	DURATION INTEGER NOT NULL,
	RATE INTEGER,
	LIKECOUNT INTEGER,
	AGERATING VARCHAR_IGNORECASE(5),
	GENRE INTEGER,
	LIKEUSERID VARCHAR_IGNORECASE,
	CONSTRAINT FILMS_PK PRIMARY KEY (ID)
    );
CREATE UNIQUE INDEX IF NOT EXISTS PRIMARY_KEY_88 ON PUBLIC.FILMS (ID);

CREATE TABLE IF NOT EXISTS PUBLIC.USERS (
        ID INTEGER NOT NULL AUTO_INCREMENT,
        EMAIL VARCHAR_IGNORECASE(40) NOT NULL,
        LOGIN VARCHAR_IGNORECASE(20) NOT NULL,
        NAME INTEGER,
        BIRTHDAY DATE NOT NULL,
        COLUMN1 JSON,
        CONSTRAINT USERS_PK PRIMARY KEY (ID),
        CONSTRAINT USERS_UNIQUE UNIQUE (EMAIL),
        CONSTRAINT USERS_UNIQUE_1 UNIQUE (LOGIN)
    );
CREATE UNIQUE INDEX IF NOT EXISTS PRIMARY_KEY_4 ON PUBLIC.USERS (ID);
CREATE UNIQUE INDEX IF NOT EXISTS USERS_UNIQUE_1_INDEX_4 ON PUBLIC.USERS (LOGIN);
CREATE UNIQUE INDEX IF NOT EXISTS USERS_UNIQUE_INDEX_4 ON PUBLIC.USERS (EMAIL);


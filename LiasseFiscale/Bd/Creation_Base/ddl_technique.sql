/* GENERATOR */
CREATE GENERATOR NUM_ID_ACCES;
CREATE GENERATOR NUM_ID_VERSION;
CREATE GENERATOR NUM_ID_MODIF;

/* TABLES */
CREATE TABLE TA_ACCES (
    ID_ACCES           DID3 primary key /* DID3 = INTEGER NOT NULL */,
    PASSWORD_ACCES     DLIB50NN DEFAULT 'PASSWORD' /* DLIB50NN = VARCHAR(50) NOT NULL */,
    IP_ACCES           DLIB50NN DEFAULT 'current_connection' /* DLIB50NN = VARCHAR(50) NOT NULL */,
    USER_ACCES         DLIB50NN DEFAULT 'USER' /* DLIB50NN = VARCHAR(50) NOT NULL */,
    QUI_CREE_ACCES     DLIB50 /* DLIB50 = VARCHAR(50) */,
    QUAND_CREE_ACCES   TIMESTAMP DEFAULT 'NOW',
    QUI_MODIF_ACCES    DLIB50 /* DLIB50 = VARCHAR(50) */,
    QUAND_MODIF_ACCES  TIMESTAMP DEFAULT 'NOW',
    "VERSION"          NUM_VERSION /* NUM_VERSION = VARCHAR(20) */
);

CREATE TABLE TA_VERSION (
    ID_VERSION           DID3 primary key /* DID3 = INTEGER NOT NULL */,
    NUM_VERSION          DLIB255NN /* DLIB255NN = VARCHAR(255) NOT NULL */,
    OLD_VERSION          DLIB255NN /* DLIB255NN = VARCHAR(255) NOT NULL */,
    QUI_CREE_VERSION     DLIB50 /* DLIB50 = VARCHAR(50) */,
    QUAND_CREE_VERSION   TIMESTAMP DEFAULT 'NOW',
    QUI_MODIF_VERSION    DLIB50 /* DLIB50 = VARCHAR(50) */,
    QUAND_MODIF_VERSION  TIMESTAMP DEFAULT 'NOW',
    IP_ACCES             DID_FACULTATIF /* DID_FACULTATIF = INTEGER */
);


CREATE TABLE TA_MODIF (
    ID_MODIF           DID3 primary key /* DID3 = INTEGER NOT NULL */,
    TABLE_MODIF        DLIB255NN /* DLIB255NN = VARCHAR(255) NOT NULL */,
    CHAMP_MODIF        DLIB255NN /* DLIB255NN = VARCHAR(255) NOT NULL */,
    VALEUR_MODIF       DLIB255NN /* DLIB255NN = VARCHAR(255) NOT NULL */,
    IP_ACCES           DLIB50NN DEFAULT 0 /* DLIB50NN = VARCHAR(50) NOT NULL */,
    QUI_CREE_MODIF     DLIB50 /* DLIB50 = VARCHAR(50) */,
    QUAND_CREE_MODIF   TIMESTAMP DEFAULT 'NOW',
    QUI_MODIF_MODIF    DLIB50 /* DLIB50 = VARCHAR(50) */,
    QUAND_MODIF_MODIF  TIMESTAMP DEFAULT 'NOW',
    "VERSION"          NUM_VERSION /* NUM_VERSION = VARCHAR(20) */
);
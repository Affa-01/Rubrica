CREATE TABLE persone (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    cognome VARCHAR(100),
    indirizzo VARCHAR(255),
    telefono VARCHAR(20),
    eta INT
);
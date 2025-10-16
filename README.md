# Rubrica

> Progetto Java per la gestione di una rubrica di contatti

## Aggiunte Extra

- Persistenza mantenuta tramite database MySQL
- Export possibili nei formati csv, json, xml e yml

## Compilazione
Creare il jar tramite
```
gradle fatJar
```
che si troverà in `app\build\libs\app-all.jar`, per eseguirlo deve trovarsi nella stessa cartella di `credenziali_database.properties`

## Configurazione

- File credenziali DB: `credenziali_database.properties` — inserisci host, porta, database, username e password.
- Schema DB: `schema_database.sql` — script per creare le tabelle richieste.

## Esecuzione

Il file jar può essere eseguito `java --jar`

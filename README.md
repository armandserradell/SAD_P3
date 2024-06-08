# SAD_P3

En aquesta pràctica s'ha desenvolupat un xat client-servidor utilitzant la biblioteca Swing i l'API NIO (Non-blocking I/O) de Java.

El servidor empra NIO per gestionar múltiples clients de manera eficient mitjançant un Selector. Accepta noves connexions i registra clients per a operacions de lectura/escriptura.

La interfície gràfica del client (SwingClient) utilitza Swing per crear una GUI amb una àrea de missatges i una llista d'usuaris. Permet als usuaris enviar missatges al servidor i actualitza la llista d'usuaris connectats cada cop que es rep un missatge.

La llista d'usuaris a la interfície gràfica (JListSW) gestiona una llista d'usuaris connectats amb un JList. Permet afegir i eliminar usuaris de la llista.

Utilitza SwingClient per gestionar la interfície gràfica del client. Aquesta connexió inicia un fil per llegir missatges del servidor i actualitzar la interfície gràfica.

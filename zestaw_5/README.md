# Zestaw 5 - Threads & Sockets

Opis Klas:

## ThreadLogger

- służy do logowania informacji serwerowych 
- drukowane informacje są możliwie jak najczytelniejsze, poprzedzone znakiem `@`

## Message 

- klasa służąca do obsługi wiadomości tekstowych wysyłanych między klientem i serwerem
- definiuje komendę wyjścia `EXIT` dla klienta

## Server

- obsługa przychodzących połączeń
- odbieranie i rozgłaszanie wiadomości
- rozszerza klasę `ThreadLogger`
- zawiera podklasy `Connection`, `Broadcaster`
- definiuje listę połączonych klientów 
- definiuje kolejkę wiadomości

### Server - Connection

- podklasa definiująca połączenie z pojedynczym klientem
- odbiera i dodaje do kolejki wiadomości odebrane
- rozszerza klasę `ThreadLogger`
- obsługuje komendę `EXIT` rozłączenia klienta

### Server - Broadcaster

- podklasa 'broadcastująca' kolejkę wiadomości do klientów
- po kolei zdejmuje z kolejki i iteruje po połączonych klientach 
- rozszerza klasę `ThreadLogger`

## Client

- łączy się z serwerem
- czyta wiadomości użytkownika i wysyła
- rozszerza klasę `ThreadLogger`
- zawiera podklasę `Receiver`

### Client - Receiver

- podklasa odbierająca wiadomości serwera i wypisująca je na ekranie
- rozszerza klasę `ThreadLogger`
- 'prompt' odebranej wiadomości to `+++`

## ServerApp

Aplikacja uruchamiająca `Server`

## ClientApp

Aplikacja uruchamiająca `Client`

# Użytkowanie

1. w celu uruchomienia serwera:
```
javac ServerApp.java
java ServerApp
```
2. w celu uruchomienia klienta:
```
javac ClientApp.java
java ClientApp
```
3. aby uruchomić więcej klientów należy otworzyć nowe okno terminala:
```
java ClientApp
```
4. można powtórzyć krok 3. kilka razy
5. przełączać się pomiędzy otwartymi oknami terminala z instancjami klienta i wpisywać wiadomości
6. aby wyjść z klienta należy wpisać `EXIT`


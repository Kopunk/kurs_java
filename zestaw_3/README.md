Zadanie 3.8

Plik Main zawiera klasy: RsaReader, RsaWriter wykorzystujące szyfrowanie RSA, z możliwośćią zapisywania tekstu do pliku.

### Test szyfrowania:

```
javac Main.java
javac TestEncryption.java
java TestEncryption
```

- do katalogu mydata zostaną zapisane klucze prywatny i publiczny
- do pliku lorem.data zotanie zapisana zaszyfrowania wiadomość

### Test Deszyfrowania:

```
javac Main.java # niepotrzebne jeśli wykonano już powyższe
javac TestDecryption.java
java TestDecryption
```

- na ekranie zostanie wyświetlona odczytana odszyfrowana wiadomość

**1. Gra w wisielca**

**Spis Treści**
1. Wprowadzenie
2. Lista Funkcjonalności
3. Instrukcja Obsługi
4. Wymagania Systemowe

 

 .1. **Wprowadzenie**
   
Gra w wisielca to klasyczna gra słowna, w której celem jest odgadnięcie ukrytego słowa poprzez podawanie liter. Program ten jest implementacją gry w języku Java z użyciem interfejsu graficznego (Swing).


 2. **Lista Funkcjonalności**
   
Losowanie słowa:
Gra losuje słowo z przygotowanej listy słów na początku każdej gry.

Ustawianie poziomu trudności:
Gracz może wybrać jeden z trzech poziomów trudności (łatwy, średni, trudny), co wpływa na liczbę dostępnych prób.

Interfejs użytkownika:
Gra posiada graficzny interfejs użytkownika z polami tekstowymi, przyciskami i etykietami informującymi o postępie gry.

Wprowadzanie liter:
Gracz może wprowadzać litery za pomocą pola tekstowego. Program sprawdza, czy litera już była używana i czy jest poprawna.

Wyświetlanie zgadywanego słowa:
Gra wyświetla aktualny stan zgadywanego słowa, używając podkreśleń dla niezgadniętych liter.

Historia zgadywanych liter:
Program prowadzi historię zgadywanych liter i wyświetla ją w panelu bocznym.

Licznik pozostałych prób:
Wyświetlana jest liczba pozostałych prób.

Zapisywanie i ładowanie historii gry:
Program zapisuje i ładuje historię wygranych i przegranych gier do/z pliku game_history.txt.

Zakończenie gry i ponowna gra:
Po zakończeniu gry (wygranej lub przegranej) program pyta, czy gracz chce zagrać ponownie.

Dostosowanie wyglądu:
Elementy interfejsu użytkownika mają dostosowane kolory i czcionki dla lepszej czytelności.

 3. **Instrukcja Obsługi**

Uruchomienie gry:
Po uruchomieniu programu zostaniesz poproszony o wybranie poziomu trudności (łatwy, średni, trudny).

Wprowadzenie litery:
Wprowadź literę w polu tekstowym i naciśnij przycisk "Zatwierdź" lub wciśnij klawisz "Enter". Litery mogą być wprowadzane tylko pojedynczo.

Historia liter:
Użyte litery będą wyświetlane w panelu "Użyte litery".

Koniec gry:
Gra kończy się, gdy odgadniesz całe słowo lub gdy skończą się próby. Po zakończeniu gry zostaniesz zapytany, czy chcesz zagrać ponownie.

Zamknięcie gry:
Zamknij program, klikając na przycisk zamykania okna. Historia gier zostanie automatycznie zapisana.

 4. **Wymagania Systemowe**

   
Java Development Kit (JDK) w wersji 8 lub nowszej
System operacyjny: Windows, macOS, Linux

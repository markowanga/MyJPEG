# MyJPEG
Własna implementacja kompresji zdjęć JPEG. Rozrzerzenie pliku to .dmjpg

Kod kompresujący i dekompresujący został zaimplementowany w Javie, natomiast graficznny interface został napisany w C# z wykorzystaniem Windows Forms (aplikacja przenaczona tylko na systemy Windows)

## Teoria oraz wyniki kompresji
Cała teroria odnośnie kompresji zdjęć i dekompresji została zapisana w pliku theory.pdf. 

Do zapisu danych do pliku zostało wykorzystane kodowanie huffmna oraz notacja w której najpierw podajemy liczbę jaka jest zapisana a następnie jej krotność.
Wyjście to wydawaćby się mogło nieoptymalne, lecz w zapisywanych danych mamy doczynienia z bardzo długimi ciągami zer, przez co notacja ta ma bardzo praktyczne zastosowanie.

## Rozmieszczenie kodu
W folderze src znajduję się kompresja / dekompresja napisana w Javie, natomiast w folderze JavaTest znajduje się wyżej opisana aplikacja w C#.

Zdjęcia możemy kompresować użuwając konnsoli w Javie, oraz aplikacji.

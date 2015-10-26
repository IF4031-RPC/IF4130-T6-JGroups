=====================================================<br>
##*   IF4130 - PENGEMBANGAN APLIKASI TERDISTRIBUSI   *<br>
###*             13512052 - 13513601                 *<br>
###*                Try Ajitiono                     *<br>
###*        Akhmad Fakhoni Listiyan Dede             *<br>
=====================================================<br>

###Skenario tes untuk ReplicateStack: <br>

- Jalankan client<br>
- Ada 4 pilihan yang tersedia, /push <x>, /pop, /top, dan quit/exit<br>
- Push digunakan untuk memasukkan sesuatu ke atas stack<br>
- Pop digunakan untuk mengeluarkan sesuatu dari atas stack<br>
- Top digunakan untuk melihat sesuatu yang ada di atas stack<br>
- Quit/exit digunakan untuk keluar dari aplikasi<br>
- Ketik "/push 2"<br>
- Ketik "/push 3"<br>
- Jalankan client lain<br>
- Ketik "/top", maka akan muncul notifikasi bahwa 3 ada di atas stack
- Ketik "/pop", maka 3 akan keluar dari stack, menyisakan 2 di dalam stack
- Untuk memastikan hal tersebut, ketik "/top" yang akan menampilkan bahwa 2 ada di top stack

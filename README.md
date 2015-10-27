=====================================================<br>
##*   IF4130 - PENGEMBANGAN APLIKASI TERDISTRIBUSI   *<br>
###*             13512052 - 13513601                 *<br>
###*                Try Ajitiono                     *<br>
###*        Akhmad Fakhoni Listiyan Dede             *<br>
=====================================================<br>

###Skenario tes untuk ReplicateStack: <br>

- Jalankan client<br>
- Ada 4 pilihan yang tersedia, /push <x>, /pop, /top, dan /quit, /exit<br>
- Push digunakan untuk memasukkan sesuatu ke atas stack<br>
- Pop digunakan untuk mengeluarkan sesuatu dari atas stack<br>
- Top digunakan untuk melihat sesuatu yang ada di atas stack<br>
- Quit/exit digunakan untuk keluar dari aplikasi<br>
- Ketik "/push 2"<br>
- Ketik "/push 3"<br>
- Jalankan client lain<br>
- Ketik "/top", maka akan muncul notifikasi bahwa 3 ada di atas stack<br>
- Ketik "/pop", maka 3 akan keluar dari stack, menyisakan 2 di dalam stack<br>
- Untuk memastikan hal tersebut, ketik "/top" yang akan menampilkan bahwa 2 ada di top stack<br>

###Skenario tes untuk ReplicateSet: <br>
- Jalankan client<br>
- Ada <br>
	- /add		: Untuk menambahkan item ke dalam queue<br>
	- /contains	: Untuk mengecek apakah suatu item ada di dalam queue<br>
	- /remove	: Untuk menghapus suatu item pada suatu queue<br>
	- /quit		: Untuk keluar dari program<br>
- Langkah pengetesan<br>
	- jalankan client1<br>
	- /add 1, /add 2, /add 3<br>
	- jalankan client2, maka akan tercetak elemen 1,2,3 di dalam set ke layar<br>
	- jalankan /add 4, /add 5 pada client 2<br>
	- jalankan client3, maka akan tercetak elemen 1,2,3,4,5 di dalam set ke layar<br>
	- ketik di client3, "/contains 3", maka akan mendapat respon '"3" ada di dalam set'<br>
	- ketik di client3, "/contains 7", maka akan mendapat respon '"7" tidak ada di dalam set'<br>
	- jalankan client4, maka akan tercetak elemen 1,2,3,4,5 di dalam set ke layar<br>
	- ketik di client4, "/contains 2", maka akan mendapat respon '"2" ada di dalam set'<br>
	- ketik di client4, "/remove 2"<br>
	- ketik di client4, "/contains 2", maka akan mendapat respon '"2" tidak ada di dalam set.'<br>
	- ketik "/quit" di semua client untuk keluar dari program<br>

Project secara utuh dapat dilihat di github https://github.com/IF4031-RPC/IF4130-T6-JGroups

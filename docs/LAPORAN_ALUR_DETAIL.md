zbis# LAPORAN ALUR SISTEM CLEANHUB
## Sistem Manajemen Laundry — Mata Kuliah Pemrograman Berorientasi Objek

---

**Nama Aplikasi:** CleanHub  
**Bahasa Pemrograman:** Java  
**Database:** MySQL (phpMyAdmin / XAMPP)  
**Frontend:** HTML, CSS, JavaScript  
**Backend:** Java (LaundryService, Repository, API Handler)  

---

## DAFTAR ISI

1. Pendahuluan
2. Persiapan Sistem
3. Menjalankan Aplikasi
4. Arsitektur Sistem
5. Alur Halaman Web Pertama Kali Dibuka
6. Alur Login
7. Alur Registrasi
8. Alur Customer
9. Alur Staff
10. Alur Admin
11. Alur Mode Konsol
12. Logout dan Keluar Aplikasi
13. Daftar Class PBO
14. Daftar Endpoint API
15. Kesimpulan Alur Data

---

## 1. PENDAHULUAN

CleanHub adalah aplikasi sistem manajemen laundry berbasis web yang dibangun dengan konsep Pemrograman Berorientasi Objek (PBO). Aplikasi ini memiliki tiga jenis pengguna (Customer, Staff, Admin) dan terhubung ke database MySQL untuk penyimpanan data permanen.

Dokumen ini menjelaskan **alur detail** bagaimana proyek CleanHub berjalan, mulai dari persiapan sistem hingga setiap fitur dijalankan oleh pengguna.

---

## 2. PERSIAPAN SISTEM

**A.** User menyalakan **XAMPP** dan menekan tombol **Start** pada modul **MySQL**.

**B.** User membuka **phpMyAdmin** di browser melalui alamat `http://localhost/phpmyadmin`.

**C.** User melakukan import file **`database/cleanhub.sql`** ke phpMyAdmin.

**D.** Database **`cleanhub`** terbentuk beserta tabel-tabel berikut:
- `users` — menyimpan data Customer, Staff, dan Admin
- `services` — menyimpan katalog layanan laundry
- `orders` — menyimpan data pesanan
- `order_services` — menyimpan detail layanan per pesanan

**E.** Data awal (seed data) otomatis dimasukkan ke database, antara lain:
- User: C-001 (Budi, member), C-002 (Sari), S-001 (Andi), A-001 (Nadia)
- Layanan: Cuci Kering (Rp 30.000), Setrika (Rp 20.000), Cuci Express (Rp 45.000)

**F.** User memastikan file konfigurasi **`TubesTest/src/main/resources/database.properties`** sudah benar:
- URL: `jdbc:mysql://localhost:3306/cleanhub`
- User: `root`
- Password: sesuai konfigurasi MySQL (default XAMPP kosong)

---

## 3. MENJALANKAN APLIKASI

**G.** User menjalankan aplikasi melalui salah satu cara berikut:
- Double-click file **`TubesTest/run.bat`**, atau
- Menjalankan class **`com.mycompany.tubestest.TubesTest`** dari NetBeans/IDE

**H.** Script **`run.bat`** melakukan proses:
1. Compile seluruh file Java ke folder `out/`
2. Menyalin `database.properties` ke folder `out/`
3. Mematikan proses lama di port 8080 (jika masih berjalan)
4. Menjalankan class **`TubesTest`**

**I.** Class **`TubesTest.main()`** berfungsi sebagai **Main Class** (entry point) aplikasi.

**J.** `TubesTest` memanggil **`CleanHubWebServer.start(8080)`** untuk menyalakan server web.

**K.** `CleanHubWebServer` membuat **HTTP Server** pada alamat **`http://localhost:8080`**.

**L.** Saat startup, class **`LaundryService`** (Singleton) melakukan tes koneksi ke MySQL melalui **`DatabaseConnection`**.

**M.** Jika koneksi berhasil, terminal menampilkan pesan:
`[DB] Terhubung ke MySQL — database cleanhub.`

**N.** User membuka browser dan mengakses **`http://localhost:8080/`**.
> **Catatan:** Aplikasi harus diakses melalui server, bukan dengan membuka file HTML langsung dari folder.

**O.** Server menampilkan file **`web/index.html`** sebagai halaman utama aplikasi.

---

## 4. ARSITEKTUR SISTEM

**P.** Aplikasi CleanHub terdiri dari empat lapisan utama:

| Lapisan | Komponen | Fungsi |
|---------|----------|--------|
| Presentation | `web/index.html` | Antarmuka pengguna (UI) |
| Web/API | `CleanHubWebServer`, `LaundryApiHandler` | Menerima request HTTP dari browser |
| Business Logic | `LaundryService`, class PBO | Logika bisnis aplikasi |
| Data Access | Repository, `DatabaseConnection` | Akses database MySQL |

**Q.** Alur komunikasi antar lapisan:

```
Browser (index.html)
  → HTTP Request (/api/...)
    → CleanHubWebServer
      → LaundryApiHandler
        → LaundryService
          → UserRepository / OrderRepository / ServiceRepository
            → DatabaseConnection (JDBC)
              → MySQL (database cleanhub)
```

**R.** **API (Application Programming Interface)** adalah kumpulan endpoint (alamat URL) buatan sendiri di Java, misalnya `/api/login`, `/api/register`, `/api/orders`.

**S.** Frontend memanggil API menggunakan JavaScript **`fetch()`**, bukan langsung ke database.

**T.** Class **`LaundryApiServlet`** tersedia sebagai implementasi konsep Servlet untuk keperluan PBO. Saat menjalankan `run.bat`, yang aktif adalah **`LaundryApiHandler`**.

---

## 5. ALUR HALAMAN WEB PERTAMA KALI DIBUKA

**U.** Browser memuat **`index.html`** dari server.

**V.** JavaScript otomatis memanggil **`GET /api/health`** untuk mengecek backend.

**W.** Jika server aktif, status berubah menjadi: *"Terhubung ke Java + MySQL (cleanhub)"*.

**X.** Jika server tidak aktif, muncul peringatan server offline.

**Y.** Halaman login ditampilkan dengan opsi: Login, Daftar (REG), dan Keluar (EXIT).

---

## 6. ALUR LOGIN

**Z.** User memasukkan ID (contoh: C-001) dan menekan **Masuk**.

**AA.** Browser mengirim request **POST /api/login** dengan body JSON `{ "id": "C-001" }`.

**AB.** `CleanHubWebServer` meneruskan request ke **`LaundryApiHandler`**.

**AC.** Handler memanggil **`LaundryService.login("C-001")`**.

**AD.** `LaundryService` memanggil **`UserRepository.findById()`**.

**AE.** Repository menjalankan query SQL ke tabel **`users`**.

**AF.** Database mengembalikan data user jika ditemukan.

**AG.** Hasil query dipetakan menjadi object Java (`Customer`, `Staff`, atau `Admin`).

**AH.** `LaundryService` mengembalikan object **`User`**.

**AI.** Handler mengubah data menjadi JSON dan mengirim response ke browser.

**AJ.** JavaScript menyimpan data user dan mengarahkan ke dashboard sesuai role.

**AK.** Jika ID tidak ditemukan, browser menampilkan pesan error.

**AL.** Dashboard yang ditampilkan:
- Customer → Dashboard Customer
- Staff → Dashboard Staff
- Admin → Dashboard Admin

---

## 7. ALUR REGISTRASI

**AM.** User menekan **Daftar (REG)** di halaman login.

**AN.** User mengisi form: Role, ID, Nama, dan opsi Member (untuk Customer).

**AO.** User menekan tombol **Daftar**.

**AP.** Browser mengirim **POST /api/register** dengan data JSON.

**AQ.** Handler memanggil **`LaundryService.register()`**.

**AR.** Sistem melakukan validasi: ID tidak kosong, nama tidak kosong, ID belum terdaftar.

**AS.** `UserRepository` menjalankan INSERT ke tabel **`users`**.

**AT.** Data tersimpan permanen di MySQL.

**AU.** Sistem memverifikasi ulang dengan **`findById()`**.

**AV.** Server mengembalikan response sukses.

**AW.** Browser kembali ke halaman login dengan pesan sukses.

---

## 8. ALUR CUSTOMER

### 8.1 Buat Pesanan

**AX.** Customer memilih menu **Buat Pesanan**.

**AY.** Browser memanggil **GET /api/services** untuk mengambil katalog layanan.

**AZ.** Customer memilih layanan. Jika member, diskon 10% dihitung (`MembershipPayment`).

**BA.** Customer menekan **Buat Pesanan**.

**BB.** Browser mengirim **POST /api/orders**.

**BC.** `LaundryService.createOrder()` menghitung harga, menerapkan diskon, generate ID (ORD-991, ...).

**BD.** `OrderRepository` menyimpan ke tabel **`orders`** dan **`order_services`**.

**BE.** Status awal: **"Menunggu Diproses"**.

**BF.** Customer menerima konfirmasi beserta ID pesanan.

### 8.2 Cek Status Pesanan

**BG.** Customer memasukkan ID pesanan.

**BH.** Browser mengirim **POST /api/orders/check**.

**BI.** Sistem mengambil data dari database dan menampilkan status, total, layanan, dan keluhan.

### 8.3 Tambah Keluhan

**BJ.** Customer memasukkan ID pesanan dan teks keluhan.

**BK.** Browser mengirim **POST /api/orders/complaint**.

**BL.** Kolom **`complaint`** di tabel **`orders`** di-update di MySQL.

---

## 9. ALUR STAFF

**BM.** Staff melihat daftar pesanan masuk (**GET /api/orders**).

**BN.** Staff memilih pesanan dan menekan **Update**.

**BO.** Staff memasukkan status baru (contoh: "Selesai").

**BP.** Browser mengirim **POST /api/orders/status**.

**BQ.** Status pesanan di-update di database.

---

## 10. ALUR ADMIN

**BR.** Admin melihat rekap user (**GET /api/admin/users**) dan rekap transaksi (**GET /api/orders**).

**BS.** Admin dapat **menghapus pesanan** (**POST /api/delete-order**).

**BT.** Admin dapat **menghapus user** (**POST /api/delete-user**) dengan syarat:
- Bukan akun yang sedang login
- User tidak masih memiliki pesanan

---

## 11. ALUR MODE KONSOL

**BU.** Aplikasi dapat dijalankan tanpa browser menggunakan **`LaundrySystem`** (mode konsol).

**BV.** Mode konsol dan mode web menggunakan **`LaundryService`** dan database **MySQL yang sama**.

**BW.** Perbedaan hanya pada input/output: terminal vs browser.

---

## 12. LOGOUT DAN KELUAR

**BX.** Logout → kembali ke halaman login.

**BY.** EXIT → tampilan ucapan terima kasih.

**BZ.** Menutup terminal (Ctrl+C) → server berhenti.

---

## 13. DAFTAR CLASS PBO

| Class | Peran |
|-------|-------|
| `TubesTest` | Main class |
| `CleanHubWebServer` | Server HTTP |
| `LaundryApiHandler` | Handler API |
| `LaundryApiServlet` | Implementasi Servlet (PBO) |
| `LaundryService` | Logika bisnis (Singleton) |
| `User` | Abstract class user |
| `Customer`, `Staff`, `Admin` | Subclass User |
| `Order`, `Service`, `OrderReport` | Model data |
| `MembershipPayment` | Diskon member (implements `DiscountSystem`) |
| `UserRepository`, `OrderRepository`, `ServiceRepository` | Akses database |
| `DatabaseConnection` | Koneksi JDBC MySQL |

---

## 14. DAFTAR ENDPOINT API

| No | Method | Endpoint | Fungsi |
|----|--------|----------|--------|
| 1 | GET | /api/health | Cek server |
| 2 | POST | /api/login | Login |
| 3 | POST | /api/register | Registrasi |
| 4 | GET | /api/services | Daftar layanan |
| 5 | GET | /api/orders | Semua pesanan |
| 6 | POST | /api/orders | Buat pesanan |
| 7 | POST | /api/orders/check | Cek status |
| 8 | POST | /api/orders/complaint | Keluhan |
| 9 | POST | /api/orders/status | Update status |
| 10 | GET | /api/admin/users | Rekap user |
| 11 | POST | /api/delete-order | Hapus pesanan |
| 12 | POST | /api/delete-user | Hapus user |

---

## 15. KESIMPULAN ALUR DATA

Setiap aksi pengguna mengikuti pola:

**Browser → API → LaundryService → Repository → MySQL → Response JSON → Tampilan**

Data disimpan **permanen** di MySQL dan dapat dikelola melalui phpMyAdmin. Aplikasi dianggap berjalan dengan benar apabila MySQL aktif, database `cleanhub` ter-import, server Java berjalan, dan user mengakses `http://localhost:8080/`.

---

*Dokumen: LAPORAN_ALUR_DETAIL.md — CleanHub PBO*

# LAPORAN ALUR SISTEM CLEANHUB (VERSI RINGKAS)
## Sistem Manajemen Laundry — PBO

---

## 1. GAMBARAN UMUM

**CleanHub** adalah aplikasi manajemen laundry berbasis web dengan tiga role: **Customer**, **Staff**, dan **Admin**. Data disimpan di **MySQL** (database `cleanhub`).

**Teknologi:**
- Frontend: HTML + JavaScript (`web/index.html`)
- Backend: Java (`LaundryService`, Repository)
- API: Endpoint buatan sendiri (`/api/...`)
- Database: MySQL via XAMPP / phpMyAdmin

---

## 2. CARA MENJALANKAN

1. XAMPP → Start **MySQL**
2. phpMyAdmin → Import **`database/cleanhub.sql`**
3. Jalankan **`TubesTest/run.bat`**
4. Browser → **`http://localhost:8080/`**

**Akun demo:** C-001 (Customer), S-001 (Staff), A-001 (Admin)

---

## 3. ARSITEKTUR (4 LAPISAN)

```
index.html  →  API (/api/...)  →  LaundryService  →  Repository  →  MySQL
   (UI)           (Handler)         (Logika PBO)        (SQL)       (Database)
```

| Lapisan | File utama |
|---------|------------|
| UI | `web/index.html` |
| Web/API | `CleanHubWebServer`, `LaundryApiHandler` |
| Bisnis | `LaundryService`, `Customer`, `Staff`, `Admin`, `Order` |
| Database | `UserRepository`, `OrderRepository`, `DatabaseConnection` |

---

## 4. ALUR UTAMA APLIKASI

### A. Startup
`run.bat` → `TubesTest.main()` → `CleanHubWebServer` (port 8080) → tampilkan `index.html`

### B. Login
User input ID → `POST /api/login` → cek tabel `users` → tampilkan dashboard sesuai role

### C. Registrasi
Isi form → `POST /api/register` → INSERT ke tabel `users` → data tersimpan di MySQL

### D. Customer
| Fitur | API | Database |
|-------|-----|----------|
| Buat pesanan | POST /api/orders | `orders`, `order_services` |
| Cek status | POST /api/orders/check | SELECT `orders` |
| Keluhan | POST /api/orders/complaint | UPDATE `orders.complaint` |

Member mendapat **diskon 10%** (`MembershipPayment`).

### E. Staff
Lihat pesanan → update status → `POST /api/orders/status` → UPDATE `orders.status`

### F. Admin
Rekap user & transaksi → hapus pesanan/user → DELETE di MySQL

---

## 5. KONSEP PBO YANG DITERAPKAN

| Konsep | Implementasi |
|--------|--------------|
| Class & Object | `Customer`, `Staff`, `Admin`, `Order`, `Service` |
| Inheritance | `Customer/Staff/Admin extends User` |
| Polymorphism | Login return `User`, dashboard beda per role |
| Encapsulation | Data di class + akses via method |
| Interface | `DiscountSystem` → `MembershipPayment` |
| Singleton | `LaundryService.getInstance()` |

---

## 6. API PENTING

| Endpoint | Fungsi |
|----------|--------|
| POST /api/login | Login |
| POST /api/register | Daftar user |
| POST /api/orders | Buat pesanan |
| POST /api/orders/check | Cek status |
| POST /api/orders/status | Update status (Staff) |
| POST /api/delete-order | Hapus pesanan (Admin) |
| POST /api/delete-user | Hapus user (Admin) |

**API** = aturan komunikasi antara browser dan backend Java (bukan dari internet, dibuat sendiri di `LaundryApiHandler`).

---

## 7. KESIMPULAN

CleanHub menghubungkan **tampilan web**, **logika Java (PBO)**, dan **database MySQL** melalui **API REST**. Setiap aksi user (login, pesan, update status, hapus data) mengikuti alur yang sama: request dari browser → proses di Java → simpan/baca MySQL → response ke browser.

---

*Dokumen: LAPORAN_ALUR_RINGKAS.md — CleanHub PBO*

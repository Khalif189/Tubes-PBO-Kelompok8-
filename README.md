# Tugas Besar PBO — CleanHub

**Repository name (format kampus):** `Tugas-Besar-<Kelas>-Kelompok_<noKel>`

Contoh: `Tugas-Besar-IT-23-Kelompok_3`

## Deskripsi

**CleanHub** — Sistem Manajemen Laundry berbasis Pemrograman Berorientasi Objek (PBO).

Topik mencakup implementasi konsep OOP (class, inheritance, polymorphism, encapsulation), manajemen user multi-role (Customer, Staff, Admin), transaksi pesanan laundry, integrasi MySQL, serta antarmuka web dan konsol.

## Anggota Kelompok

| No | Nama | NIM | Kontribusi |
|----|------|-----|------------|
| 1  | *(isi)* | *(isi)* | *(isi)* |
| 2  | *(isi)* | *(isi)* | *(isi)* |
| 3  | *(isi)* | *(isi)* | *(isi)* |

> Ganti tabel di atas sesuai data kelompok Anda.

## Struktur Project

```
tubes pbo/
├── README.md                 ← file ini
├── .gitignore
├── database/
│   └── cleanhub.sql          ← script database MySQL
├── docs/
│   ├── LAPORAN_ALUR_DETAIL.md
│   └── LAPORAN_ALUR_RINGKAS.md
├── web/                      ← frontend (HTML, CSS, JS)
│   ├── index.html
│   ├── css/app.css
│   └── js/cleanhub-ui.js
└── TubesTest/                ← backend Java (main project)
    ├── run.bat               ← jalankan aplikasi
    ├── pom.xml
    └── src/main/java/com/mycompany/tubestest/
        ├── TubesTest.java    ← entry point
        ├── LaundryService.java
        ├── LaundrySystem.java
        ├── db/               ← JDBC repository
        └── web/              ← REST API + web server
```

## Cara Menjalankan

### Prasyarat

- JDK 17+
- XAMPP MySQL (aktif)
- Import `database/cleanhub.sql` via phpMyAdmin

### Konfigurasi database

```powershell
copy TubesTest\src\main\resources\database.properties.example TubesTest\src\main\resources\database.properties
```

Edit `database.properties` jika user/password MySQL berbeda.

### Jalankan

```powershell
cd TubesTest
.\run.bat
```

Buka browser: **http://localhost:8080/**

Akun demo: `C-001` (Customer), `S-001` (Staff), `A-001` (Admin)

## Setup GitHub (sesuai Tutorial)

### 1. Buat repository di GitHub

1. Login [github.com](https://github.com)
2. **New repository**
3. **Repository name:** `Tugas-Besar-<Kelas>-Kelompok_<noKel>`
4. **Description:** Sistem Manajemen Laundry CleanHub — Tugas Besar PBO
5. Centang **Add README** (opsional jika push dari lokal sudah ada README)
6. Pilih template **.gitignore** (Java) — atau gunakan `.gitignore` di repo ini
7. **Create repository**

### 2. Hubungkan & push dari komputer lokal

Ganti `USERNAME` dan `REPO` dengan akun dan nama repo Anda:

```powershell
cd "d:\apps\folder tugas\PBO\tubes pbo"

git remote set-url origin https://github.com/USERNAME/REPO.git
git push -u origin main
```

Contoh:

```powershell
git remote set-url origin https://github.com/johndoe/Tugas-Besar-IT-23-Kelompok_3.git
git push -u origin main
```

### 3. Clone (anggota lain)

```powershell
git clone https://github.com/USERNAME/REPO.git
cd REPO
```

## Alur Kerja Git (Branch & Merge)

Setiap fitur baru → buat branch terpisah:

```powershell
git checkout -b feature_nama_fitur
# ... edit code ...
git add .
git commit -m "Menambahkan fitur X"
git push origin feature_nama_fitur
```

Merge via **Pull Request** di web GitHub, atau lokal:

```powershell
git checkout main
git pull origin main
git merge feature_nama_fitur
git push origin main
```

### Contoh pembagian branch anggota

| Anggota | Branch | Isi |
|---------|--------|-----|
| A | `feature_login_register` | Login & registrasi user |
| B | `feature_order_tracking` | Pesanan & lacak status |
| C | `feature_admin_dashboard` | Dashboard admin & laporan |

## Dokumentasi

Lihat folder [`docs/`](docs/) untuk laporan alur sistem lengkap dan ringkas.

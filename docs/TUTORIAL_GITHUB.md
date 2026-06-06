# Tutorial GitHub — Kelompok 8 IF-48-04

Panduan setup dan alur kerja GitHub untuk Tugas Besar PBO **CleanHub**.

| Item | Nilai |
|------|-------|
| **Repository name** | `Tugas-Besar-IF-48-04-Kelompok_8` |
| **Kelas** | IF-48-04 |
| **Kelompok** | 8 |
| **Topik** | Sistem Manajemen Laundry CleanHub (PBO + Java + MySQL + Web) |
| **Folder lokal** | `d:\apps\folder tugas\PBO\tubes pbo` |

---

## Bagian 1 — Setup Awal Project

### 1.1 Buat akun GitHub

1. Buka [https://github.com](https://github.com)
2. Daftar / login akun GitHub

### 1.2 Buat repository di GitHub

1. Klik tombol **+** → **New repository**
2. Isi form:

| Field | Isi |
|-------|-----|
| **Repository name** | `Tugas-Besar-IF-48-04-Kelompok_8` |
| **Description** | Sistem Manajemen Laundry CleanHub — Tugas Besar PBO Kelompok 8 IF-48-04 |
| **Public / Private** | Sesuai arahan dosen |
| **Add README** | **Jangan** dicentang (README sudah ada di komputer lokal) |
| **Add .gitignore** | **Jangan** dicentang (`.gitignore` sudah ada di lokal) |

3. Klik **Create repository**

### 1.3 Hubungkan folder lokal ke GitHub

Ganti `USERNAME` dengan username GitHub Anda (contoh: `alfan123`):

```powershell
cd "d:\apps\folder tugas\PBO\tubes pbo"

git remote set-url origin https://github.com/USERNAME/Tugas-Besar-IF-48-04-Kelompok_8.git
git push -u origin main
```

**Contoh lengkap** (jika username GitHub = `alfan123`):

```powershell
cd "d:\apps\folder tugas\PBO\tubes pbo"

git remote set-url origin https://github.com/alfan123/Tugas-Besar-IF-48-04-Kelompok_8.git
git push -u origin main
```

> Saat pertama push, GitHub akan meminta login. Gunakan akun GitHub kelompok.

### 1.4 Clone repository (untuk anggota lain)

Anggota yang belum punya folder project:

```powershell
cd "d:\apps\folder tugas\PBO"
git clone https://github.com/USERNAME/Tugas-Besar-IF-48-04-Kelompok_8.git
cd Tugas-Besar-IF-48-04-Kelompok_8
```

Atau clone ke folder dengan nama custom:

```powershell
git clone https://github.com/USERNAME/Tugas-Besar-IF-48-04-Kelompok_8.git "tubes pbo"
```

---

## Bagian 2 — Struktur Project Tugas Besar

Project CleanHub sudah dibuat di folder lokal. Struktur utama:

```
Tugas-Besar-IF-48-04-Kelompok_8/
├── README.md
├── .gitignore
├── database/
│   └── cleanhub.sql              ← script MySQL
├── docs/
│   ├── TUTORIAL_GITHUB.md        ← file ini
│   ├── LAPORAN_ALUR_DETAIL.md
│   └── LAPORAN_ALUR_RINGKAS.md
├── web/                          ← frontend
│   ├── index.html
│   ├── css/app.css
│   └── js/cleanhub-ui.js
└── TubesTest/                    ← backend Java
    ├── run.bat                   ← jalankan aplikasi
    └── src/main/java/...         ← source code OOP
```

### File `.gitignore`

Sudah berisi (sesuai tutorial kampus + Java):

```
bin/
obj/
*.cbp
TubesTest/out/
TubesTest/target/
*.class
```

File build (`out/`, `target/`, `.class`) **tidak** di-push ke GitHub.

### Commit awal (sudah dilakukan)

```powershell
git add .
git commit -m "Initial commit - CleanHub PBO project"
git push -u origin main
```

---

## Bagian 3 — Alur Kerja Project dengan GitHub

### 3.1 Anggota clone project

```powershell
git clone https://github.com/USERNAME/Tugas-Besar-IF-48-04-Kelompok_8.git
```

### 3.2 Buat branch untuk fitur baru

Setiap fitur baru → branch terpisah:

```powershell
git checkout -b feature_nama_fitur
```

**Contoh:**

```powershell
git checkout -b feature_login_register
git checkout -b feature_lacak_pesanan
git checkout -b feature_admin_dashboard
```

### 3.3 Commit & push branch

Setelah selesai coding:

```powershell
git add .
git commit -m "Menambahkan fitur login dan register"
git push origin feature_login_register
```

### 3.4 Merge ke main

**Cara 1 — Pull Request (disarankan):**

1. Buka repo di browser: `https://github.com/USERNAME/Tugas-Besar-IF-48-04-Kelompok_8`
2. Klik **Compare & pull request**
3. Review perubahan → **Merge pull request**

**Cara 2 — Merge lokal:**

```powershell
git checkout main
git pull origin main
git merge feature_login_register
git push origin main
```

---

## Bagian 4 — Contoh Pembagian Tugas Kelompok 8

| Anggota | Branch | Tugas |
|---------|--------|-------|
| Anggota A | `feature_login_register` | Login, register, daftar user |
| Anggota B | `feature_order_customer` | Buat pesanan, lacak pesanan, keluhan |
| Anggota C | `feature_staff_admin` | Dashboard staff, update status, admin |
| Anggota D | `feature_database_docs` | MySQL, laporan alur, README |

### Alur singkat per anggota

```
Buat branch → coding → commit → push → Pull Request → merge ke main
```

---

## Bagian 5 — Perintah Git yang Sering Dipakai

```powershell
# Cek status file
git status

# Lihat branch aktif
git branch

# Pindah ke branch main
git checkout main

# Ambil update terbaru dari GitHub
git pull origin main

# Lihat riwayat commit
git log --oneline -5
```

---

## Bagian 6 — Menjalankan CleanHub (setelah clone)

```powershell
# 1. Import database di phpMyAdmin: database/cleanhub.sql

# 2. Copy config database
copy TubesTest\src\main\resources\database.properties.example TubesTest\src\main\resources\database.properties

# 3. Jalankan server
cd TubesTest
.\run.bat

# 4. Buka browser
# http://localhost:8080/
```

Akun demo: `C-001` (Customer), `S-001` (Staff), `A-001` (Admin)

---

## Checklist Sebelum Push

- [ ] XAMPP MySQL jalan & database `cleanhub` sudah di-import
- [ ] `run.bat` berhasil dijalankan
- [ ] Tidak ada password MySQL di file yang di-commit (`database.properties` di-ignore)
- [ ] Nama repo GitHub: **`Tugas-Besar-IF-48-04-Kelompok_8`**
- [ ] Tabel anggota kelompok sudah diisi di `README.md`

---

## Link Repo (isi setelah push)

```
https://github.com/USERNAME/Tugas-Besar-IF-48-04-Kelompok_8
```

Ganti `USERNAME` dengan username GitHub kelompok Anda.

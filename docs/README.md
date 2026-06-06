# Dokumentasi CleanHub

Folder ini berisi laporan alur sistem untuk proyek PBO CleanHub.

## File

| File | Isi |
|------|-----|
| **LAPORAN_ALUR_DETAIL.md** | Alur lengkap A–BZ (untuk bab implementasi / lampiran) |
| **LAPORAN_ALUR_RINGKAS.md** | Ringkasan 2–3 halaman (untuk bab metodologi / ringkasan) |

## Cara buka di Microsoft Word

1. Buka **Microsoft Word**
2. **File → Open** → pilih file `.md` dari folder `docs/`
3. Atau: buka file `.md` di Notepad → **Select All → Copy** → Paste ke Word
4. Atur font (mis. Times New Roman 12), spasi, dan heading sesuai format kampus

## Cara convert ke .docx (opsional)

- Online: [Pandoc](https://pandoc.org/) atau converter Markdown to Word
- VS Code / Cursor: extension "Markdown PDF" atau export via plugin

## Struktur proyek terkait

```
tubes pbo/
├── docs/                    ← laporan (folder ini)
├── database/cleanhub.sql    ← script MySQL
├── web/index.html           ← frontend
└── TubesTest/               ← backend Java
    ├── run.bat              ← jalankan aplikasi
    └── src/main/java/...    ← source code
```

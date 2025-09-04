# Note App (Android - Java)

Ứng dụng ghi chú đơn giản, viết bằng Android Java, lưu trữ dữ liệu cục bộ bằng SharedPreferences + Gson.  
Sử dụng các UI Components: RecyclerView, CardView, FloatingActionButton, EditText
## Tính năng

-  Thêm ghi chú mới với tiêu đề + nội dung.
-  Chỉnh sửa ghi chú bằng cách nhấn vào item trong danh sách.
- Xóa ghi chú trực tiếp trong màn hình chỉnh sửa.
- ìm kiếm ghi chú theo tiêu đề hoặc nội dung (lọc theo từ khóa).
- Chuyển đổi giao diện: xem ghi chú theo dạng **Danh sách** hoặc **Lưới**.
- Lưu trữ cục bộ: dữ liệu được lưu vào bộ nhớ máy, không mất khi tắt ứng dụng.
- Giao diện theo Material Design, trực quan, dễ sử dụng.

---

## 🖼️ Hình minh họa giao diện

### Màn hình danh sách ghi chú
- Có thanh tìm kiếm ở trên cùng.
- Nút toggle () để chuyển giữa danh sách & lưới.
- Nút (+) để thêm ghi chú mới.
- Khi chưa có dữ liệu: hiển thị `"Chưa có ghi chú nào. Nhấn + để bắt đầu!"`
  
*(ảnh minh họa: <img width="383" height="829" alt="image" src="https://github.com/user-attachments/assets/33666ad8-0cb3-4f18-b5b9-7e7567a69f71" />


)*

---

### Màn hình tạo/sửa ghi chú
- Nhập **Tiêu đề** và **Nội dung**.
- Có nút **Lưu** (xanh lá) và **Xóa** (đỏ).
- Khi thêm mới → nút Xóa sẽ bị vô hiệu hóa.

*(ảnh minh họa: <img width="385" height="792" alt="image" src="https://github.com/user-attachments/assets/639c15d2-37ac-4d97-8e10-24c0fa0ed3ba" />


)*

---

## 🏗️ Cấu trúc thư mục
java/com/example/note
│── Adapter/
│ └── NoteAdapter.java # Adapter cho RecyclerView
│
│── data/
│ ├── Note.java # Model ghi chú
│ └── NoteStorage.java # Lưu/đọc ghi chú bằng SharedPreferences
│
│── ui/
│ ├── MainActivity.java # Màn hình danh sách ghi chú
│ └── NoteEditorActivity.java # Màn hình tạo/sửa/xóa ghi chú
│
res/layout/
│── activity_main.xml # Layout danh sách + tìm kiếm
│── activity_note_editor.xml # Layout tạo/sửa ghi chú
│── item_note.xml # Layout hiển thị một ghi chú


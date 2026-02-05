// ================== CHUNG ==================
function getInputValue(id) {
    const el = document.getElementById(id);
    return el ? el.value.trim() : "";
}

function showAlert(message, type = "info") {
    // Nâng cấp sau: dùng toast (SweetAlert2, Toastify, Bootstrap toast,...)
    const className = type === "error" ? "alert-danger" : "alert-success";
    alert(message); // tạm thời giữ alert
    // console.log(`[${type.toUpperCase()}] ${message}`);
}

// ================== THÊM SÁCH ==================
function addNewBook() {
    const title    = getInputValue("title");
    const author   = getInputValue("author");
    const category = getInputValue("category");
    const image     = document.getElementById("imageFile")?.files[0]; // nếu muốn upload file thật

    if (!title || !author || !category) {
        showAlert("Vui lòng nhập đầy đủ: Tiêu đề, Tác giả, Danh mục", "error");
        return;
    }

    const bookData = { title, author, category };

    // Nếu có upload file thật → dùng FormData thay vì JSON
    // Hiện tại giả sử backend chấp nhận imageUrl dạng string
    if (getInputValue("imageUrl")) {
        bookData.imageUrl = getInputValue("imageUrl");
    }

    fetch("/api/books", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            // Nếu có CSRF: thêm header X-XSRF-TOKEN (lấy từ cookie/meta)
        },
        body: JSON.stringify(bookData)
    })
        .then(res => {
            if (!res.ok) throw new Error(`Lỗi ${res.status}: ${res.statusText}`);
            return res.text();
        })
        .then(() => {
            showAlert("Thêm sách thành công!");
            window.location.href = "/";
        })
        .catch(err => {
            console.error("Add book error:", err);
            showAlert("Không thể thêm sách. Kiểm tra console để xem chi tiết.", "error");
        });
}

// ================== LOAD SÁCH ĐỂ EDIT ==================
function loadBookForEdit() {
    const pathParts = window.location.pathname.split("/").filter(Boolean);
    const id = pathParts[pathParts.length - 1];

    if (!id || isNaN(id)) {
        showAlert("ID sách không hợp lệ", "error");
        return;
    }

    fetch(`/api/books/${id}`)
        .then(res => {
            if (!res.ok) throw new Error(`Không tìm thấy sách (mã ${res.status})`);
            return res.json();
        })
        .then(book => {
            document.getElementById("bookId").value       = book.id       || "";
            document.getElementById("bookTitle").value    = book.title    || "";
            document.getElementById("bookAuthor").value   = book.author   || "";
            document.getElementById("bookCategory").value = book.category || "";
            document.getElementById("bookImageUrl").value = book.imageUrl || "";
        })
        .catch(err => {
            console.error("Load book error:", err);
            showAlert("Không tải được thông tin sách", "error");
        });
}

// ================== CẬP NHẬT SÁCH ==================
function saveBook() {
    const id       = getInputValue("bookId");
    const title    = getInputValue("bookTitle");
    const author   = getInputValue("bookAuthor");
    const category = getInputValue("bookCategory");
    const imageUrl = getInputValue("bookImageUrl");

    if (!id || !title || !author || !category) {
        showAlert("Thiếu thông tin cần thiết để cập nhật", "error");
        return;
    }

    fetch(`/api/books/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ id, title, author, category, imageUrl })
    })
        .then(res => {
            if (!res.ok) throw new Error(`Cập nhật thất bại (mã ${res.status})`);
            return res.text();
        })
        .then(() => {
            showAlert("Cập nhật thành công!");
            window.location.href = "/";
        })
        .catch(err => {
            console.error("Update error:", err);
            showAlert("Lỗi khi cập nhật sách", "error");
        });
}

// ================== XÓA SÁCH ==================
function deleteBook(id) {
    if (!id) {
        showAlert("Không có ID sách để xóa", "error");
        return;
    }

    if (!confirm("Bạn chắc chắn muốn xóa sách này?")) return;

    fetch(`/api/books/${id}`, { method: "DELETE" })
        .then(res => {
            if (!res.ok) throw new Error(`Xóa thất bại (mã ${res.status})`);
            return res.text();
        })
        .then(() => {
            showAlert("Xóa sách thành công!");
            window.location.reload();
        })
        .catch(err => {
            console.error("Delete error:", err);
            showAlert("Không thể xóa sách", "error");
        });
}
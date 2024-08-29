$(document).ready(function() {
    let currentPage = 0;
    const pageSize = 5;

    // Hàm tải danh sách blog
    function loadBlogs(page, size, query = '') {
        $.ajax({
            url: 'http://localhost:8081/api/blogs',
            type: 'GET',
            dataType: 'json',
            data: { page: page, size: size, q: query },
            success: function(data) {
                var blogList = $('#blogList');
                if (page === 0) {
                    blogList.empty();  // Xóa danh sách blog hiện tại khi tải lần đầu
                }
                $.each(data, function(index, blog) {
                    blogList.append(
                        '<div class="blog-item">' +
                        '<h3>' + blog.title + '</h3>' +
                        '<p>' + blog.content + '</p>' +
                        '<button class="btn btn-delete btn-sm" onclick="deleteBlog(' + blog.id + ')">Xóa</button>' +
                        '</div>'
                    );
                });
                if (data.length < size) {
                    $('#loadMore').hide();  // Ẩn nút "Tải thêm" nếu không còn dữ liệu
                } else {
                    $('#loadMore').show();  // Hiển thị nút "Tải thêm"
                }
            },
            error: function() {
                alert('Không thể tải danh sách blog.');
            }
        });
    }

    // Tải danh sách blog khi trang được tải
    loadBlogs(currentPage, pageSize);

    // Xử lý sự kiện submit của form tìm kiếm
    $('#searchBlogForm').submit(function(event) {
        event.preventDefault();
        var query = $('#searchQuery').val();
        currentPage = 0;  // Đặt lại trang hiện tại khi tìm kiếm
        loadBlogs(currentPage, pageSize, query);
    });

    // Xử lý sự kiện click nút "Tải thêm"
    $('#loadMore').click(function() {
        currentPage++;
        var query = $('#searchQuery').val();
        loadBlogs(currentPage, pageSize, query);
    });

    // Thêm blog mới
    $('#addBlogForm').submit(function(event) {
        event.preventDefault();
        var blogData = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        $.ajax({
            url: 'http://localhost:8081/api/blogs',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(blogData),
            success: function() {
                alert('Thêm mới blog thành công!');
                $('#title').val('');
                $('#content').val('');
                currentPage = 0;  // Đặt lại trang hiện tại khi thêm mới blog
                loadBlogs(currentPage, pageSize);
            },
            error: function() {
                alert('Không thể thêm mới blog.');
            }
        });
    });

    // Xóa blog
    window.deleteBlog = function(id) {
        if (confirm('Bạn có chắc chắn muốn xóa blog này không?')) {
            $.ajax({
                url: 'http://localhost:8081/api/blogs/' + id,
                type: 'DELETE',
                success: function() {
                    alert('Xóa blog thành công!');
                    currentPage = 0;  // Đặt lại trang hiện tại khi xóa blog
                    var query = $('#searchQuery').val();
                    loadBlogs(currentPage, pageSize, query);
                },
                error: function() {
                    alert('Không thể xóa blog.');
                }
            });
        }
    };
});

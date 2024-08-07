$(document).ready(function () {
    getAllPosts();
});

function getAllPosts() {
    $.ajax({
        url: 'http://localhost:8080/blog/getAllPosts',
        method: 'GET',
        success: function (result) {
            console.log(result);
            let postList = $('#postTableBody');
            postList.empty();
            result.forEach(post => {
                postList.append(
                    '<tr>' +
                    '<td>' + post.id + '</td>' +
                    '<td>' + post.title + '</td>' +
                    '<td>' + post.content + '</td>' +
                    '<td>' +
                    '<button class="btn btn-warning updateBtn" data-id="' + post.id + '" data-title="' + post.title + '" data-content="' + post.content + '">Update</button> ' +
                    '<button class="btn btn-danger deleteBtn" data-id="' + post.id + '">Delete</button>' +
                    '</td>' +
                    '</tr>'
                );
            });
        },
        error: function (error) {
            console.log(error);
            alert('Failed to get posts');
        }
    });
}

$('#savepost').click(function () {
    let postId = $('#post-id').val();
    let postTitle = $('#post-title').val();
    let postContent = $('#post-content').val();
    console.log(postId, postTitle, postContent);

    $.ajax({
        url: 'http://localhost:8080/blog/savePost',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            id: postId,
            title: postTitle,
            content: postContent
        }),
        success: function (result) {
            getAllPosts();
            clearFields();
            console.log(result);
            alert('Post saved');
        },
        error: function (error) {
            console.log(error);
            alert('Post not saved,Try again');
        }
    });
});

$(document).on('click', '.updateBtn', function () {
    let postId = $(this).data('id');
    let postTitle = $(this).data('title');
    let postContent = $(this).data('content');

    $('#update-post-id').val(postId);
    $('#update-post-title').val(postTitle);
    $('#update-post-content').val(postContent);

    $('#updatePostPopup').css('display', 'block');
});

$(document).on('click', '.deleteBtn', function () {
    let postId = $(this).data('id');

    $('#deletePostPopup').css('display', 'block');
    $('#confirmDeletePost').data('id', postId);
});

$('#updatePost').click(function () {
    let postId = $('#update-post-id').val();
    let postTitle = $('#update-post-title').val();
    let postContent = $('#update-post-content').val();
    console.log(postId, postTitle, postContent);

    $.ajax({
        url: 'http://localhost:8080/blog/updatePost',
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({
            id: postId,
            title: postTitle,
            content: postContent
        }),
        success: function (result) {
            getAllPosts();
            clearFields();
            $('#updatePostPopup').css('display', 'none');
            console.log(result);
            alert('Post updated');
        },
        error: function (error) {
            console.log(error);
            alert('Post not updated,Try again');
        }
    });
});

$('#confirmDeletePost').click(function () {
    let postId = $(this).data('id');
    console.log(postId);

    $.ajax({
        url: 'http://localhost:8080/blog/deletePost/' + postId,
        method: 'DELETE',
        success: function (result) {
            getAllPosts();
            $('#deletePostPopup').css('display', 'none');
            console.log(result);
            alert('Post deleted');
        },
        error: function (error) {
            console.log(error);
            alert('Post not deleted,Try again');
        }
    });
});

$('#cancelDeletePost').click(function () {
    $('#deletePostPopup').css('display', 'none');
});

$('#closeUpdatePopup').click(function () {
    $('#updatePostPopup').css('display', 'none');
});

$('#closeDeletePopup').click(function () {
    $('#deletePostPopup').css('display', 'none');
});

function clearFields() {
    $('#post-id').val('');
    $('#post-title').val('');
    $('#post-content').val('');
}

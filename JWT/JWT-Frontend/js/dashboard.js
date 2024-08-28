$(document).ready(function() {
  let token = localStorage.getItem('token');
  if (token) {
    //
    $.ajax({
      url: "http://localhost:8080/api/v1/blog/newMethod",
      method: "GET",
      contentType: "application/json",
      headers: {
        "Authorization": `Bearer ${token}`
      },
      success:function (response) {
        console.log(response);
        $('<p>'+response+'</p>').appendTo('.container')
      },
      error : function (error) {
        console.log(error);
      }
    })
  }else {
    window.location.href = 'signin.html';
  }
});

function logOut() {
  localStorage.removeItem('token');
  window.location.href = 'signin.html';
}



function userRegistration() {
  event.preventDefault();

  //define variables
  var name = $('#name').val();
  var email = $('#email').val();
  var password = $('#password').val();

  console.log({
    name: name,
    email: email,
    password: password
  });

  //create ajax request
  $.ajax({
    url: 'http://localhost:8080/api/v1/auth/register',
    method: 'POST',
    contentType: "application/json",
    "data":JSON.stringify({
      name: name,
      email: email,
      password: password
    }),
    // error handling
    success: function(response) {
      console.log(response);
      localStorage.setItem('token', response.data.token)
      clearFields();
    },
    error: function(error) {
      console.log(error);
      alert(error.responseJSON.message);
    }
  });

}

function clearFields() {
  $('#name').val('');
  $('#email').val('');
  $('#password').val('');
}

function userLogin() {
  event.preventDefault();

  //define variables
  var email = $('#email').val();
  var password = $('#password').val();

  console.log({
    email: email,
    password: password
  });

  //create ajax request
  $.ajax({
    url: 'http://localhost:8080/api/v1/auth/authenticate',
    method: 'POST',
    contentType: "application/json",
    "data":JSON.stringify({
      email: email,
      password: password
    }),
    // error handling
    success: function(response) {
      console.log(response);
      localStorage.setItem('token', response.data.token)
      clearFields();
      window.location.href = 'dashboard.html';
    },
    error: function(error) {
      console.log(error);
      alert(error.responseJSON.message);
    }
  });
}

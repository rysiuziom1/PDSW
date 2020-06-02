document.getElementById('loginForm').addEventListener('submit', check);

function check(event) {
    event.preventDefault();
    const url = 'http://localhost:8080/student/kolokwium';
    const urlEndPoint = 'http://localhost:8080/api/v1/student/add'
    const studentData = {
        "studentIndex": document.getElementById('studentIndex'),
        "firstName": document.getElementById('studentName'),
        "lastName": document.getElementById('studentLastName')
    };

    const params = {
        headers : { "content-type" : "application/json; charset=UTF-8" },
        body : studentData,
        method : "POST",
        mode : "cors"
    };

    fetch(urlEndPoint, params).then(function (response) {
        if (response.ok) {
            response.redirect(url, 200)
        }
    })
}
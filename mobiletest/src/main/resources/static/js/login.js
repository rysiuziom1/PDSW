document.getElementById('loginForm').addEventListener('submit', check);

function check(event) {
    event.preventDefault();
    console.log("TEST");

    const urlEndPoint = 'http://localhost:8080/api/v1/student/add'
    const url = 'http://localhost:8080/login/student'

    const studentData = {
        "studentIndex": document.getElementById('studentIndex').value,
        "firstName": document.getElementById('studentName').value,
        "lastName": document.getElementById('studentLastName').value
    };

    const params = {
        headers : { "content-type" : "application/json; charset=UTF-8" },
        body : JSON.stringify(studentData),
        method : "POST",
        mode : "cors",
    };

    fetch(urlEndPoint, params).then(function (response) {
        if (response.ok) {
            console.log("Response from API with code 200");
            sessionStorage.setItem("studentIndex", studentData.studentIndex);
            console.log(response.text().then(value => {
                window.location.href = value;
            }))
        } else {
            throw new Error("Could not reach the API: " + response.statusText);
        }
    });
}

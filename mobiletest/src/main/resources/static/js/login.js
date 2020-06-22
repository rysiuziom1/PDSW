document.getElementById('loginForm').addEventListener('submit', check);

$(document).ready(function() {
    $("#danger-alert").hide();
});

function check(event) {
    event.preventDefault();
    console.log("TEST");

    const urlEndPoint = 'api/v1/student/add'
    const url = '/login/student'

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
            localStorage.setItem("studentIndex", studentData.studentIndex);
            if (localStorage.getItem("isDownloaded") === null) {
                localStorage.setItem("isDownloaded", "false");
            }
            console.log(response.text().then(value => {
                window.location.href = value;
            }))
        } else {
            $("#danger-alert").fadeTo(3000, 1000).slideUp(500, function() {
                $("#danger-alert").slideUp(500);
            });
            throw new Error("Could not reach the API: " + response.statusText);
        }
    });
}

$(document).ready(function () {
    const urlEndPoint = 'http://localhost:8080/api/v1/student/get_student?index=' + sessionStorage.getItem("studentIndex");

    fetch(urlEndPoint)
        .then(response => response.json())
        .then(data => {
            $('#studentData').text(data.firstName + ' ' + data.lastName + ' ' + data.studentIndex);
            let remainingTime = data.remainingTime;
            let minutes = Math.floor(remainingTime);
            let seconds = ("0" + Math.round((remainingTime - minutes) * (6. / 10.) * 100)).slice(-2);
            $('#time').text(minutes + ':' + seconds);
            localStorage.setItem("studentUUID", data.studentID);
        });

});

function finishTest() {
    const urlEndPoint = 'http://localhost:8080/api/v1/student/finish_test';
    const params = {
        headers : { "content-type" : "application/json" },
        body : JSON.stringify(localStorage.getItem("studentUUID")),
        method : "POST",
        mode : "cors",
    };
    fetch(urlEndPoint, params)
        .then(response => {
            if (response.ok) {
                sessionStorage.clear();
                localStorage.clear();
            }
        })
}

function requestTime() {

}

function sendFile() {
    const urlEndPoint = 'http://localhost:8080/api/v1/student/upload_file';
    const input = document.getElementById('fileInput');

    const formData = new FormData();

    formData.append('file', input.files[0]);

    $("#fileList").append('<li class="list-group-item">' + input.files[0].name + '</li>')

    const params = {
        body : formData,
        method : "POST",
    };

    fetch(urlEndPoint, params)
        .then(response => {
            if (response.ok) {
                console.log("File sent to server.")
            }
        }).then(
            success => console.log(success) // Handle the success response object
        ).catch(
            error => console.log(error) // Handle the error response object
        );
}

function getRemainingTime() {
    const urlEndPoint = 'http://localhost:8080/api/v1/student/get_student?index=' + sessionStorage.getItem("studentIndex");

    fetch(urlEndPoint)
        .then(response => response.json())
        .then(data => {
            let remainingTime = data.remainingTime;
            let minutes = Math.floor(remainingTime);
            let seconds = ("0" + Math.round((remainingTime - minutes) * (6. / 10.) * 100)).slice(-2);
            $('#time').text(minutes + ':' + seconds);
        });
}

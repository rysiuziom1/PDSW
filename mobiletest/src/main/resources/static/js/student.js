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

function sendFile() {
    const urlEndPoint = 'http://localhost:8080/api/v1/student/get_student?index=' + sessionStorage.getItem("studentIndex");
    const input = document.getElementById('fileInput');

    const upload = (file) => {
        fetch()
    }
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

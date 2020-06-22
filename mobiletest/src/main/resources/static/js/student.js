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

    getTestFile();
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

function getTestFile() {
    const urlEndPoint = 'http://localhost:8080/api/v1/student/get_file'

    fetch(urlEndPoint).then(response => {
        if (response.ok) {
            response.arrayBuffer().then(function (buffer) {
                savePdfFile('test-tasks.pdf', buffer);
            });
        }
    })
}

function savePdfFile(fileName, buffer) {
    // Object that represents data, in this case pdf
    let blob = new Blob([buffer], {type: "application/pdf"});
    // Creates link for this object download in html document
    let link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = fileName;
    link.click();
}

function sendFile() {
    const urlEndPoint = 'http://localhost:8080/api/v1/student/upload_file';
    const input = document.getElementById('fileInput');

    const formData = new FormData();

    formData.append('file', input.files[0]);


    const params = {
        body : formData,
        method : "POST",
        enctype : "multipart/form-data"
    };

    fetch(urlEndPoint, params)
        .then(response => {
            if (response.ok) {
                console.log("File sent to server.")
                let currentDate = new Date();
                let datetime = "added: " + currentDate.getDate() + "/"
                    + (currentDate.getMonth()+1)  + "/"
                    + currentDate.getFullYear() + " at "
                    + currentDate.getHours() + ":"
                    + currentDate.getMinutes() + ":"
                    + currentDate.getSeconds();

                $("#fileList").append('<li class="list-group-item">' + input.files[0].name + '  ' + datetime + '</li>')
            }
        }).then(success => console.log(success)

        ).catch(
            error => console.log(error) // Handle the error response object
        );
    return false;
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

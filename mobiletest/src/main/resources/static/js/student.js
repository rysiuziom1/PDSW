$(document).ready(function () {
    const urlEndPoint = 'api/v1/student/get_student?index=' + localStorage.getItem("studentIndex");

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
    if (localStorage.getItem("isDownloaded") === "false") {
        localStorage.setItem("isDownloaded", "true");
        getExerciseFile();
    }
});

function finishTest() {
    const urlEndPoint = 'api/v1/student/finish_test';

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
                window.location.replace("/student");
            }
        })
}

function getExerciseFile() {
    const urlEndPoint = 'api/v1/student/get_exercise?index=' + localStorage.getItem("studentIndex");
    let contentType;
    let fileName;
    fetch(urlEndPoint).then(response => {
        if (response.ok) {
            let cd = response.headers.get("content-disposition");
            contentType = response.headers.get("content-type");
            let regex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
            let match = regex.exec(cd);
            fileName = match[1]
            return response.blob();
        }
    }).then(data => {
        try {
            let blob = new Blob([data], {type: contentType});
            console.log(data);
            let objectUrl = URL.createObjectURL(blob);
            let link = document.createElement('a');
            link.download = fileName;
            link.href = objectUrl;
            link.target = "_blank";
            link.click();
        } catch (e) {
            console.log("Save Blob method failed with the following exception.");
            console.log(e);
        }
    })
}

function getTestFile() {
    const urlEndPoint = 'api/v1/student/get_file'

    fetch(urlEndPoint).then(response => {
        if (response.ok) {
            response.json().then(function (data) {
                savePdfFile(data.filename, data.file);
            });
        }
    })
}

function savePdfFile(fileName, buffer) {
    // Object that represents data, in this case pdf
    let blob = new Blob([atob(buffer)]);
    // Creates link for this object download in html document
    let link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = fileName;
    link.click();
}

function sendFile() {
    const urlEndPoint = 'api/v1/student/upload_file';
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
    const urlEndPoint = 'api/v1/student/get_student?index=' + localStorage.getItem("studentIndex");

    fetch(urlEndPoint)
        .then(response => response.json())
        .then(data => {

            var remainingTime = data.remainingTime
            var minutes = Math.floor(remainingTime)
            var request = data.requestTime;
            if(request == false){
                $("#requestButton").attr('disabled', false);
            }else{
                 $("#requestButton").attr('disabled', true);
            }
            if(minutes<=5){
                $("#remTime").addClass("red");
                $("#remTime").removeClass("white");
                $("#remTime").addClass("white-text");
                $("#cont").addClass("border-danger");
            }else{
                 $("#remTime").removeClass("red");
                 $("#remTime").removeClass("white-text");
                $("#remTime").addClass("white");
                $("#cont").removeClass("border-danger");
            }
            var seconds = ("0" + Math.round((remainingTime - minutes) * (6. / 10.) * 100)).slice(-2);
            $('#time').text(minutes + ':' + seconds);
        });
}

function requestTime(){
     const urlEndPoint = 'api/v1/student/request_time'

         const params = {
             headers : { "content-type" : "application/json" },
             body : JSON.stringify(localStorage.getItem("studentUUID")),
             method : "POST",
             mode : "cors",
         };
          fetch(urlEndPoint, params)
                 .then(response => {
                        $("#requestButton").attr('disabled', true);
                 })
}

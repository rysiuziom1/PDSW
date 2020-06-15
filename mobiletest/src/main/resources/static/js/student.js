$(document).ready(function () {
    // const queryString = window.location.search;
    // const urlParameters = new URLSearchParams(queryString)
    //
    // $('#studentData').text(urlParameters.get('firstName') + ' ' + urlParameters.get('lastName') + ' ' + sessionStorage.getItem('studentIndex'))

    const urlEndPoint = 'http://localhost:8080/api/v1/student/get_student?index=' + sessionStorage.getItem("studentIndex");

    fetch(urlEndPoint)
        .then(response => response.json())
        .then(data => {
            $('#studentData').text(data.firstName + ' ' + data.lastName + ' ' + data.studentIndex);
            $('#time').text(data.remainingTime);
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

document.getElementById('testForm').addEventListener('submit', check);

function check(event) {
    event.preventDefault();
    const url = '/students_list';
    const urlEndPoint = 'http://localhost:8080/api/v1/teacher/test/set'
    const KnowledgeTestData = {
        "totalTestTime" : document.getElementById('startTime').value,
        "solutionsAbsolutePath" : document.getElementById('solutionsFolder').value,
        "exercisesAbsolutePath" : document.getElementById('taskFolder').value,
    };

    const params = {
        headers : { "content-type" : "application/json; charset=UTF-8" },
        body : JSON.stringify(KnowledgeTestData),
        method : "POST",
        mode : "cors"
    };
    console.log(KnowledgeTestData);
    fetch(urlEndPoint, params).then(function (response) {
        if (response.ok) {
            location.replace(url);
            console.log("git");
        }else{
        document.getElementById("error").innerHTML=response.body}
    })
}
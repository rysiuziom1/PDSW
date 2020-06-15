$(document).ready(function () {
    const queryString = window.location.search;
    const urlParameters = new URLSearchParams(queryString)

    $('#studentData').text(urlParameters.get('firstName') + ' ' + urlParameters.get('lastName') + ' ' + sessionStorage.getItem('studentIndex'))

});

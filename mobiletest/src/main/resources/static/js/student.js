$(document).ready(function () {
    const queryString = window.location.search;
    const urlParametrs = new URLSearchParams(queryString)

    $('#studentData').text(urlParametrs.get('firstName') + ' ' + urlParametrs.get('lastName') + ' ' + urlParametrs.get('index'))

});
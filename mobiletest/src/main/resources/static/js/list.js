
function finishTests(){
     const urlEndPoint = 'http://localhost:8080/api/v1/student/finish_tests';
     const params = {
                     headers : { "content-type" : "application/json; charset=UTF-8" },
                     method : "POST",
                     mode : "cors"
                 };
     fetch(urlEndPoint, params).then(function (response) {
                         if (response.ok) {

                                    }else{
                            console.log(response)
                     }



})}
function changeTime(id, operation, i){

        const urlEndPoint = 'http://localhost:8080/api/v1/student/'+operation;
          const params = {
                headers : { "content-type" : "application/json; charset=UTF-8" },
                body : JSON.stringify(id),
                method : "POST",
                mode : "cors"
            };

            fetch(urlEndPoint, params).then(function (response) {
                    if (response.ok) {
                            jQuery('#'+i).fadeOut(500);
                            jQuery('#'+i).fadeIn(500);
                               }else{
                       console.log(response)
                }

})}
function loadList(){
    console.log("TEST");

    const urlEndPoint = 'http://localhost:8080/api/v1/student/get'
    const url = 'http://localhost:8080/login/student'




    var markup=``;
    fetch(urlEndPoint)
      .then(response => response.json())
      .then(data => {
            var i=0;
            console.log(data)
            for(var item of data){

                var remainingTime = Math.abs(item.remainingTime);
                var minutes = Math.floor(remainingTime);
                var seconds = ("0" + Math.round((remainingTime - minutes) * (6. / 10.) * 100)).slice(-2);
                var request = item.requestTime;
                if(request){
                    var end= 'red lighten-2'
                    var state= 'Prośba o dodanie czasu'
                }else{
                     if(remainingTime==0){
                        var end= 'red lighten-3'
                        var state= 'Zakończono'
                     }else{
                        var end= ''
                        var state= 'W trakcie'
                     }
                }

            markup=markup+`
                <tr class="align-middle ${end}">
                    <th scope="row">${item.firstName} ${item.lastName}</th>
                    <td>${item.studentIndex}</td>
                    <td id='${i}'>${minutes}:${seconds}</td>
                    <td>
                        <button class="btn btn-md btn-green" onClick="changeTime('${item.studentID}','increase_time', ${i})">+5</button>
                        <button class="btn btn-md btn-red" onClick="changeTime('${item.studentID}','decrease_time', ${i})">-5</button>
                        <button class="btn btn-md btn-red" onClick="changeTime('${item.studentID}','finish_test', ${i})"><i class="fa fa-remove"></i></button>
                    </td>
                    <td>
                        <span>${state}</span>
                    </td>
                    <td><i class="fa fa-lg fa-chain-broken red-ic"></i></td>
                </tr>
            `;
            document.getElementById('tab').innerHTML=markup;
            console.log(item);
        }
      });
}
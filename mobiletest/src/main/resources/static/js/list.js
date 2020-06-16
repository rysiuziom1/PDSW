

function loadList(){
    console.log("TEST");

    const urlEndPoint = 'http://localhost:8080/api/v1/student/get'
    const url = 'http://localhost:8080/login/student'




    var markup=``;
    fetch(urlEndPoint)
      .then(response => response.json())
      .then(data => {
            for(var item of data){
            markup=markup+`
                <tr class="align-middle">
                    <th scope="row">${item.firstName} ${item.lastName}</th>
                    <td>${item.studentIndex}</td>
                    <td>${item.remainingTime}</td>
                    <td>
                        <button class="btn btn-md btn-green">+5</button>
                        <button class="btn btn-md btn-red">-5</button>
                        <button class="btn btn-md btn-red"><i class="fa fa-remove"></i></button>
                    </td>
                    <td>
                        W trakcie
                    </td>
                    <td><i class="fa fa-lg fa-chain-broken red-ic"></i></td>
                </tr>
            `;
            document.getElementById('tab').innerHTML=markup;
            console.log(item);
        }
      });
}
function getUsersAndDisplay()
{
    let url = "http://192.168.43.33:2323/api/telegram/most/active/users";
    let usersTable = document.getElementById('UserTableId');
    fetch(url)
    .then(response => response.json())
    .then(data => {
        data = data.result;
        let i = 1;
        for(let key in data)
        {
            let row = usersTable.insertRow();
            let id = row.insertCell();
            id.innerHTML = i;
            let firstname = row.insertCell();
            firstname.innerHTML = data[key]['user']['firstname'];
            let lastname = row.insertCell();
            lastname.innerHTML = data[key]['user']['lastname'];
            let count = row.insertCell();
            count.innerHTML = data[key]['count'];
            i += 1;
        }
    });
}

getUsersAndDisplay();
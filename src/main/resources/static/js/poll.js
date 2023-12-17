function createPoll()
{
    let closeForm = document.getElementById('closeFormId');
    let message = document.getElementById('titlePollId');
    let option1 = document.getElementById('option1');
    let option2 = document.getElementById('option2');
    let option3 = document.getElementById('option3');
    let options = [option1.value, option2.value, option3.value];
    option1.value = '';
    option2.value = '';
    option3.value = '';
    let url = `https://api.telegram.org/bot6764896295:AAFiP6WJ4UyYIKaWZTgBlLY5_S_7uDq3cS0/sendPoll?chat_id=@ManasBMTest&question=${message.value}&options=${JSON.stringify(options)}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            console.log(data)
            alert('Опрос успешно создан')
        });
    message.value = '';
    closeForm.click();
}

function getPolls()
{
    let url = "http://192.168.43.33:2323/api/telegram/polls";
    fetch(url)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            data = data.result;
            let text = '';
            for(let i = 0; i < data.length; i++)
            {
                let option = '';
                let options = data[i].poll.options;
                for(let j = 0; j < options.length; j++)
                {
                    let part = `<li class="list-group-item">${options[j].text}<span style="margin-left: 40px; color: #00bb00">${options[j].voter_count}</span></li>`;
                    option += part;
                }
                let item = `<div class="card mb-4 ml-4" style="width: 18rem;">
                                    <div class="card-header">
                                        ${data[i].poll.question}
                                    </div>
                                    <ul class="list-group list-group-flush">
                                        ${option}
                                   </ul>
                                </div>`;
                text += item;
                document.getElementById('pollAreaId').innerHTML = text;
            }
        });
}

getPolls();
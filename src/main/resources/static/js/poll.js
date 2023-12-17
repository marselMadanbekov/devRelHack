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
        });
    message.value = '';
    closeForm.click();


}
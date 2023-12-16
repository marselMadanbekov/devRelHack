document.addEventListener('DOMContentLoaded', function () {
    const createPupilForm = document.getElementById('user-create-form');

    createPupilForm.addEventListener('submit', function (e) {
        e.preventDefault();
        document.getElementById("progress-spinner").hidden = false;

        if (confirm("Вы уверены, что хотите создать пользователя?")) {
            let formData = new FormData(this);
            console.log(formData)
            fetch('/users/create-user', {
                method: 'POST',
                body: formData
            })
                .then(response => response.json())
                .then(data => {
                    document.getElementById("progress-spinner").hidden = true;
                    alert(data.message);
                    window.location.href = "/users"; // Перенаправление пользователя после успешного создания
                })
                .catch(error => {
                    document.getElementById("progress-spinner").hidden = true;
                    console.error('Ошибка:', error);
                    alert('Что-то пошло не так');
                });
        }
    });
});

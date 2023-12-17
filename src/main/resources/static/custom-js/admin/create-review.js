document.addEventListener('DOMContentLoaded', function () {
    const saveButton = document.querySelector('#createReview');
    saveButton.addEventListener('click', function () {

        const eventId = document.getElementById("eventId").value;
        const message = document.getElementById('commentMessage').value;


        const checkedRadios = document.querySelectorAll('input[type="radio"]');
        let grade = 0;
        checkedRadios.forEach(function (item) {
            if (item.checked)
                grade = item.getAttribute("grade");
        });

        let formData = new FormData();
        formData.append("eventId", eventId);
        formData.append("message", message);
        formData.append("grade", grade);

        $.ajax({
            url: '/events/create-review',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function (response) {
                alert('Данные успешно отправлены на сервер:' + response);
            },
            error: function (xhr, status, error) {
                // Обработка ошибок запроса, если нужно
                alert('Произошла ошибка при отправке данных:' + error);
            }
        });
    });
});

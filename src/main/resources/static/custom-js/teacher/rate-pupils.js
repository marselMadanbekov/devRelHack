document.addEventListener('DOMContentLoaded', function () {
    const saveButton = document.querySelector('.btn-primary');
    saveButton.addEventListener('click', function() {
        let dataToSend = [];

        const selectedType = document.querySelector('input[name="mark-type-radios"]:checked').value;
        const subjectId = document.getElementById("subjectId").value;
        const pupils = document.querySelectorAll('.pupils');

        pupils.forEach(function(pupil) {
            const pupilId = pupil.getAttribute("pupilId");
            const theme = pupil.querySelector('input[type="text"]').value;
            const checkedRadios = pupil.querySelectorAll('input[type="radio"]');
            let grade = 0;
            checkedRadios.forEach(function (item){
                if(item.checked)
                    grade = item.getAttribute("grade");
            });

            let pupilData = {
                pupilId: pupilId,
                theme: theme,
                grade: grade
            };

            dataToSend.push(pupilData);
        });

        // Добавляем selectedType и subjectId в объект данных
        const requestData = {
            markType: selectedType,
            subjectId: subjectId,
            pupilsRate: dataToSend
        };
        $.ajax({
            url: '/teacher/groups/submit-rate', // Замените на URL вашего сервера для сохранения данных
            type: 'POST', // Или другой метод HTTP, если требуется
            contentType: 'application/json', // Указываем тип содержимого как JSON
            data: JSON.stringify(requestData), // Преобразуем массив в JSON перед отправкой
            success: function(response) {
                // Обработка успешного ответа от сервера, если нужно
                alert('Данные успешно отправлены на сервер:'+ response);
            },
            error: function(xhr, status, error) {
                // Обработка ошибок запроса, если нужно
                alert('Произошла ошибка при отправке данных:'+ error);
            }
        });
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const saveTestButton = document.getElementById("save-test");
    const answerInputs = document.querySelectorAll(".test-answer-input");

    saveTestButton.addEventListener("click", function (e) {
        e.preventDefault();
        let exerciseId = document.getElementById("exerciseId").value;
        let countOfCorrectAnswers = 0;
        let countOfTotalQuestions = 0;

        answerInputs.forEach(function (item) {
            countOfTotalQuestions++;
            let correctAnswer = item.getAttribute('answer');
            let pupilsAnswer = item.value;
            if (correctAnswer === pupilsAnswer)
                countOfCorrectAnswers++;
        })

        let formData = new FormData();
        formData.append("exerciseId", exerciseId);
        formData.append("totalQuestions", countOfTotalQuestions + '');
        formData.append("correctAnswers", countOfCorrectAnswers + '');
        if (confirm("Вы уверены что хотите сохранить прогресс?")) {
            $.ajax({
                url: "/pupil/homeworks/submit-test",
                type: "POST",
                data: formData,
                contentType: false,
                processData: false,
                success: function (response) {
                    alert(response.message);
                    window.location.reload();
                },
                error: function (xhr) {
                    try {
                        const errorData = JSON.parse(xhr.responseText);
                        if (errorData.hasOwnProperty("error")) {
                            alert(errorData.error);
                        } else {
                            alert('Что то пошло не так');
                        }
                    } catch (e) {
                        alert('Что то пошло не так');
                    }
                }
            })
        }
    })
});

document.addEventListener('DOMContentLoaded', function () {
    const answerInput = document.getElementById("answerInput");
    const answerDiv = document.getElementById("answerDiv");

    const currentNumber = document.getElementById("currentNumber");

    const voiceOnButton = document.getElementById("voiceOn");
    const voiceOffButton = document.getElementById("voiceOff");

    const breakButton = document.getElementById("breakButton");
    const startButton = document.getElementById("startButton");
    const saveButton = document.getElementById("saveButton");

    const speed = document.getElementById("speed").value;
    const countOfDigits = document.getElementById("countOfDigits").value;
    const countOfNumbers = document.getElementById("countOfNumbers").value;
    const trainerTaskOrdinal = document.getElementById("trainerTaskOrdinal").value;

    let intervalId;
    let speechSynthesis = window.speechSynthesis;

    const exerciseId = document.getElementById("exerciseId").value;
    let voiceOn = true;
    let showingArray = [];
    let correctAnswer = null;

    voiceOnButton.addEventListener("click", function () {
        voiceOn = false;
        voiceOnButton.hidden = true;
        voiceOffButton.hidden = false;
    })
    voiceOffButton.addEventListener("click", function () {
        voiceOn = true;
        voiceOnButton.hidden = false;
        voiceOffButton.hidden = true;
    })

    startButton.addEventListener("click", function () {
        breakButton.hidden = false;
        saveButton.hidden = true;
        startButton.hidden = true;

        let formData = new FormData();
        formData.append("digits", countOfDigits);
        formData.append("count", countOfNumbers);
        formData.append("taskOrdinal", trainerTaskOrdinal);
        $.ajax({
            url: "/pupil/homeworks/get-trainer-task",
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            success: function (response) {
                showingArray = response.task.taskEntry;
                correctAnswer = response.task.answer;
                console.log(correctAnswer);
                startTrainer();
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
    });

    breakButton.addEventListener("click", function (){
        if (speechSynthesis) {
            speechSynthesis.cancel();
        }
        clearInterval(intervalId);
        breakButton.hidden = true;
        startButton.hidden = false;
        currentNumber.innerText = "Приготовьтесь!"
    })
    saveButton.addEventListener("click", function () {
        let pupilsAnswer = answerInput.value;
        if (pupilsAnswer == null || pupilsAnswer === '') {
            alert("Введите ответ!");
        } else {
            let formData = new FormData();
            formData.append('exerciseId', exerciseId);
            formData.append('isCorrect', (+correctAnswer === +pupilsAnswer) + '');
            $.ajax({
                url: "/pupil/homeworks/submit-trainer",
                type: "POST",
                data: formData,
                contentType: false,
                processData: false,
                success: function (response) {
                    if (response.solved === response.total)
                        window.location.reload()
                    else {
                        document.getElementById("correct").innerText = "Правильно: " + response.correct;
                        document.getElementById("progress").innerText = "Решено: " + response.solved + '/' + response.total;
                    }
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
            startButton.hidden = false;
            saveButton.hidden = true;
            answerDiv.hidden = true;
            currentNumber.style.fontSize = 30 + 'px';
            currentNumber.innerText = 'Приготовьтесь!';
            currentNumber.hidden = false;
        }
    })

    function startTrainer() {
        let index = 0;
        let speech = new SpeechSynthesisUtterance();
        speech.lang = 'ru';
        console.log(speed);
        if ('speechSynthesis' in window) {
            // Проверка на доступность speechSynthesis
            if (0.7 < speed && speed < 1.0) {
                speech.rate = 2;
            } else if (speed < 0.8) {
                voiceOnButton.click();
            } else {
                speech.rate = 1.2;
            }
            let fontSize = 100; // Базовый размер шрифта

            if (countOfDigits <= 3) {
                // Небольшое число, можно использовать больший размер шрифта
                fontSize = 100;
            } else {
                // Для более длинных чисел уменьшаем размер шрифта
                fontSize = 100 - (countOfDigits - 3) * 10; // Формула для уменьшения шрифта
            }

            function displayElement() {
                try {
                    if (index < showingArray.length) {
                        currentNumber.style.fontSize = fontSize + 'px';
                        currentNumber.innerText = showingArray[index];

                        if (voiceOn && speechSynthesis) {
                            speech.text = showingArray[index];
                            speechSynthesis.speak(speech);
                            speech.onend = function () {
                                index++;
                                displayElement();
                            };
                        } else {
                            index++;
                        }
                    } else {
                        clearInterval(intervalId);
                        currentNumber.hidden = true;
                        answerDiv.hidden = false;
                        saveButton.hidden = false;
                        breakButton.hidden = true;

                        // Остановка озвучивания после завершения массива
                        if (speechSynthesis) {
                            speechSynthesis.cancel();
                        }
                        index = 0; // Сброс индекса для последующего использования
                    }
                } catch (error) {
                    // Обработка ошибок speechSynthesis
                    alert('Произошла ошибка с speechSynthesis:' + error);
                    // Дополнительные действия, которые нужно предпринять при возникновении ошибки
                }
            }

            intervalId = setInterval(displayElement, speed * 1000);
        } else {
            alert('speechSynthesis не поддерживается в этом браузере.');
            // Дополнительные действия в случае, если speechSynthesis не поддерживается
        }
    }
});

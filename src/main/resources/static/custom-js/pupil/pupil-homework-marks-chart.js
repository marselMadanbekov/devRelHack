$(document).ready(function () {
    const homeworkChartCanvas = document.getElementById('pupils-homework-mark-chart');
    const prevMonthButton = document.getElementById('prevWeekButtonHomework');
    const nextMonthButton = document.getElementById('nextWeekButtonHomework');
    const pupilId = document.getElementById('pupilId').value;
    const subjectId = document.getElementById("subjectId").value;
    let weekShift = 0;
    let homeworkChart = null;


    prevMonthButton.addEventListener('click', function (e) {
        e.preventDefault();
        weekShift += 1;
        updateChart();
    });

    nextMonthButton.addEventListener('click', function (e) {
        e.preventDefault();
        if (weekShift > 0) {
            weekShift -= 1;
            updateChart();
        } else {
            alert("Следующие месяцы недоступны");
        }
    });


    function updateChart() {
        if (homeworkChart) {
            homeworkChart.destroy();
        }

        $.ajax({
            url: '/pupils/statistics/homework-statistic?pupilId=' + pupilId + '&subjectId=' + subjectId + '&weekShift=' + weekShift,
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                let labels = data.map(item => item.date);
                let valuesCorrect = data.map(item => item.correct);
                let valuesIncorrect = data.map(item => item.incorrect)

                console.log("incorrect -> " + valuesIncorrect);
                console.log("correct -> " + valuesCorrect);
                const chartData = {
                    labels: labels,
                    datasets: [{
                        label: 'Правильные',
                        data: valuesCorrect,
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1,
                        fill: false
                    },{
                        label: 'Неправильные',
                        data: valuesIncorrect,
                        backgroundColor: 'rgba(255, 99, 132, 0.5)',
                        borderColor: 'rgba(255,99,132,1)',
                        borderWidth: 1,
                        fill: false
                    },],
                };

                homeworkChart = new Chart(homeworkChartCanvas, {
                    type: 'bar',
                    data: chartData,
                    options: {
                        responsive: true,
                        scales: {
                            yAxes: [{
                                ticks: {
                                    beginAtZero: true
                                }
                            }]
                        },
                        legend: {
                            display: false
                        },
                        elements: {
                            point: {
                                radius: 0
                            }
                        }
                    },
                });
            },

            error: function () {
                alert("Произошла ошибка при загрузке данных. Попробуйте перезагрузить страницу");
            },
        });
    }

    updateChart();
});

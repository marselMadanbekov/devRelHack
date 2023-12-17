$(document).ready(function () {
    const salesChartCanvas = document.getElementById('new-pupils-chart');
//    const prevMonthButton = document.getElementById('prevMonthButtonPupils');
//    const nextMonthButton = document.getElementById('nextMonthButtonPupils');
//    let month = 0;
    let salesChart = null;
//
//
//    prevMonthButton.addEventListener('click', function (e) {
//        e.preventDefault();
//        month += 1;
//        updateChart();
//    });
//
//    nextMonthButton.addEventListener('click', function (e) {
//        e.preventDefault();
//        if (month > 0) {
//            month -= 1;
//            updateChart();
//        } else {
//            alert("Следующие месяцы недоступны");
//        }
//    });


    function updateChart() {
        if (salesChart) {
            salesChart.destroy();
        }

        $.ajax({
            url: 'http://192.168.43.33:2323/api/telegram/activity',
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                data = data.result
                const labels = []
                const values = []
                for (let key in data) {
                  if (data.hasOwnProperty(key)) {
                    labels.push(key);
                    values.push(data[key])
                  }
                }
                const chartData = {
                    labels: labels,
                    datasets: [{
                        label: 'кол-во',
                        data: values,
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1,
                        fill: true
                    }],
                };

                salesChart = new Chart(salesChartCanvas, {
                    type: 'horizontalBar',
                    data: chartData,
                    options: {
                        responsive: true,
                        scales: {
                            y: {
                                beginAtZero: true,
                            },
                        },
                    },
                });
            },

            error: function (error) {
                alert("Произошла ошибка при загрузке данных. Попробуйте перезагрузить страницу");
            },
        });
    }

    updateChart();
});

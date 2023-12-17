$(document).ready(function () {
    const salesChartCanvas = document.getElementById('top-events-chart');
    let salesChart = null;


    function updateChart() {
        if (salesChart) {
            salesChart.destroy(); // Уничтожение предыдущего графика при обновлении
        }

        $.ajax({
            url: '/events/top-events',
            method: 'POST',
            dataType: 'json',
            success: function (response) {
                const labels = response.map(item => item.label);
                const values = response.map(item => item.value);

                const chartData = {
                    labels: labels,
                    datasets: [{
                        label: 'Рейтинг',
                        data: values,
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1,
                        fill: true
                    }],
                };

                salesChart = new Chart(salesChartCanvas, {
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
            error: function (error) {
                // alert("Произошла ошибка при загрузке данных. Попробуйте перезагрузить страницу");
            },
        });
    }

    updateChart();
});

$(document).ready(function () {
    const salesChartCanvas = document.getElementById('popularMessages');
    let salesChart = null;


    function updateChart() {
        if (salesChart) {
            salesChart.destroy(); // Уничтожение предыдущего графика при обновлении
        }

        $.ajax({
            url: 'https://192.168.43.33:2323/api/telegram/get/popular/messages',
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                const labels = data.result.map(item => item.text);
                const values = data.result.map(item => item.count);

                const chartData = {
                    labels: labels,
                    datasets: [{
                        label: 'Количество',
                        data: values,
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1,
                        fill: true
                    }],
                };

                salesChart = new Chart(salesChartCanvas, {
                    type: 'line',
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

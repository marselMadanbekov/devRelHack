document.addEventListener('DOMContentLoaded', function () {
    const sendDistributionForm = document.getElementById('send-personal-distribution-form');

    sendDistributionForm.addEventListener('submit', function (e) {
        e.preventDefault();
        document.getElementById("progress-spinner").hidden = false;
        if (confirm("Вы уверены что хотите создать группу?")) {
            let formData = new FormData(this);
            $.ajax({
                url: "/users/send-distribution",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function (response) {
                    document.getElementById("progress-spinner").hidden = true;
                    alert(response.message);
                    window.location.href = "/events";
                },
                error: function (xhr) {
                    document.getElementById("progress-spinner").hidden = true;
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
            });
        }
    })
});

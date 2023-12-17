document.addEventListener('DOMContentLoaded', function () {
    const createGroupForm = document.getElementById('create-event-form');

    createGroupForm.addEventListener('submit', function (e) {
        e.preventDefault();
        document.getElementById("progress-spinner").hidden = false;
        if (confirm("Вы уверены что хотите создать мероприятие?")) {
            let formData = new FormData(this);
            $.ajax({
                url: "/events/create-event",
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

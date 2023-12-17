document.addEventListener('DOMContentLoaded', function () {
    const changeAttendanceEventButtons = document.querySelectorAll('.change-attendance-event');

    changeAttendanceEventButtons.forEach(function (item){
        item.addEventListener("click", function (e){
            e.preventDefault();

            if(confirm("Вы уверены что хотите изменить статус участника?")){
                let eventId = document.getElementById('eventId').value;
                let userId = item.getAttribute("userId");

                let formData = new FormData();
                formData.append("eventId", eventId);
                formData.append("userId", userId);
                $.ajax({
                    url: "/events/change-attendance",
                    type: "POST",
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function (response){
                        alert(response.message);
                        window.location.reload();
                    },
                    error: function (xhr){
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
    })
});

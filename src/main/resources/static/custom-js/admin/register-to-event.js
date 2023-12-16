document.addEventListener('DOMContentLoaded', function () {
    const registerButtons = document.querySelectorAll('.register-to-event');

    registerButtons.forEach(function (item){
        item.addEventListener("click", function (e){
            e.preventDefault();

            if(confirm("Вы уверены что хотите зарегистрироваться на мероприятие?")){
                let eventId = item.getAttribute("eventId");

                let formData = new FormData();
                formData.append("eventId", eventId);
                $.ajax({
                    url: "/events/register",
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

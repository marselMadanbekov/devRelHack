document.addEventListener('DOMContentLoaded', function () {
    const addMediaForm = document.getElementById('add-social-media-form');

    addMediaForm.addEventListener('submit', function (e){
        e.preventDefault();

        if(confirm("Вы уверены что хотите добавить социальную сеть пользователю?")) {
            let formData = new FormData(this);
            $.ajax({
                url: "/users/add-social-media",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
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
            });
        }
    })
});

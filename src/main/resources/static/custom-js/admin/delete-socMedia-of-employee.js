document.addEventListener('DOMContentLoaded', function () {
    const deleteSocMediaButtons = document.querySelectorAll('.delete-media-button');

    deleteSocMediaButtons.forEach(function (item){
        item.addEventListener("click", function (e){
            e.preventDefault();

            if(confirm("Вы уверены что хотите удалить социальную сеть пользователя?")){
                let employeeId = document.getElementById('userId').value;
                let socMedia = item.getAttribute("socMedia");

                let formData = new FormData();
                formData.append("userId", employeeId);
                formData.append("socMedia", socMedia);
                $.ajax({
                    url: "/users/delete-social-media",
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

document.addEventListener('DOMContentLoaded', function () {
    const activateDeactivateButton = document.getElementById('activate-deactivate');

    activateDeactivateButton.addEventListener("click", function (e){
        e.preventDefault();
        if(confirm("Вы уверены что хотите изменить статус этого пользователя?")){
            let formData = new FormData();
            let userdataId = document.getElementById('userdataId').value;
            formData.append("userdataId", userdataId);

            $.ajax({
                url: "/owner-admin/users/activate-deactivate",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function (response){
                    alert(response.message);
                    window.location.reload();
                },
                error: function (xhr){
                    try{
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
});

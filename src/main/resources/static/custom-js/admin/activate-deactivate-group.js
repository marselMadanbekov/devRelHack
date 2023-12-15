document.addEventListener('DOMContentLoaded', function () {
    const activateDeactivateButton = document.getElementById('activate-deactivate-group');

    activateDeactivateButton.addEventListener("click", function (e){
        e.preventDefault();
        if(confirm("Вы уверены что хотите изменить статус этой группы?")){
            let formData = new FormData();
            let groupId = document.getElementById('groupId').value;
            formData.append("groupId", groupId);

            $.ajax({
                url: "/owner-admin/groups/activate-deactivate",
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

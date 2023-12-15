document.addEventListener('DOMContentLoaded', function () {
    const removePupilButtons = document.querySelectorAll('.remove-pupil');

    removePupilButtons.forEach(function (item){
        item.addEventListener("click", function (e){
            e.preventDefault();

            if(confirm("Вы уверены что хотите удалить ученика из этой группы?")){
                let groupId = document.getElementById('groupId').value;
                let pupilId = item.getAttribute("pupilId");

                let formData = new FormData();
                formData.append("groupId", groupId);
                formData.append("pupilId", pupilId);
                $.ajax({
                    url: "/owner-admin/groups/remove-pupil",
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

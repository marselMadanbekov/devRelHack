document.addEventListener('DOMContentLoaded', function () {
    const deletePupilButton = document.getElementById('delete-pupil');

    deletePupilButton.addEventListener("click", function (e){
        e.preventDefault();
        if(confirm("Вы уверены что хотите удалить этого ученика?")){
            let formData = new FormData();
            let pupilId = this.getAttribute("pupilId");
            formData.append("pupilId", pupilId);

            $.ajax({
                url: "/owner-admin/users/delete-pupil",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function (response){
                    alert(response.message);
                    window.location.href = "/admin/pupils";
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

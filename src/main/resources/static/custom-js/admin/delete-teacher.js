document.addEventListener('DOMContentLoaded', function () {
    const deleteTeacherButton = document.getElementById('delete-teacher');

    deleteTeacherButton.addEventListener("click", function (e){
        e.preventDefault();
        if(confirm("Вы уверены что хотите удалить этого учителя?")){
            let formData = new FormData();
            let pupilId = this.getAttribute("teacherId");
            formData.append("teacherId", pupilId);

            $.ajax({
                url: "/owner-admin/users/delete-teacher",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function (response){
                    alert(response.message);
                    window.location.href = "/admin/teachers";
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

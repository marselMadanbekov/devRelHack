document.addEventListener('DOMContentLoaded', function () {
    const deleteSubjectButtons = document.querySelectorAll('.delete-subject-of-teacher');

    deleteSubjectButtons.forEach(function (item){
        item.addEventListener("click", function (e){
            e.preventDefault();

            if(confirm("Вы уверены что хотите удалить предмет для учителя?")){
                let teacherId = document.getElementById('teacherId').value;
                let subjectId = item.getAttribute("subjectId");

                let formData = new FormData();
                formData.append("teacherId", teacherId);
                formData.append("subjectId", subjectId);
                $.ajax({
                    url: "/owner-admin/teachers/delete-subject-for-teacher",
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

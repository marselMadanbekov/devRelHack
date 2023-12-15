document.addEventListener('DOMContentLoaded', function () {
    const deleteLessonButtons = document.querySelectorAll('.delete-lesson');

    deleteLessonButtons.forEach(function (item){
        item.addEventListener("click", function (e){
            e.preventDefault();

            if(confirm("Вы уверены что хотите удалить день из расписания?")){
                let groupId = document.getElementById('groupId').value;
                let dayOfWeek = item.getAttribute("dayOfWeek");

                let formData = new FormData();
                formData.append("groupId", groupId);
                formData.append("dayOfWeek", dayOfWeek);
                $.ajax({
                    url: "/owner-admin/groups/delete-lesson-day",
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

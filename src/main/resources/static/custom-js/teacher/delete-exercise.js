document.addEventListener('DOMContentLoaded', function () {
    const deleteExerciseButtons = document.querySelectorAll(".delete-exercise");

    deleteExerciseButtons.forEach( function (item){
        item.addEventListener("click", function (e){
            e.preventDefault();
            if(confirm("Вы уверены что хотите удалить упражнение?")){
                let formData = new FormData();
                let exerciseId = item.getAttribute("exerciseId");
                formData.append("exerciseId", exerciseId);
                $.ajax({
                    url: "/teacher/homeworks/delete-exercise",
                    type: "POST",
                    data: formData,
                    contentType: false,
                    processData: false,
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
                })
            }
        })
    });
});

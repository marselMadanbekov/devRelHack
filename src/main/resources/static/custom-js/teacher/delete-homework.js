document.addEventListener('DOMContentLoaded', function () {
    const deleteHomeworkButtons = document.querySelectorAll(".delete-homework");

    deleteHomeworkButtons.forEach( function (item){
        item.addEventListener("click", function (e){
            e.preventDefault();
            if(confirm("Вы уверены что хотите удалить домашнее задание?")){
                let formData = new FormData();
                let homeworkId = item.getAttribute("homeworkId");
                formData.append("homeworkId", homeworkId);
                $.ajax({
                    url: "/teacher/homeworks/delete-homework",
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

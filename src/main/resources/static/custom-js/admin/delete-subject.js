document.addEventListener('DOMContentLoaded', function () {
    const deleteSubjectButton = document.getElementById('delete-subject');

    deleteSubjectButton.addEventListener("click", function (e){
        e.preventDefault();
        if(confirm("Вы уверены что хотите удалить этот предмет?")){
            let formData = new FormData();
            let subjectId = this.getAttribute("subjectId");
            formData.append("subjectId", subjectId);

            $.ajax({
                url: "/owner-admin/subjects/delete-subject",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function (response){
                    alert(response.message);
                    window.location.href = "/main";
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

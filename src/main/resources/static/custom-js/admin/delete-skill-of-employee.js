document.addEventListener('DOMContentLoaded', function () {
    const deleteSkillButtons = document.querySelectorAll('.delete-skill-of-employee');

    deleteSkillButtons.forEach(function (item){
        item.addEventListener("click", function (e){
            e.preventDefault();

            if(confirm("Вы уверены что хотите удалить навык пользователя?")){
                let employeeId = document.getElementById('employeeId').value;
                let skill = item.getAttribute("skill");

                let formData = new FormData();
                formData.append("employeeId", employeeId);
                formData.append("skill", skill);
                $.ajax({
                    url: "/users/delete-skill",
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

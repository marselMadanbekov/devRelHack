document.addEventListener('DOMContentLoaded', function () {
    const addSkillForm = document.getElementById('add-skill-form');

    addSkillForm.addEventListener('submit', function (e){
        e.preventDefault();

        if(confirm("Вы уверены что хотите добавить навык пользователю?")) {
            let formData = new FormData(this);
            $.ajax({
                url: "/users/add-skill",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
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
            });
        }
    })
});

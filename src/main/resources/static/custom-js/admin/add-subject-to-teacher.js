document.addEventListener('DOMContentLoaded', function () {
    const addSubjectToTeacher = document.getElementById('add-subject-to-teacher-form');

    addSubjectToTeacher.addEventListener('submit', function (e){
        e.preventDefault();

        if(confirm("Вы уверены что хотите добавить предмет учителю?")) {
            let formData = new FormData(this);
            $.ajax({
                url: "/owner-admin/teachers/add-subject-to-teacher",
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

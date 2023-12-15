document.addEventListener('DOMContentLoaded', function () {
    const addLessonToTimetable = document.getElementById('add-lesson-to-timetable-form');

    addLessonToTimetable.addEventListener('submit', function (e) {
        e.preventDefault();

        if (confirm("Вы уверены что хотите изменить расписание?")) {
            let formData = new FormData(this);
            document.getElementById("progress-spinner-timetable").hidden = false;
            $.ajax({
                url: "/owner-admin/groups/add-lesson",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function (response) {
                    document.getElementById("progress-spinner-timetable").hidden = true;
                    alert(response.message);
                    window.location.reload();
                },
                error: function (xhr) {
                    document.getElementById("progress-spinner-timetable").hidden = true;
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

document.addEventListener('DOMContentLoaded', function () {
    const pupilsStateCheckBoxes = document.querySelectorAll(".form-check-input");
    const editAttendance = document.getElementById("edit-attendance");

    editAttendance.addEventListener("click", function (e){
        e.preventDefault();
        let pupilData = {};
        let lessonID = document.getElementById("lessonId").value;

        pupilsStateCheckBoxes.forEach(function (item){
            let pupilId = item.getAttribute('pupilId');
            pupilData[pupilId] = item.checked;
        })

        let formData = {
            lessonId: lessonID,
            attendance :pupilData
        }
        if(confirm("Вы уверены что хотите сохранить изменения?")){
            $.ajax({
                url: "/teacher/lessons/edit-attendance",
                type: "POST",
                data: JSON.stringify(formData),
                contentType: 'application/json',
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

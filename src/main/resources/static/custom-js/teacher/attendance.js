document.addEventListener('DOMContentLoaded', function () {
    const lessonCreateButton = document.getElementById("lesson-create-form");
    const pupilsStateCheckBoxes = document.querySelectorAll(".form-check-input");

    lessonCreateButton.addEventListener("submit", function (e){
        e.preventDefault();
        let pupilData = {};
        let groupId = document.getElementById("groupId").value;
        let date = document.getElementById("date").value;

        pupilsStateCheckBoxes.forEach(function (item){
            let pupilId = item.getAttribute('pupilId');
            pupilData[pupilId] = item.checked;
        })

        let formData = {
            date: date,
            groupId: groupId,
            attendance :pupilData
        }
        if(confirm("Вы уверены что хотите сохранить посещаемость?")){
            $.ajax({
                url: "/teacher/lessons/submit-attendance",
                type: "POST",
                data: JSON.stringify(formData),
                contentType: 'application/json',
                success: function (response) {
                    alert(response.message);
                    window.location.href = "/teacher/lessons/group-lessons?groupId=" + groupId;
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

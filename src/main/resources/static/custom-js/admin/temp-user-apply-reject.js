document.addEventListener('DOMContentLoaded', function () {
    const applyForm = document.getElementById("temp-user-apply-form");
    const rejectForm = document.getElementById("temp-user-reject-form");

    applyForm.addEventListener("submit", function (e){
        e.preventDefault();

        if(confirm("Вы уверены что хотите принять этот запрос?")){
            document.getElementById("progress-spinner").hidden = false;
            $.ajax({
                url: "/owner-admin/temp-users/apply",
                type: "POST",
                data: new FormData(this),
                processData: false,
                contentType: false,
                success: function (response) {
                    document.getElementById("progress-spinner").hidden = true;
                    alert(response.message);
                },
                error: function (xhr) {
                    document.getElementById("progress-spinner").hidden = true;

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
    });

    rejectForm.addEventListener("submit", function (e){
        e.preventDefault();

        if(confirm("Вы уверены что хотите отклонить этот запрос?")){
            document.getElementById("progress-spinner").hidden = false;
            $.ajax({
                url: "/owner-admin/temp-users/reject",
                type: "POST",
                data: new FormData(this),
                processData: false,
                contentType: false,
                success: function (response) {
                    document.getElementById("progress-spinner").hidden = true;
                    alert(response.message);
                },
                error: function (xhr) {
                    document.getElementById("progress-spinner").hidden = true;

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

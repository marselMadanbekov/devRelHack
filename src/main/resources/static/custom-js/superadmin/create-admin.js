document.addEventListener('DOMContentLoaded', function () {
    const createAdminForm = document.getElementById('create-owner-form');

    createAdminForm.addEventListener('submit', function (e){
        e.preventDefault();
        document.getElementById("progress-spinner").hidden = false;

        if(confirm("Вы уверены что хотите создать администратора?")) {
            let formData = new FormData(this);
            $.ajax({
                url: "/super-admin-owner/admins/create-admin",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function (response) {
                    document.getElementById("progress-spinner").hidden = true;
                    alert(response.message);
                    window.location.reload();
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

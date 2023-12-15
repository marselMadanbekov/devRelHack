document.addEventListener('DOMContentLoaded', function () {
    const createOwnerForm = document.getElementById('create-owner-form');
    const setOwnerForm = document.getElementById('set-owner-form');

    setOwnerForm.addEventListener('submit',function (e){
        e.preventDefault();
        document.getElementById("progress-spinner").hidden = false;


        if(confirm("Вы уверены что хотите назначить владельца?")){
            let formData = new FormData(this);
            $.ajax({
                url:"/super-admin/branches/set-owner",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success:function (response){
                    document.getElementById("progress-spinner").hidden = true;
                    alert(response.message);
                    window.location.href = '/super-admin/branches';
                },
                error: function (xhr){
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
            })
        }
    })

    createOwnerForm.addEventListener('submit', function (e){
        e.preventDefault();

        if(confirm("Вы уверены что хотите создать владельца?")) {
            let formData = new FormData(this);
            $.ajax({
                url: "/super-admin/owners/create-owner",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function (response) {
                    alert(response.message);
                    window.location.href = '/super-admin/branches';
                },
                error: function (xhr, status, error) {
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

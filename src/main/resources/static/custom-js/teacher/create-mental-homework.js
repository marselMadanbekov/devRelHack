document.addEventListener('DOMContentLoaded', function () {
    const testExerciseCreateButton = document.getElementById("createTestExercise");
    const trainerExerciseCreateButton = document.getElementById("createTrainerExercise");
    const mentalHomeworkForm = document.getElementById("mental-homework-form");

    let psv = document.getElementById("PSV");
    let pb = document.getElementById("PB");
    let pd = document.getElementById("PD");
    let topic = "";
    let speed = document.getElementById("speed").value;
    let type = 0;


    psv.addEventListener("change", function (e){
        topic = "PSV";
        pb.value = 0;
        pd.value = 0;
    });

    pb.addEventListener("change", function (e){
        topic = pb.value;
        psv.value = 0;
        pd.value = 0;
    });

    pd.addEventListener("change", function (e){
        topic = pd.value;
        psv.value = 0;
        pb.value = 0;
    });

    testExerciseCreateButton.addEventListener("click", function (e){
        e.preventDefault();
        if(topic === ""){
            alert("Выберите одну из тем!");
        }
        else{
            type = 0;
            if(mentalHomeworkForm.checkValidity()){
                createExercise();
            }else {
                alert("Заполните обязательные поля!");
            }
        }
    });


    trainerExerciseCreateButton.addEventListener("click", function (e) {
        e.preventDefault();
        speed = document.getElementById("speed").value;
        if(topic === ""){
            alert("Выберите одну из тем!");
        }else{
            if(speed == null || speed === ""){
                alert("Укажите скорость!");
            }else{
                type = 1;
                if(mentalHomeworkForm.checkValidity()){
                    createExercise();
                }else {
                    alert("Заполните обязательные поля!");
                }
            }
        }
    })

    function createExercise(){
        if(confirm("Вы уверены что хотите добавить упражнение?")){

            let formData = new FormData();
            formData.append("type", type);
            formData.append("topic", topic);
            formData.append("digitsCount", document.getElementById("digitsCount").value);
            formData.append("numbersCount", document.getElementById("numbersCount").value);
            formData.append("questionsCount", document.getElementById("questionsCount").value);
            formData.append("homeworkId", document.getElementById("homeworkId").value);
            formData.append("speed",speed);
            $.ajax({
                url: "/teacher/homeworks/add-mental-exercise",
                type: "POST",
                data: formData,
                contentType: false,
                processData: false,
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
    }
});

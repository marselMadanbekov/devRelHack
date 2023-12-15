document.addEventListener('DOMContentLoaded', function () {
    const homeworkCreateButtons = document.querySelectorAll(".createHomeworkButton");
    const deadLineInput = document.getElementById("deadLine");

    homeworkCreateButtons.forEach( function (item){
        item.addEventListener("click", function (e){
            e.preventDefault();
            if(deadLineInput.value == null || deadLineInput.value === ''){
                alert("Выберите дату завершения домашнего задания");
            }else {
                let groupId = document.getElementById("groupId").value;
                let deadLine = document.getElementById("deadLine").value;
                let pupilId = item.getAttribute("pupilId");
                window.location.href = '/teacher/homeworks/create-mental-homework?groupId=' + groupId + '&pupilId=' +pupilId + '&deadLine=' + deadLine;
            }
        })
    });
});

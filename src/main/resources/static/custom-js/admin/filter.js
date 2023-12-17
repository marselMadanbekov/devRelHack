document.addEventListener('DOMContentLoaded', function () {
    const filterButton = document.getElementById('filter');

    filterButton.addEventListener("click", function (e) {
        e.preventDefault();

        let level = document.getElementById("levelSelect").value;
        let skill = document.getElementById("skillSelect").value;

        window.location.href = '/users?level=' + level + '&skill=' + skill;
    })
});

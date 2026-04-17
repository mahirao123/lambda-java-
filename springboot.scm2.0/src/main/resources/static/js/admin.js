document.addEventListener("DOMContentLoaded", function () {

    console.log("admin user");

    document.querySelector("#image_file_input")
        .addEventListener("change", function (event) {

            let file = event.target.files[0];

            if (!file) return;

            if (!file.type.startsWith("image/")) {
                alert("Only image files allowed!");
                event.target.value = "";
                return;
            }

            let reader = new FileReader();

            reader.onload = function () {
                document.querySelector("#upload_image_preview")
                    .setAttribute("src", reader.result);
            };

            reader.readAsDataURL(file);
        });

});
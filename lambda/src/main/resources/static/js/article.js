document.addEventListener("DOMContentLoaded", function () {


    /* =========================================================
       CATEGORY → SUBCATEGORY
    ========================================================== */

    const categorySelect =
        document.getElementById("category");

    const subCategorySelect =
        document.getElementById("subCategory");


    if (categorySelect && subCategorySelect) {


        categorySelect.addEventListener("change", function () {


            const categoryId = this.value;


            // Reset subcategory

            subCategorySelect.innerHTML =
                '<option value="">Loading...</option>';

            subCategorySelect.disabled = true;


            // No category selected

            if (!categoryId) {

                subCategorySelect.innerHTML =
                    '<option value="">Select SubCategory</option>';

                return;
            }


            // API call

            fetch(
                "/editor/category/subcategories/" + categoryId,
                {
                    method: "GET",
                    headers: {
                        "Accept": "application/json"
                    }
                }
            )


            .then(function (response) {

                if (!response.ok) {

                    throw new Error(
                        "HTTP Error: " + response.status
                    );

                }

                return response.json();

            })


            .then(function (data) {


                // Clear loading

                subCategorySelect.innerHTML =
                    '<option value="">Select SubCategory</option>';


                // No subcategory

                if (!Array.isArray(data) || data.length === 0) {

                    subCategorySelect.innerHTML =
                        '<option value="">No SubCategory available</option>';

                    subCategorySelect.disabled = true;

                    return;
                }


                // Add subcategories

                data.forEach(function (subCategory) {


                    const option =
                        document.createElement("option");


                    option.value =
                        subCategory.id;


                    option.textContent =
                        subCategory.name;


                    subCategorySelect.appendChild(option);

                });


                // Enable select

                subCategorySelect.disabled = false;

            })


            .catch(function (error) {


                console.error(
                    "Subcategory loading error:",
                    error
                );


                subCategorySelect.innerHTML =
                    '<option value="">Failed to load SubCategory</option>';

                subCategorySelect.disabled = true;

            });

        });

    }



    /* =========================================================
       IMAGE PREVIEW
    ========================================================== */

    const imageInput =
        document.getElementById("images");

    const imagePreview =
        document.getElementById("imagePreview");


    if (imageInput && imagePreview) {


        imageInput.addEventListener("change", function () {


            imagePreview.innerHTML = "";


            const files =
                Array.from(this.files);


            files.forEach(function (file) {


                if (!file.type.startsWith("image/")) {

                    return;

                }


                const reader =
                    new FileReader();


                reader.onload =
                    function (event) {


                        const wrapper =
                            document.createElement("div");


                        wrapper.className =
                            "relative rounded-lg overflow-hidden " +
                            "border border-gray-200 " +
                            "dark:border-gray-700";


                        wrapper.innerHTML = `

                            <img
                                src="${event.target.result}"
                                alt="Article Image"
                                class="w-full h-40 object-cover">


                            <div
                                class="absolute bottom-0
                                       left-0 right-0
                                       bg-black/60
                                       text-white
                                       text-xs
                                       p-2
                                       truncate">

                                ${file.name}

                            </div>

                        `;


                        imagePreview.appendChild(wrapper);

                    };


                reader.readAsDataURL(file);

            });

        });

    }



    /* =========================================================
       VIDEO PREVIEW
    ========================================================== */

    const videoInput =
        document.getElementById("videos");

    const videoPreview =
        document.getElementById("videoPreview");


    if (videoInput && videoPreview) {


        videoInput.addEventListener("change", function () {


            videoPreview.innerHTML = "";


            const files =
                Array.from(this.files);


            files.forEach(function (file) {


                if (!file.type.startsWith("video/")) {

                    return;

                }


                const url =
                    URL.createObjectURL(file);


                const wrapper =
                    document.createElement("div");


                wrapper.className =
                    "relative rounded-xl " +
                    "overflow-hidden bg-black";


                wrapper.innerHTML = `

                    <video
                        src="${url}"
                        controls
                        preload="metadata"
                        class="w-full h-64 object-cover">

                    </video>


                    <div
                        class="bg-black text-white
                               text-xs p-2 truncate">

                        ${file.name}

                    </div>

                `;


                videoPreview.appendChild(wrapper);

            });

        });

    }



    /* =========================================================
       FORM SUBMIT PROTECTION
    ========================================================== */

    const articleForm =
        document.getElementById("articleForm");


    const submitButton =
        document.getElementById("submitButton");


    if (articleForm && submitButton) {


        articleForm.addEventListener(
            "submit",
            function () {


                /*
                 * Disabled fields are NOT submitted by HTML forms.
                 * Therefore enable subcategory before submit.
                 */

                if (subCategorySelect) {

                    subCategorySelect.disabled = false;

                }


                submitButton.disabled = true;


                submitButton.innerHTML =
                    "Saving Article...";


            }
        );

    }

});

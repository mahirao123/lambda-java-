document.addEventListener("DOMContentLoaded", function () {

    /* =====================================================
       CURRENT DATE
       Format: dd/MM/yyyy
       ===================================================== */

    function getCurrentDate() {

        const today = new Date();

        const day = String(
            today.getDate()
        ).padStart(2, "0");

        const month = String(
            today.getMonth() + 1
        ).padStart(2, "0");

        const year = today.getFullYear();

        return `${day}/${month}/${year}`;
    }


    /* =====================================================
       CURRENT DAY
       Example: Thursday
       ===================================================== */

    function getCurrentDay() {

        const today = new Date();

        return today.toLocaleDateString(
            "en-US",
            {
                weekday: "long"
            }
        );
    }


    /* =====================================================
       FORMAT DATE
       Converts yyyy-MM-dd → dd/MM/yyyy
       ===================================================== */

    function formatDateForDisplay(dateValue) {

        if (!dateValue) {
            return "";
        }


        // Already dd/MM/yyyy

        if (
            dateValue.includes("/") &&
            dateValue.length === 10
        ) {
            return dateValue;
        }


        // yyyy-MM-dd

        const parts = dateValue.split("-");


        if (parts.length === 3) {

            return (
                parts[2]
                + "/"
                + parts[1]
                + "/"
                + parts[0]
            );

        }


        return dateValue;
    }



    /* =====================================================
       ADD CATEGORY MODAL
       ===================================================== */

    window.openCategoryModal = function () {

        const modal =
            document.getElementById(
                "categoryModal"
            );


        const dateInput =
            document.getElementById(
                "categoryDate"
            );


        const dayInput =
            document.getElementById(
                "categoryDay"
            );


        // Set today's date

        if (dateInput) {

            dateInput.value =
                getCurrentDate();

        }


        // Set today's day

        if (dayInput) {

            dayInput.value =
                getCurrentDay();

        }


        if (modal) {

            modal.classList.remove(
                "hidden"
            );

        }

    };



    /* =====================================================
       CLOSE ADD CATEGORY
       ===================================================== */

    window.closeCategoryModal = function () {

        const modal =
            document.getElementById(
                "categoryModal"
            );


        if (modal) {

            modal.classList.add(
                "hidden"
            );

        }

    };



    /* =====================================================
       UPDATE CATEGORY MODAL
       ===================================================== */

    window.openEditCategoryModal = function (button) {

        const modal =
            document.getElementById(
                "editCategoryModal"
            );


        const id =
            button.getAttribute(
                "data-id"
            );


        const date =
            button.getAttribute(
                "data-date"
            );


        const day =
            button.getAttribute(
                "data-day"
            );


        document.getElementById(
            "editCategoryId"
        ).value = id || "";


        document.getElementById(
            "editCategoryDate"
        ).value =
            formatDateForDisplay(date);


        document.getElementById(
            "editCategoryDay"
        ).value =
            day || "";


        if (modal) {

            modal.classList.remove(
                "hidden"
            );

        }

    };



    /* =====================================================
       CLOSE UPDATE CATEGORY
       ===================================================== */

    window.closeEditCategoryModal = function () {

        const modal =
            document.getElementById(
                "editCategoryModal"
            );


        if (modal) {

            modal.classList.add(
                "hidden"
            );

        }

    };



    /* =====================================================
       ADD SUBCATEGORY MODAL
       ===================================================== */

    window.openSubCategoryModal = function (button) {

        const modal =
            document.getElementById(
                "subCategoryModal"
            );


        const categoryId =
            button.getAttribute(
                "data-category-id"
            );


        const categoryDate =
            button.getAttribute(
                "data-category-date"
            );


        const categoryDay =
            button.getAttribute(
                "data-category-day"
            );


        /* Set category ID */

        document.getElementById(
            "categoryId"
        ).value =
            categoryId || "";


        /* Display category information */

        const selectedCategory =
            document.getElementById(
                "selectedCategory"
            );


        if (selectedCategory) {

            selectedCategory.textContent =
                "Category: "
                + formatDateForDisplay(
                    categoryDate
                )
                + " • "
                + (categoryDay || "");

        }


        /* Clear old subcategory name */

        const nameInput =
            document.getElementById(
                "subCategoryName"
            );


        if (nameInput) {

            nameInput.value = "";

        }


        /* Open modal */

        if (modal) {

            modal.classList.remove(
                "hidden"
            );

        }


        /* Focus input */

        setTimeout(function () {

            if (nameInput) {

                nameInput.focus();

            }

        }, 100);

    };



    /* =====================================================
       CLOSE ADD SUBCATEGORY
       ===================================================== */

    window.closeSubCategoryModal = function () {

        const modal =
            document.getElementById(
                "subCategoryModal"
            );


        if (modal) {

            modal.classList.add(
                "hidden"
            );

        }

    };



    /* =====================================================
       UPDATE SUBCATEGORY MODAL
       ===================================================== */

    window.openEditSubCategoryModal =
        function (button) {


            const modal =
                document.getElementById(
                    "editSubCategoryModal"
                );


            const id =
                button.getAttribute(
                    "data-id"
                );


            const name =
                button.getAttribute(
                    "data-name"
                );


            const categoryDate =
                button.getAttribute(
                    "data-category-date"
                );


            const categoryDay =
                button.getAttribute(
                    "data-category-day"
                );


            /* Set subcategory ID */

            document.getElementById(
                "editSubCategoryId"
            ).value =
                id || "";


            /* Set subcategory name */

            document.getElementById(
                "editSubCategoryName"
            ).value =
                name || "";


            /* Display category information */

            const categoryText =
                document.getElementById(
                    "editSubCategoryCategory"
                );


            if (categoryText) {

                categoryText.textContent =
                    "Category: "
                    + formatDateForDisplay(
                        categoryDate
                    )
                    + " • "
                    + (categoryDay || "");

            }


            /* Open modal */

            if (modal) {

                modal.classList.remove(
                    "hidden"
                );

            }


            /* Focus input */

            setTimeout(function () {

                const nameInput =
                    document.getElementById(
                        "editSubCategoryName"
                    );


                if (nameInput) {

                    nameInput.focus();

                }

            }, 100);

        };



    /* =====================================================
       CLOSE UPDATE SUBCATEGORY
       ===================================================== */

    window.closeEditSubCategoryModal =
        function () {

            const modal =
                document.getElementById(
                    "editSubCategoryModal"
                );


            if (modal) {

                modal.classList.add(
                    "hidden"
                );

            }

        };



    /* =====================================================
       CLICK OUTSIDE MODAL TO CLOSE
       ===================================================== */

    window.addEventListener(
        "click",
        function (event) {


            const modalIds = [

                "categoryModal",

                "editCategoryModal",

                "subCategoryModal",

                "editSubCategoryModal"

            ];


            modalIds.forEach(
                function (modalId) {


                    const modal =
                        document.getElementById(
                            modalId
                        );


                    if (
                        modal &&
                        event.target === modal
                    ) {

                        modal.classList.add(
                            "hidden"
                        );

                    }

                }
            );

        }
    );



    /* =====================================================
       ESC KEY CLOSE
       ===================================================== */

    document.addEventListener(
        "keydown",
        function (event) {


            if (
                event.key !== "Escape"
            ) {

                return;

            }


            closeCategoryModal();

            closeEditCategoryModal();

            closeSubCategoryModal();

            closeEditSubCategoryModal();

        }
    );

});
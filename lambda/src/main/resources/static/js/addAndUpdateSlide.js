document.addEventListener("DOMContentLoaded", function () {

    const fileInput = document.getElementById("image_file_input");

    const imagePreview =
        document.getElementById("upload_image_preview");

    const videoPreview =
        document.getElementById("upload_video_preview");

    const noMedia =
        document.getElementById("no_media_preview");


    // Safety check
    if (!fileInput || !imagePreview || !videoPreview || !noMedia) {

        console.error("Media preview elements are missing.");

        return;
    }


    /*
     * Hide all previews
     */
    function hidePreviews() {

        imagePreview.classList.add("hidden");

        videoPreview.classList.add("hidden");

        noMedia.classList.add("hidden");


        // Stop video
        videoPreview.pause();

        videoPreview.removeAttribute("src");

        videoPreview.load();
    }


    /*
     * Show "No media selected"
     */
    function showNoMedia(message = "No media selected") {

        hidePreviews();

        noMedia.textContent = message;

        noMedia.classList.remove("hidden");
    }


    /*
     * File selected
     */
    fileInput.addEventListener("change", function () {

        const file = this.files[0];


        // No file
        if (!file) {

            showNoMedia();

            return;
        }


        console.log("Selected file:", file.name);

        console.log("File type:", file.type);


        // Hide previous preview
        hidePreviews();


        // Create temporary browser URL
        const objectUrl = URL.createObjectURL(file);


        /*
         * IMAGE
         */
        if (file.type.startsWith("image/")) {

            console.log("Image selected");


            imagePreview.src = objectUrl;

            imagePreview.classList.remove("hidden");


            // Release old object URL after image loads
            imagePreview.onload = function () {

                console.log("Image preview loaded");

            };

        }


        /*
         * VIDEO
         */
        else if (file.type.startsWith("video/")) {

            console.log("Video selected");


            videoPreview.src = objectUrl;

            videoPreview.classList.remove("hidden");

            videoPreview.muted = true;

            videoPreview.currentTime = 0;


            videoPreview.play()
                .then(function () {

                    console.log("Video preview playing");

                })
                .catch(function (error) {

                    console.log(
                        "Video autoplay blocked:",
                        error
                    );

                });

        }


        /*
         * Unsupported file
         */
        else {

            URL.revokeObjectURL(objectUrl);

            showNoMedia(
                "Please select an image or video."
            );

        }

    });


    /*
     * Pause video when mouse enters
     */
    videoPreview.addEventListener(
        "mouseenter",
        function () {

            if (!videoPreview.paused) {

                videoPreview.pause();

            }

        }
    );


    /*
     * Play video when mouse leaves
     */
    videoPreview.addEventListener(
        "mouseleave",
        function () {

            if (videoPreview.src) {

                videoPreview.play()
                    .catch(function () {});

            }

        }
    );


    /*
     * UPDATE PAGE
     *
     * Show existing media if available.
     */
    const existingMediaUrl =
        /*[[${sliderForm.mediaUrl}]]*/ null;


    if (existingMediaUrl &&
        existingMediaUrl !== "null" &&
        existingMediaUrl !== "") {

        console.log(
            "Existing media:",
            existingMediaUrl
        );


        /*
         * Determine existing media type
         */
        const lowerUrl =
            existingMediaUrl.toLowerCase();


        const isVideo =
            lowerUrl.includes(".mp4") ||
            lowerUrl.includes(".webm") ||
            lowerUrl.includes(".mov") ||
            lowerUrl.includes(".m4v") ||
            lowerUrl.includes(".ogg");


        hidePreviews();


        if (isVideo) {

            videoPreview.src =
                existingMediaUrl;

            videoPreview.classList.remove("hidden");

            videoPreview.muted = true;

            videoPreview.play()
                .catch(function () {

                    console.log(
                        "Existing video autoplay blocked"
                    );

                });

        } else {

            imagePreview.src =
                existingMediaUrl;

            imagePreview.classList.remove("hidden");

        }

    }

});

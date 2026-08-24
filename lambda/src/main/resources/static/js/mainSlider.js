document.addEventListener("DOMContentLoaded", function () {

    /* =====================================================
       ELEMENTS
       ===================================================== */

    const slider =
        document.getElementById("mainSlider");

    const track =
        slider?.querySelector(".main-slider-track");

    if (!slider || !track) {

        console.log("Main slider not found");

        return;
    }


    const slides =
        Array.from(
            track.querySelectorAll(".main-slide")
        );


    if (slides.length === 0) {

        console.log("No main slides found");

        return;
    }


    /* =====================================================
       SETTINGS
       ===================================================== */

    let currentIndex = 0;

    let autoTimer = null;

    const AUTO_DELAY = 5000;

    let isAnimating = false;


    /* =====================================================
       INITIAL SLIDE SETUP
       ===================================================== */

    slides.forEach((slide, index) => {

        slide.style.position = "absolute";
        slide.style.inset = "0";

        slide.style.opacity =
            index === 0 ? "1" : "0";

        slide.style.visibility =
            index === 0 ? "visible" : "hidden";

        slide.style.transform =
            index === 0
                ? "rotateY(0deg)"
                : "rotateY(180deg)";

        slide.style.transition =
            "transform 800ms ease-in-out, opacity 400ms ease";

    });


    /* =====================================================
       MOVE SLIDER - 360 DEGREE
       ===================================================== */

    function moveSlider(newIndex, direction = 1) {

        if (isAnimating) {
            return;
        }


        if (slides.length <= 1) {
            return;
        }


        if (newIndex < 0) {

            newIndex =
                slides.length - 1;

        }


        if (newIndex >= slides.length) {

            newIndex = 0;

        }


        if (newIndex === currentIndex) {
            return;
        }


        isAnimating = true;


        const currentSlide =
            slides[currentIndex];

        const nextSlide =
            slides[newIndex];


        /* =================================================
           PREPARE NEXT SLIDE
           ================================================= */

        nextSlide.style.visibility =
            "visible";

        nextSlide.style.opacity =
            "1";


        /*
         * Start next slide rotated.
         */

        nextSlide.style.transform =
            direction > 0
                ? "rotateY(-180deg)"
                : "rotateY(180deg)";


        /*
         * Force browser reflow.
         */

        nextSlide.offsetHeight;


        /* =================================================
           ROTATE CURRENT SLIDE
           ================================================= */

        currentSlide.style.transform =
            direction > 0
                ? "rotateY(180deg)"
                : "rotateY(-180deg)";


        /* =================================================
           ROTATE NEXT SLIDE TO FRONT
           ================================================= */

        nextSlide.style.transform =
            "rotateY(0deg)";


        /* =================================================
           UPDATE INDEX
           ================================================= */

        currentIndex =
            newIndex;


        updateDots();


        stopAllVideos();


        /*
         * Wait until rotation finishes.
         */

        setTimeout(
            function () {

                currentSlide.style.opacity =
                    "0";

                currentSlide.style.visibility =
                    "hidden";


                currentSlide.style.transform =
                    "rotateY(180deg)";


                nextSlide.style.opacity =
                    "1";

                nextSlide.style.visibility =
                    "visible";


                playCurrentVideo();


                isAnimating = false;

            },
            850
        );

    }


    /* =====================================================
       STOP ALL VIDEOS
       ===================================================== */

    function stopAllVideos() {

        slides.forEach(slide => {

            const video =
                slide.querySelector(
                    ".main-slide-video"
                );


            if (!video) {
                return;
            }


            video.pause();

            video.currentTime = 0;

            video.muted = true;

            updateMuteIcon(video);

        });

    }


    /* =====================================================
       PLAY CURRENT VIDEO
       ===================================================== */

    function playCurrentVideo() {

        const slide =
            slides[currentIndex];


        if (!slide) {
            return;
        }


        const video =
            slide.querySelector(
                ".main-slide-video"
            );


        if (!video) {
            return;
        }


        /*
         * Every new slide starts muted.
         */

        video.muted = true;


        video.play()
            .catch(() => {});


        updateMuteIcon(video);

    }


    /* =====================================================
       UPDATE MUTE ICON
       ===================================================== */

    function updateMuteIcon(video) {

        const slide =
            video.closest(".main-slide");


        if (!slide) {
            return;
        }


        const button =
            slide.querySelector(
                ".main-video-mute"
            );


        if (!button) {
            return;
        }


        const icon =
            button.querySelector("i");


        if (!icon) {
            return;
        }


        if (video.muted) {

            icon.classList.remove(
                "fa-volume-high"
            );

            icon.classList.add(
                "fa-volume-xmark"
            );

        } else {

            icon.classList.remove(
                "fa-volume-xmark"
            );

            icon.classList.add(
                "fa-volume-high"
            );

        }

    }


    /* =====================================================
       CLICK ANYWHERE ON VIDEO/SLIDE
       MUTE / UNMUTE
       ===================================================== */

    slides.forEach(slide => {

        const video =
            slide.querySelector(
                ".main-slide-video"
            );


        if (!video) {
            return;
        }


        slide.addEventListener(
            "click",
            function (event) {

                /*
                 * Ignore controls.
                 */

                if (
                    event.target.closest(
                        ".main-slider-button"
                    ) ||
                    event.target.closest(
                        ".main-slider-dot"
                    ) ||
                    event.target.closest(
                        ".main-video-mute"
                    )
                ) {

                    return;

                }


                /*
                 * Only active slide.
                 */

                if (
                    slide !==
                    slides[currentIndex]
                ) {

                    return;

                }


                /*
                 * Play if paused.
                 */

                if (video.paused) {

                    video.play()
                        .catch(() => {});

                }


                /*
                 * Toggle mute.
                 */

                video.muted =
                    !video.muted;


                updateMuteIcon(video);

            }
        );

    });


    /* =====================================================
       MUTE BUTTON
       ===================================================== */

    slider
        .querySelectorAll(
            ".main-video-mute"
        )
        .forEach(button => {

            button.addEventListener(
                "click",
                function (event) {

                    event.preventDefault();

                    event.stopPropagation();


                    const slide =
                        button.closest(
                            ".main-slide"
                        );


                    if (!slide) {
                        return;
                    }


                    const video =
                        slide.querySelector(
                            ".main-slide-video"
                        );


                    if (!video) {
                        return;
                    }


                    if (video.paused) {

                        video.play()
                            .catch(() => {});

                    }


                    video.muted =
                        !video.muted;


                    updateMuteIcon(video);

                }
            );

        });


    /* =====================================================
       UPDATE DOTS
       ===================================================== */

    function updateDots() {

        slider
            .querySelectorAll(
                ".main-slider-dot"
            )
            .forEach(
                (dot, index) => {

                    dot.classList.toggle(
                        "active",
                        index === currentIndex
                    );

                }
            );

    }


    /* =====================================================
       NEXT
       ===================================================== */

    function nextSlide() {

        moveSlider(
            currentIndex + 1,
            1
        );

    }


    /* =====================================================
       PREVIOUS
       ===================================================== */

    function previousSlide() {

        moveSlider(
            currentIndex - 1,
            -1
        );

    }


    /* =====================================================
       NEXT BUTTON
       ===================================================== */

    const nextButton =
        slider.querySelector(
            ".main-slider-next"
        );


    if (nextButton) {

        nextButton.addEventListener(
            "click",
            function (event) {

                event.preventDefault();

                event.stopPropagation();

                nextSlide();

            }
        );

    }


    /* =====================================================
       PREVIOUS BUTTON
       ===================================================== */

    const prevButton =
        slider.querySelector(
            ".main-slider-prev"
        );


    if (prevButton) {

        prevButton.addEventListener(
            "click",
            function (event) {

                event.preventDefault();

                event.stopPropagation();

                previousSlide();

            }
        );

    }


    /* =====================================================
       PAGINATION
       ===================================================== */

    slider
        .querySelectorAll(
            ".main-slider-dot"
        )
        .forEach(
            (dot, index) => {

                dot.addEventListener(
                    "click",
                    function (event) {

                        event.preventDefault();

                        event.stopPropagation();


                        const direction =
                            index > currentIndex
                                ? 1
                                : -1;


                        moveSlider(
                            index,
                            direction
                        );

                    }
                );

            }
        );


    /* =====================================================
       AUTO SLIDE
       ===================================================== */

    function startAutoSlide() {

        stopAutoSlide();


        if (slides.length <= 1) {
            return;
        }


        autoTimer =
            setInterval(
                function () {

                    if (!isAnimating) {

                        nextSlide();

                    }

                },
                AUTO_DELAY
            );

    }


    /* =====================================================
       STOP AUTO SLIDE
       ===================================================== */

    function stopAutoSlide() {

        if (autoTimer !== null) {

            clearInterval(autoTimer);

            autoTimer = null;

        }

    }


    /* =====================================================
       MOUSE ENTER
       ===================================================== */

    slider.addEventListener(
        "mouseenter",
        function () {

            stopAutoSlide();

        }
    );


    /* =====================================================
       MOUSE LEAVE
       ===================================================== */

    slider.addEventListener(
        "mouseleave",
        function () {

            startAutoSlide();

        }
    );


    /* =====================================================
       TOUCH
       ===================================================== */

    slider.addEventListener(
        "touchstart",
        function () {

            stopAutoSlide();

        },
        { passive: true }
    );


    /* =====================================================
       VIDEO ENDED
       ===================================================== */

    slides.forEach(slide => {

        const video =
            slide.querySelector(
                ".main-slide-video"
            );


        if (!video) {
            return;
        }


        video.addEventListener(
            "ended",
            function () {

                if (
                    slide ===
                    slides[currentIndex]
                ) {

                    nextSlide();

                }

            }
        );

    });


    /* =====================================================
       INITIALIZE
       ===================================================== */

    updateDots();

    playCurrentVideo();

    startAutoSlide();

});
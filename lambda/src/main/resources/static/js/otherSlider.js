document.addEventListener("DOMContentLoaded", function () {

    /* =========================================================
       ELEMENTS
       ========================================================= */

    const slider =
        document.getElementById("otherSlider");

    const track =
        document.getElementById("otherSliderTrack");

    const nextButton =
        document.getElementById("otherSliderNext");

    const prevButton =
        document.getElementById("otherSliderPrev");


    if (!slider || !track) {

        console.log("OTHER slider not found");

        return;
    }


    /* =========================================================
       ORIGINAL SLIDES
       ========================================================= */

    let originalSlides =
        Array.from(
            track.querySelectorAll(".other-slide")
        );


    if (originalSlides.length === 0) {

        console.log("No OTHER slides found");

        return;
    }


    console.log(
        "OTHER SLIDES:",
        originalSlides.length
    );


    /* =========================================================
       SETTINGS
       ========================================================= */

    const delay = 5000;

    let timer = null;

    let mouseOver = false;

    let isMoving = false;


    /*
     * TRUE when user has unmuted a video.
     *
     * While this is true:
     *
     * - slider cannot move
     * - mouseleave cannot restart slider
     * - video is allowed to finish
     */

    let videoPlayingWithSound = false;

    let activeSoundVideo = null;


    /* =========================================================
       GET VISIBLE SLIDES
       ========================================================= */

    function getVisibleSlides() {

        if (window.innerWidth >= 1024) {

            return 4;

        }

        if (window.innerWidth >= 768) {

            return 2;

        }

        return 1;

    }


    /* =========================================================
       CREATE INFINITE SLIDES
       ========================================================= */

    function createInfiniteSlides() {

        /*
         * Remove previous clones
         */

        track
            .querySelectorAll(
                ".other-slide-clone"
            )
            .forEach(clone => {

                clone.remove();

            });


        const visible =
            getVisibleSlides();


        /*
         * Clone first N slides
         */

        for (
            let i = 0;
            i < visible &&
            i < originalSlides.length;
            i++
        ) {

            const clone =
                originalSlides[i]
                    .cloneNode(true);


            clone.classList.add(
                "other-slide-clone"
            );


            track.appendChild(clone);

        }


        /*
         * Clone last N slides
         */

        for (
            let i = Math.max(
                0,
                originalSlides.length -
                visible
            );

            i < originalSlides.length;

            i++
        ) {

            const clone =
                originalSlides[i]
                    .cloneNode(true);


            clone.classList.add(
                "other-slide-clone"
            );


            track.insertBefore(
                clone,
                track.firstChild
            );

        }


        /*
         * Reconnect video ended events
         * because cloned videos are new elements.
         */

        bindVideoEndedEvents();

    }


    /* =========================================================
       GET ALL SLIDES
       ========================================================= */

    function getAllSlides() {

        return Array.from(
            track.querySelectorAll(
                ".other-slide"
            )
        );

    }


    /* =========================================================
       CURRENT INDEX
       ========================================================= */

    let currentIndex =
        getVisibleSlides();


    /* =========================================================
       SET SLIDE WIDTHS
       ========================================================= */

    function setSlideWidths() {

        const visible =
            getVisibleSlides();


        const viewport =
            slider.querySelector(
                ".other-slider-viewport"
            );


        if (!viewport) {
            return;
        }


        const viewportWidth =
            viewport.getBoundingClientRect()
                .width;


        const slideWidth =
            viewportWidth / visible;


        const allSlides =
            getAllSlides();


        allSlides.forEach(slide => {

            slide.style.flex =
                "0 0 " +
                slideWidth +
                "px";

            slide.style.width =
                slideWidth +
                "px";

        });

    }


    /* =========================================================
       MOVE SLIDER
       ========================================================= */

    function moveSlider(
        animate = true
    ) {

        const allSlides =
            getAllSlides();


        if (!allSlides.length) {
            return;
        }


        const slideWidth =
            allSlides[0]
                .getBoundingClientRect()
                .width;


        const position =
            currentIndex *
            slideWidth;


        track.style.transition =
            animate
                ? "transform 700ms ease-in-out"
                : "none";


        track.style.transform =
            "translate3d(-" +
            position +
            "px, 0, 0)";

    }


    /* =========================================================
       STOP ALL VIDEOS
       ========================================================= */

    function stopVideos() {

        /*
         * IMPORTANT:
         *
         * If a video is currently playing with sound,
         * do NOT stop it.
         */

        if (videoPlayingWithSound &&
            activeSoundVideo) {

            return;
        }


        getAllSlides()
            .forEach(slide => {

                const video =
                    slide.querySelector(
                        ".other-slide-video"
                    );


                if (!video) {
                    return;
                }


                video.pause();

                video.currentTime = 0;

                video.muted = true;

            });

    }


    /* =========================================================
       PLAY VISIBLE VIDEO
       ========================================================= */

    function playVisibleVideo() {

        /*
         * NEVER interrupt an unmuted video.
         */

        if (videoPlayingWithSound &&
            activeSoundVideo) {

            return;
        }


        /*
         * Stop previous muted videos.
         */

        getAllSlides()
            .forEach(slide => {

                const video =
                    slide.querySelector(
                        ".other-slide-video"
                    );


                if (!video) {
                    return;
                }


                /*
                 * Do not touch the active
                 * sound video.
                 */

                if (
                    video ===
                    activeSoundVideo
                ) {

                    return;

                }


                video.pause();

                video.currentTime = 0;

                video.muted = true;

            });


        const visible =
            getVisibleSlides();


        const allSlides =
            getAllSlides();


        /*
         * Find first visible video.
         */

        for (
            let i = currentIndex;

            i < currentIndex + visible &&
            i < allSlides.length;

            i++
        ) {

            const video =
                allSlides[i]
                    .querySelector(
                        ".other-slide-video"
                    );


            if (!video) {
                continue;
            }


            /*
             * Normal automatic playback
             * is always muted.
             */

            video.muted = true;

            video.loop = true;


            video.play()
                .catch(() => {});


            /*
             * Only play first visible video.
             */

            break;

        }

    }


    /* =========================================================
       NEXT SLIDE
       ========================================================= */

    function nextSlide() {

        /*
         * NEVER slide while an unmuted video
         * is playing.
         */

        if (videoPlayingWithSound) {

            console.log(
                "Slider locked - video playing with sound"
            );

            return;
        }


        if (isMoving) {
            return;
        }


        isMoving = true;


        currentIndex++;


        stopVideos();


        moveSlider(true);


        setTimeout(
            function () {

                const visible =
                    getVisibleSlides();


                /*
                 * Reached cloned first slides.
                 */

                if (
                    currentIndex >=
                    originalSlides.length +
                    visible
                ) {

                    currentIndex =
                        visible;


                    moveSlider(false);

                }


                playVisibleVideo();


                isMoving = false;

            },
            750
        );

    }


    /* =========================================================
       PREVIOUS SLIDE
       ========================================================= */

    function previousSlide() {

        /*
         * NEVER slide while unmuted video
         * is playing.
         */

        if (videoPlayingWithSound) {

            console.log(
                "Slider locked - video playing with sound"
            );

            return;
        }


        if (isMoving) {
            return;
        }


        isMoving = true;


        currentIndex--;


        stopVideos();


        moveSlider(true);


        setTimeout(
            function () {

                const visible =
                    getVisibleSlides();


                /*
                 * Reached beginning clones.
                 */

                if (
                    currentIndex <
                    visible
                ) {

                    currentIndex =
                        originalSlides.length +
                        visible -
                        1;


                    moveSlider(false);

                }


                playVisibleVideo();


                isMoving = false;

            },
            750
        );

    }


    /* =========================================================
       START AUTO SLIDE
       ========================================================= */

    function startAutoSlide() {

        /*
         * Do not start slider while
         * unmuted video is playing.
         */

        if (videoPlayingWithSound) {

            console.log(
                "Auto slide blocked - sound video active"
            );

            return;
        }


        stopAutoSlide();


        if (
            originalSlides.length <=
            getVisibleSlides()
        ) {

            return;
        }


        timer =
            setInterval(
                function () {

                    /*
                     * Check both conditions.
                     */

                    if (
                        !mouseOver &&
                        !videoPlayingWithSound
                    ) {

                        nextSlide();

                    }

                },
                delay
            );

    }


    /* =========================================================
       STOP AUTO SLIDE
       ========================================================= */

    function stopAutoSlide() {

        if (timer !== null) {

            clearInterval(timer);

            timer = null;

        }

    }


    /* =========================================================
       MOUSE ENTER
       ========================================================= */

    slider.addEventListener(
        "mouseenter",
        function () {

            mouseOver = true;

            stopAutoSlide();

        }
    );


    /* =========================================================
       MOUSE LEAVE
       ========================================================= */

    slider.addEventListener(
        "mouseleave",
        function () {

            mouseOver = false;


            /*
             * IMPORTANT:
             *
             * If unmuted video is playing,
             * DO NOT restart slider.
             */

            if (
                videoPlayingWithSound
            ) {

                console.log(
                    "Slider remains stopped - sound video still playing"
                );

                return;
            }


            startAutoSlide();

        }
    );


    /* =========================================================
       NEXT BUTTON
       ========================================================= */

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


    /* =========================================================
       PREVIOUS BUTTON
       ========================================================= */

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


    /* =========================================================
       MUTE / UNMUTE
       
       EVENT DELEGATION

       This also works for cloned slides.
       ========================================================= */

/* =========================================================
   TAP / CLICK ANYWHERE ON VIDEO SLIDE
   ========================================================= */

slider.addEventListener(
    "click",
    function (event) {

        /*
         * Ignore slider navigation buttons.
         */
        if (
            event.target.closest(".other-slider-button")
        ) {
            return;
        }


        /*
         * Find the slide that was clicked.
         *
         * This works for both original slides
         * and cloned slides.
         */
        const slide =
            event.target.closest(".other-slide");


        if (!slide) {
            return;
        }


        /*
         * Find video inside clicked slide.
         */
        const video =
            slide.querySelector(
                ".other-slide-video"
            );


        /*
         * Image slide → nothing to mute/unmute.
         */
        if (!video) {
            return;
        }


        /*
         * Find mute button inside this slide.
         * It is optional now, but we update its icon
         * if it exists.
         */
        const button =
            slide.querySelector(
                ".other-video-mute"
            );


        const icon =
            button
                ? button.querySelector("i")
                : null;


        /* =================================================
           MUTED → UNMUTED
           ================================================= */

        if (video.muted) {

            console.log(
                "Tapped video → UNMUTE"
            );


            /*
             * Stop automatic slider.
             */
            stopAutoSlide();


            /*
             * Lock slider while sound video
             * is playing.
             */
            videoPlayingWithSound = true;

            activeSoundVideo = video;


            /*
             * IMPORTANT:
             *
             * Do not loop when sound is enabled.
             * Video must finish first.
             */
            video.loop = false;


            /*
             * Unmute.
             */
            video.muted = false;


            /*
             * Make sure video is playing.
             */
            video.play()
                .catch(error => {

                    console.log(
                        "Video play error:",
                        error
                    );

                });


            /*
             * Change icon.
             */
            if (icon) {

                icon.classList.remove(
                    "fa-volume-xmark"
                );

                icon.classList.add(
                    "fa-volume-high"
                );

            }

        }


        /* =================================================
           UNMUTED → MUTED
           ================================================= */

        else {

            console.log(
                "Tapped video → MUTE"
            );


            /*
             * Mute video.
             */
            video.muted = true;


            /*
             * Keep video playing.
             */
            video.play()
                .catch(() => {});


            /*
             * IMPORTANT:
             *
             * Keep slider locked until this video
             * finishes, exactly as your current logic.
             */
            videoPlayingWithSound = true;

            activeSoundVideo = video;

            stopAutoSlide();


            /*
             * Change icon.
             */
            if (icon) {

                icon.classList.remove(
                    "fa-volume-high"
                );

                icon.classList.add(
                    "fa-volume-xmark"
                );

            }

        }

    }
);

    /* =========================================================
       VIDEO ENDED EVENT
       
       This is the key part.
       
       Slider resumes ONLY after the video
       completely finishes.
       ========================================================= */

    function bindVideoEndedEvents() {

        const videos =
            track.querySelectorAll(
                ".other-slide-video"
            );


        videos.forEach(video => {

            /*
             * Using onended instead of addEventListener
             * prevents duplicate handlers after resize.
             */

            video.onended =
                function () {

                    /*
                     * Only handle the video that
                     * currently owns the slider.
                     */

                    if (
                        activeSoundVideo !==
                        video
                    ) {

                        return;
                    }


                    console.log(
                        "Unmuted video finished"
                    );


                    /*
                     * Clear active video.
                     */

                    activeSoundVideo = null;

                    videoPlayingWithSound = false;


                    /*
                     * Reset video.
                     */

                    video.currentTime = 0;


                    /*
                     * Mute it again.
                     */

                    video.muted = true;


                    /*
                     * Restore normal muted loop.
                     */

                    video.loop = true;


                    /*
                     * Find mute button.
                     */

                    const frame =
                        video.closest(
                            ".other-media-frame"
                        );


                    if (frame) {

                        const button =
                            frame.querySelector(
                                ".other-video-mute"
                            );


                        if (button) {

                            const icon =
                                button.querySelector(
                                    "i"
                                );


                            if (icon) {

                                icon.classList.remove(
                                    "fa-volume-high"
                                );

                                icon.classList.add(
                                    "fa-volume-xmark"
                                );

                            }

                        }

                    }


                    /*
                     * Resume slider.
                     *
                     * Even if mouse is currently
                     * outside the slider.
                     */

                    if (!mouseOver) {

                        startAutoSlide();

                    } else {

                        /*
                         * Mouse is still over slider.
                         * Keep it stopped until mouse leaves.
                         */

                        stopAutoSlide();

                    }


                    /*
                     * Continue muted video preview.
                     */

                    video.play()
                        .catch(() => {});

                };

        });

    }


    /* =========================================================
       RESIZE
       ========================================================= */

    let resizeTimer = null;


    window.addEventListener(
        "resize",
        function () {

            clearTimeout(
                resizeTimer
            );


            resizeTimer =
                setTimeout(
                    function () {

                        /*
                         * Do not destroy an unmuted
                         * video while it is playing.
                         */

                        if (
                            videoPlayingWithSound
                        ) {

                            console.log(
                                "Resize ignored while sound video is playing"
                            );

                            return;
                        }


                        stopAutoSlide();

                        stopVideos();


                        /*
                         * Rebuild clones.
                         */

                        createInfiniteSlides();


                        /*
                         * Reset index.
                         */

                        currentIndex =
                            getVisibleSlides();


                        setSlideWidths();

                        moveSlider(false);

                        playVisibleVideo();

                        startAutoSlide();

                    },
                    250
                );

        }
    );


    /* =========================================================
       INITIALIZE
       ========================================================= */

    createInfiniteSlides();

    setSlideWidths();

    moveSlider(false);

    playVisibleVideo();

    startAutoSlide();


});
/* ============================================================
   Part 3A - Initialization + Play/Pause + Auto Hide Controls
   ============================================================ */

const PLAY_ICON = "play-icon fa-solid fa-circle-play";
const PAUSE_ICON = "play-icon fa-solid fa-circle-pause";

document.addEventListener("DOMContentLoaded", () => {

    document.querySelectorAll(".video-card").forEach(initVideoPlayer);

});

function initVideoPlayer(card) {

    const video = card.querySelector(".preview-video");

    const overlay = card.querySelector(".video-overlay");

    const playBtn = card.querySelector(".play-btn");

    const centerIcon = card.querySelector(".play-icon");

    const playToggle = card.querySelector(".play-toggle");

    const playToggleIcon = card.querySelector(".play-toggle-icon");

    const controls = card.querySelector(".video-controls");
	
	/* ===============================
	   Progress & Time Elements
	=============================== */

	const progressBar = card.querySelector(".progress-bar");

	const currentTimeEl = card.querySelector(".current-time");

	const durationEl = card.querySelector(".duration");
	
	/* ===============================
	   Controls
	=============================== */

	const muteBtn = card.querySelector(".mute-btn");
	const muteIcon = card.querySelector(".mute-icon");

	const volumeSlider = card.querySelector(".volume-slider");

	const fullscreenBtn = card.querySelector(".fullscreen-btn");

	let isSeeking = false;
	
	

    let hideTimer = null;

    // manual = true means user clicked play button
    let manualPlay = false;

	
	/* ===============================
	   Format Time
	=============================== */

function formatTime(seconds){

    if(isNaN(seconds) || !isFinite(seconds)){
        return "00:00";
    }


    const minutes =
        Math.floor(seconds / 60);

    const secs =
        Math.floor(seconds % 60);


    return `${String(minutes).padStart(2,"0")}:${String(secs).padStart(2,"0")}`;

}
	
	
	
    /* ===============================
       Show Controls
    =============================== */

    function showControls() {

        controls.classList.remove("hide");

        playBtn.classList.remove("hide");

        clearTimeout(hideTimer);

        if (!video.paused) {

            hideTimer = setTimeout(() => {

                controls.classList.add("hide");

                playBtn.classList.add("hide");

            }, 2000);

        }

    }

    /* ===============================
       Hide Controls
    =============================== */

    function hideControls() {

        if (!video.paused) {

            controls.classList.add("hide");

            playBtn.classList.add("hide");

        }

    }

    /* ===============================
       Play
    =============================== */

	function playVideo(fromStart = false) {

	    if (fromStart) {
	        video.currentTime = 0;
	    }

	    video.play();

	    centerIcon.className = PAUSE_ICON;
	    playToggleIcon.className = "fa-solid fa-pause";

	    showControls();
	}

    /* ===============================
       Pause
    =============================== */

    function pauseVideo() {

        video.pause();

        centerIcon.className = PLAY_ICON;

        playToggleIcon.className = "fa-solid fa-play";

        controls.classList.remove("hide");

        playBtn.classList.remove("hide");

        clearTimeout(hideTimer);

    }

    /* ===============================
       Toggle Play
    =============================== */

	function toggleVideo() {

	    if (video.paused) {

	        manualPlay = true;
	        playVideo(false);

	    } else {

	        manualPlay = false;
	        pauseVideo();

	    }

	}

    /* ===============================
       Click Anywhere
    =============================== */

    overlay.addEventListener("click", toggleVideo);

    playBtn.addEventListener("click", e => {

        e.stopPropagation();

        toggleVideo();

    });

    playToggle.addEventListener("click", e => {

        e.stopPropagation();

        toggleVideo();

    });

    /* ===============================
       Hover Preview
    =============================== */

	card.addEventListener("mouseenter", () => {

	    if (!manualPlay) {
	        playVideo(true);
	    }

	});

    card.addEventListener("mouseleave", () => {

        if (!manualPlay) {

            pauseVideo();

        }

    });

    /* ===============================
       Mouse Move
    =============================== */

    card.addEventListener("mousemove", () => {

        showControls();

    });

    /* ===============================
       Video Events
    =============================== */

    video.addEventListener("play", () => {

        centerIcon.className = PAUSE_ICON;

        playToggleIcon.className = "fa-solid fa-pause";

        showControls();

    });
	
	/* ===============================
	   Video Metadata
	   /* ============================================================
	      Video Progress + Duration + Seek
	   ============================================================ */


	   progressBar.min = 0;
	   progressBar.max = 100;
	   progressBar.value = 0;



	   function formatTime(seconds){

	       if(!seconds || !isFinite(seconds)){
	           return "00:00";
	       }


	       let minutes = Math.floor(seconds / 60);

	       let secondsPart = Math.floor(seconds % 60);


	       return (
	           String(minutes).padStart(2,"0")
	           +
	           ":"
	           +
	           String(secondsPart).padStart(2,"0")
	       );

	   }




	   /*
	    Load Total Duration
	   */

	   function updateDuration(){


	       if(
	           video.readyState >= 1 &&
	           isFinite(video.duration)
	       ){


	           durationEl.innerHTML =
	           formatTime(video.duration);


	       }

	   }



	   ["loadedmetadata", "loadeddata", "canplay", "durationchange"].forEach(event => {
	       video.addEventListener(event, () => {
	           console.log(event, video.duration);

	           if (isFinite(video.duration) && video.duration > 0) {
	               durationEl.textContent = formatTime(video.duration);
	           }
	       });
	   });



	   video.addEventListener(
	   "durationchange",
	   updateDuration
	   );



	   video.addEventListener(
	   "canloaded",
	   updateDuration
	   );




	   /*
	    Update Running Time
	   */


	   video.addEventListener(
	   "timeupdate",
	   ()=>{


	       if(
	           video.duration &&
	           isFinite(video.duration) &&
	           !isSeeking
	       ){


	           let progress =
	           (video.currentTime / video.duration) * 100;



	           progressBar.value = progress;



	           currentTimeEl.innerHTML =
	           formatTime(video.currentTime);


	       }


	   });





	   /*
	    Start Drag
	   */


	   progressBar.addEventListener(
	   "mousedown",
	   ()=>{

	       isSeeking = true;

	   });



	   progressBar.addEventListener(
	   "touchstart",
	   ()=>{

	       isSeeking = true;

	   });





	   /*
	    While Moving
	   */


	   progressBar.addEventListener(
	   "input",
	   ()=>{


	       if(
	           video.duration &&
	           isFinite(video.duration)
	       ){


	           let preview =
	           (progressBar.value / 100)
	           *
	           video.duration;



	           currentTimeEl.innerHTML =
	           formatTime(preview);


	       }


	   });






	   /*
	    Finish Seek
	   */


	   function finishSeek(){


	       if(
	           video.duration &&
	           isFinite(video.duration)
	       ){


	           video.currentTime =
	           (progressBar.value / 100)
	           *
	           video.duration;


	       }


	       isSeeking=false;


	   }




	   progressBar.addEventListener(
	   "mouseup",
	   finishSeek
	   );



	   progressBar.addEventListener(
	   "touchend",
	   finishSeek
	   );






	   /*
	    Video Complete
	   */


	   video.addEventListener(
	   "ended",
	   ()=>{


	       progressBar.value=100;


	       currentTimeEl.innerHTML =
	       formatTime(video.duration);


	   });

/* ===============================
   Volume
=============================== */

volumeSlider.value = video.volume;

volumeSlider.addEventListener("input", () => {

    video.volume = volumeSlider.value;

    if (video.volume == 0) {

        video.muted = true;

    } else {

        video.muted = false;

    }

});

/* ===============================
   Mute
=============================== */

muteBtn.addEventListener("click", () => {

    video.muted = !video.muted;

    updateMuteIcon();

});

/* ===============================
   Update Mute Icon
=============================== */

function updateMuteIcon() {

    if (video.muted || video.volume == 0) {

        muteIcon.className = "mute-icon fa-solid fa-volume-xmark";

    } else {

        muteIcon.className = "mute-icon fa-solid fa-volume-high";

    }

}

/* ===============================
   Fullscreen
=============================== */

fullscreenBtn.addEventListener("click", () => {

    if (!document.fullscreenElement) {

        card.requestFullscreen();

    } else {

        document.exitFullscreen();

    }

});

/* ===============================
   Double Click
=============================== */

video.addEventListener("dblclick", () => {

    if (!document.fullscreenElement) {

        card.requestFullscreen();

    } else {

        document.exitFullscreen();

    }

});

/* ===============================
   Loading
=============================== */

video.addEventListener("waiting", () => {

    card.classList.add("loading");

});

video.addEventListener("playing", () => {

    card.classList.remove("loading");

});

/* ===============================
   Keyboard
=============================== */

card.setAttribute("tabindex", "0");

card.addEventListener("keydown", e => {

    switch (e.code) {

        case "Space":

            e.preventDefault();

            toggleVideo();

            break;

        case "ArrowRight":

            video.currentTime += 5;

            break;

        case "ArrowLeft":

            video.currentTime -= 5;

            break;

        case "KeyM":

            video.muted = !video.muted;

            updateMuteIcon();

            break;

        case "KeyF":

            if (!document.fullscreenElement) {

                card.requestFullscreen();

            } else {

                document.exitFullscreen();

            }

            break;

    }

});

/* ===============================
   Volume Change
=============================== */

video.addEventListener("volumechange", () => {

    volumeSlider.value = video.muted ? 0 : video.volume;

    updateMuteIcon();

});

	
    video.addEventListener("pause", () => {

        centerIcon.className = PLAY_ICON;

        playToggleIcon.className = "fa-solid fa-play";

        controls.classList.remove("hide");

        playBtn.classList.remove("hide");

    });

    video.addEventListener("ended", () => {

        manualPlay = false;

        centerIcon.className = PLAY_ICON;

        playToggleIcon.className = "fa-solid fa-play";

        controls.classList.remove("hide");

        playBtn.classList.remove("hide");

    });

// End Video
progressBar.value = 0;

currentTimeEl.textContent = "00:00";

video.load();

const wait = setInterval(() => {

    if (video.readyState >= 1 && isFinite(video.duration) && video.duration > 0) {

        durationEl.textContent = formatTime(video.duration);

        clearInterval(wait);

    }

}, 100);

updateMuteIcon();
}
	



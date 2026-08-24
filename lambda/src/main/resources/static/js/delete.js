const baseUrl = "http://localhost:8081";

function viewContact(btn) {

    const id = btn.dataset.id;
    const name = btn.dataset.name;
    const email = btn.dataset.email;
    const phone = btn.dataset.phone;
    const image = btn.dataset.image;

    // ✅ Correct dataset names (must match HTML)
    const linkedInLink = btn.dataset.linkedin;
    const websiteLink = btn.dataset.website;
    const favorite = btn.dataset.favorite;

    // ✅ Fill modal

    document.getElementById("vImage").src = image;
    document.getElementById("vName").innerText = name;
    document.getElementById("vEmail").innerText = email;
    document.getElementById("vPhone").innerText = phone;

    // ✅ Fix links
    const linkedinEl = document.getElementById("vLinkedInLink");

    linkedinEl.innerText = linkedInLink;

    const websiteEl = document.getElementById("vWebsiteLink");

    websiteEl.innerText = websiteLink;

    // ✅ Fix favorite (string → boolean)
    const favEl = document.getElementById("vFavorite");

    if (favorite === "true") {
        favEl.innerHTML = "⭐ ⭐ ⭐ ⭐ ⭐";
    } else {
        favEl.innerHTML = "";
    }

    // ✅ Show modal
    document.getElementById("viewContactModal").classList.remove("hidden");
}

// Close modal
function closeViewModal() {
    document.getElementById("viewContactModal").classList.add("hidden");
}

async function deleteContact(id){
	Swal.fire({
	icon:"warning",	
	  title: "Do you want to delete this contact?",
	  showCancelButton: true,
	  confirmButtonText: "Delete",
	}).then((result) => {
	  /* Read more about isConfirmed, isDenied below */
	  if (result.isConfirmed){
		const url=`${baseUrl}/user/contacts/delete/`+id;
		window.location.replace(url);
	  }
		 
	});
	
}

async function deleteEmployee(id){
	Swal.fire({
	icon:"warning",	
	  title: "Do you want to delete this Employee?",
	  showCancelButton: true,
	  confirmButtonText: "Delete",
	}).then((result) => {
	  /* Read more about isConfirmed, isDenied below */
	  if (result.isConfirmed){
		const url=`${baseUrl}/employee/delete/`+id;
		window.location.replace(url);
	  }
		 
	});
	
}
async function deleteOpening(id){
	Swal.fire({
	icon:"warning",	
	  title: "Do you want to delete this Opening?",
	  showCancelButton: true,
	  confirmButtonText: "Delete",
	}).then((result) => {
	  /* Read more about isConfirmed, isDenied below */
	  if (result.isConfirmed){
		const url=`${baseUrl}/hr/opening/delete/`+id;
		window.location.replace(url);
	  }
		 
	});
	}
async function deleteSlide(id){
	Swal.fire({
	icon:"warning",	
	  title: "Do you want to delete this Slide?",
	  showCancelButton: true,
	  confirmButtonText: "Delete",
	}).then((result) => {
	  /* Read more about isConfirmed, isDenied below */
	  if (result.isConfirmed){
		const url=`${baseUrl}/editor/slider/delete/`+id;
		window.location.replace(url);
	  }
		 
	});
	}
	
async function deleteComplain(id){
	Swal.fire({
	icon:"warning",	
	  title: "Do you want to delete this complain Form Link?",
	  showCancelButton: true,
	  confirmButtonText: "Delete",
	}).then((result) => {
	  /* Read more about isConfirmed, isDenied below */
	  if (result.isConfirmed){
		const url=`${baseUrl}/hr/complainLinks/delete/`+id;
		window.location.replace(url);
	  }
		 
	});
	
}

async function deleteVideos(id){
	Swal.fire({
	icon:"warning",	
	  title: "Do you want to delete Video Links?",
	  showCancelButton: true,
	  confirmButtonText: "Delete",
	}).then((result) => {
	  /* Read more about isConfirmed, isDenied below */
	  if (result.isConfirmed){
		const url=`${baseUrl}/editor/deleteVideoOneByOne/`+id;
		window.location.replace(url);
	  }
		 
	});
	
}

// contact delete function

const baseUrl="http://localhost:8081"
async function deleteContact(id){
	Swal.fire({
	icon:"warning",	
	  title: "Do you want to delete the contact?",
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

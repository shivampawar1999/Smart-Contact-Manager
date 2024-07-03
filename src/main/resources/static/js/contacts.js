console.log("contacts js");

const baseUrl = "http://localhost:9090";

// set the modal menu element
const viewContactModal = document.getElementById('view_contact_modal');

// options with default values
const options = {
	placement: 'bottom-right',
	backdrop: 'dynamic',
	backdropClasses:
		'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
	closable: true,
	onHide: () => {
		console.log('modal is hidden');
	},
	onShow: () => {
		console.log('modal is shown');
	},
	onToggle: () => {
		console.log('modal has been toggled');
	},
};

// instance options object
const instanceOptions = {
	id: 'view_contact_modal',
	override: true
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModel() {
	contactModal.show();
}

function closeContactModel() {
	contactModal.hide();
}

//function call load data
async function loadContactdata(id) {

	console.log(id);

	try {

		const data = await (await fetch(`${baseUrl}/api/contacts/${id}`)).json();
		console.log(data);

		document.querySelector("#contact_name").innerHTML = data.name;
		document.querySelector("#contact_email").innerHTML = data.email;
		document.querySelector("#websiteLink").innerHTML = data.websiteLink;
		document.querySelector("#phoneNumber").innerHTML = data.phoneNumber;
		document.querySelector("#linkdInLink").innerHTML = data.linkdInLink;
		document.querySelector("#favorite").innerHTML = data.favorite;
		document.querySelector("#description").innerHTML = data.description;
		document.querySelector("#address").innerHTML = data.address;

		// Set profile picture
		const profilePicture = document.querySelector("#profile_picture");
		profilePicture.src = data.picture;

		openContactModel()

	} catch (error) {
		console.log("error", error);
	}

}

//delete alert
async function deleteContact(id) {
	
	console.log("delete id -> "+ id);

	Swal.fire({
		title: "Do you want to Delete the Contact?",
		icon: "warning",
		showCancelButton: true,
		confirmButtonText: "Delete",
		
	}).then((result) => {
		/* Read more about isConfirmed, isDenied below */
		if (result.isConfirmed) {
			Swal.fire("Delete!", "", "success");
			const url = `${baseUrl}/user/contacts/delete/`+ id;
			window.location.replace(url);
		} else if (result.isDenied) {
			Swal.fire("Changes are not saved", "", "info");
		}
	});
}
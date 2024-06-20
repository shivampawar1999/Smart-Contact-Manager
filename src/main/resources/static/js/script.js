console.log("script loded");

let currentTheme = getTheme();

document.addEventListener("DOMContentLoaded", () =>{
	changeTheme();
});


function changeTheme(){
	
	document.querySelector("html").classList.add(currentTheme);
	const changeThemeButton = document.querySelector("#theme_change_button");
	
	changeThemeButton.querySelector("span").textContent = currentTheme == "light" ? "Dark" : "Light";
	changeThemeButton.addEventListener("click", (event) => {
		console.log("change theme button clicked");
		
		const oldTheme = currentTheme;
		
		if(currentTheme === "dark"){
			currentTheme = "light";
		}else{
			currentTheme = "dark";
		}
		
		setTheme(currentTheme);
		document.querySelector("html").classList.remove(oldTheme);
		document.querySelector("html").classList.add(currentTheme);
		
	})
};

//set theme to local storage
function setTheme(theme){
	localStorage.setItem("theme", theme);
}

//get theme from local storage
function getTheme(){
	let theme = localStorage.getItem("theme");
	return theme ? theme : "light";
}







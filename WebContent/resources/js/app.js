
			const alert = document.getElementById("alert");
			displayAlert();
			function displayAlert() {
				  //alert.textContent = "${STATUS}";
				  //alert.classList.add(`alert-${action}`);
				  // remove alert
				  setTimeout(function () {
				    alert.textContent = "";
				  }, 3000);
				}
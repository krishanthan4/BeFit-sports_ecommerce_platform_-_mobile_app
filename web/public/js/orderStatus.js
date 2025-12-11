function changeStatus(invoice_id) {
  const status_select = document.getElementById("status_select").value;

  const request = new XMLHttpRequest();
  const form = new FormData();
  form.append("status_select", status_select);
  form.append("invoice_id", invoice_id);

  request.onreadystatechange = () => {
    if (request.readyState == 4 && request.status == 200) {
      if (request.responseText == "success") {
        document
          .getElementById("msgToast")
          .classList.remove("hidden", "border-red-500/90");
        document
          .getElementById("msgToast")
          .classList.add("border-green-500/90");
        document.getElementById("msgIcon").classList.remove("bg-red-500/90");
        document.getElementById("msgIcon").classList.add("bg-green-500/90");
        document.getElementById("msg").innerText = "status_changed";
        setTimeout(() => {
            document
            .getElementById("msgToast")
            .classList.add("hidden");
        }, 1000);
      }
    }
  };
  request.open("POST", "./processes/orderStatusProcess.php", true);
  request.send(form);
}

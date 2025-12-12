async function changeStatus(invoice_id) {
    const status_select = document.getElementById("status_select").value;

    try {
        const response = await OrderService.updateOrderStatus(invoice_id, status_select);
        if (response.success) {
            document
                .getElementById("msgToast")
                .classList.remove("hidden", "border-red-500/90");
            document
                .getElementById("msgToast")
                .classList.add("border-green-500/90");
            document.getElementById("msgIcon").classList.remove("bg-red-500/90");
            document.getElementById("msgIcon").classList.add("bg-green-500/90");
            document.getElementById("msg").innerText = "Status changed successfully";
            setTimeout(() => {
                document.getElementById("msgToast").classList.add("hidden");
            }, 1000);
        }
    } catch (error) {
        document.getElementById("msg").innerText = error.message;
        setTimeout(() => {
            document.getElementById("msgToast").classList.add("hidden");
        }, 1000);
    }
}

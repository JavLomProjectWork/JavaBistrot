const pingButton = document.getElementById("ping-btn");
const statusEl = document.getElementById("status");

if (pingButton && statusEl) {
  pingButton.addEventListener("click", () => {
    statusEl.textContent = "JS attivo: click ricevuto.";
  });
}

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("recipeForm");

  form.addEventListener("submit", (event) => {
    const ingredientEntries = document.querySelectorAll("#ingredientList .ingredient-entry");

    for (let entry of ingredientEntries) {
      const wholeNumberInput = entry.querySelector("input[name$='.wholeNumberQuantity']");
      const fractionSelect = entry.querySelector("select[name$='.fractionQuantity']");

      const wholeNumber = parseInt(wholeNumberInput?.value || "0", 10);
      const fraction = fractionSelect?.value.trim();

      if (wholeNumber === 0 && fraction === "0") {
        event.preventDefault(); // Prevent form submission
        alert(
          `Invalid ingredient quantity: Both the whole number and fraction are 0. Please specify a valid quantity for your ingredient.`
        );
        return;
      }
    }
  });
});

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("recipeForm");

  form.addEventListener("submit", (event) => {
    const ingredientInputs = document.querySelectorAll(
      "#ingredientList .ingredient-entry input[name$='.component']"
    );
    const ingredientNames = [];

    for (let input of ingredientInputs) {
      const value = input.value.trim().toLowerCase();
      if (value) {
        if (ingredientNames.includes(value)) {
          event.preventDefault(); // Prevent form submission
          alert(`Duplicate ingredient detected: "${input.value}". Please ensure all ingredients are unique.`);
          return;
        }
        ingredientNames.push(value);
      }
    }
  });
});

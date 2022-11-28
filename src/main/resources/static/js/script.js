const showDurationInYearsToSelect = () => {
    document.getElementById("selectDuration").innerText = "Select subscription duration:";
    const radioButtonsWrapElem = document.getElementsByClassName("form-check");

    for (let radioButtonsWrapElemKey of radioButtonsWrapElem) {
             radioButtonsWrapElemKey.removeAttribute("hidden");
    }

    document.getElementById("confirmButton").removeAttribute("hidden");
}

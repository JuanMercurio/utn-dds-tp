const tabs = document.querySelectorAll(".hc-card-tab")

const htmlToElement = (html) => {
    var template = document.createElement('template');
    html = html.trim(); // Never return a text node of whitespace as the result
    template.innerHTML = html;
    return template.content.firstChild;
}

const altaParadasControl = (value) => `<div class="alta-paradas-control">
<div class="form-group alta-numero-parada">
    <label class="alta-form-group-name" for="numeroParada">
        #
    </label>
    <input type="number" class="form-control" id="numeroParada" value="${value}" readonly>
</div>
<div class="form-group">
    <label class="alta-form-group-name">
        Nombre del Sector
    </label>
    <input type="text" class="form-control" id="nombreParada">
</div>
<div class="form-group">
    </div>
${eliminarParadaButton}
${agregarParadaButton}
</div>`

const agregarParadaButton = `<button class="agregar-parada-button" id="agregarParadaButton" type="button">
<i class="fa-solid fa-plus"></i>
</button>`

const eliminarParadaButton = `<button class="agregar-parada-button" id="eliminarParadaButton" type="button">
<i class="fa-solid fa-minus"></i>
</button>`

const onClickEliminarParada = (e) => {
    e.target.closest(".alta-paradas-control").remove()
    const controls = document.querySelectorAll(".alta-paradas-control")
    let lastControl = controls[controls.length - 1]
    let addButton = htmlToElement(agregarParadaButton)
    addButton.addEventListener("click", onClickAgregarParada)
    
    if(controls.length>1){
        let deleteButton = htmlToElement(eliminarParadaButton)
        deleteButton.addEventListener("click", onClickEliminarParada)
        lastControl.append(deleteButton, addButton)
    } else {
        lastControl.append(addButton)
    }

}

const onClickAgregarParada = (e) => {
    const controls = document.querySelectorAll(".alta-paradas-control")
    let newDiv = htmlToElement(altaParadasControl(controls.length+1))
    document.querySelector(".alta-paradas-table").append(newDiv)
    newDiv.querySelector("#agregarParadaButton").addEventListener("click", onClickAgregarParada)
    newDiv.querySelector("#eliminarParadaButton").addEventListener("click", onClickEliminarParada)
    e.target.id === "agregarParadaButton" ? e.target.remove() : e.target.closest("#agregarParadaButton").remove()
    controls[controls.length - 1]?.querySelector("#eliminarParadaButton")?.remove()
}

tabs.forEach( (tab) => {
    tab.addEventListener('click', () => {
        tabs.forEach( (tab) => {
            tab.classList.remove("selected")
            document.querySelector(tab.dataset.target).classList.add("d-none")
        })
        tab.classList.add("selected")
        document.querySelector(tab.dataset.target).classList.remove("d-none")
    })
})

document.querySelector("#agregarParadaButton").addEventListener("click", onClickAgregarParada)
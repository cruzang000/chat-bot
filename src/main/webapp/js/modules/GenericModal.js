
export class GenericModal {
    constructor(modalType, templateFile) {
        this.modalType = modalType;
        this.templateFile = templateFile;
    }

    /**
     * loads in generic modal from dom
     */
    generateModalTemplate = () => document.querySelector('#modal-template').content.cloneNode(true).querySelector(".modal-content");

    buildModalBody = () => {
        return $('<div>').load(this.templateFile, html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, "text/html");

            return doc.querySelector("div").outerHTML;
        })
    }

    showModal = () => {
        const template = this.generateModalTemplate();

        template.querySelector(".modal-title").textContent = this.modalType;
        template.querySelector(".modal-body").appendChild(this.buildModalBody()[0]);

        const elementToAppend = document.querySelector(".modal-dialog");
        elementToAppend.replaceChild(template, elementToAppend.querySelector(".modal-content"));
    }
}

export const CreateGenericModal = button => {
    if (button) {
        const modalType = button.value;
        const templateFile = $(button).data('template');

        const modal = new GenericModal(modalType, templateFile);

        modal.showModal();
    }
}


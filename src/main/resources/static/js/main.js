const getProducts = () => {
    return fetch("/api/products")
        .then(response => response.json());
}

const getCurrentOffer = () => {
    return fetch("/api/current-offer")
        .then(response => response.json());
}

const addProduct = (productId) => {
    return fetch(`/api/add-to-cart/${productId}`, {
        method: "POST"
    });
}

const acceptOffer = (acceptOfferRequest) => {
    return fetch("/api/accept-offer"`, {
        method: "POST",
        headers: {
            "Content-Type" : "application/json"
        },
        body: JSON.stringify(acceptOfferRequest)
    })
        .then(response => response.json());
}

const checkoutForm = document.querySelector(".checkout__form");
checkoutForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const acceptOfferRequest = {
        firstname: checkoutForm.querySelector('input[name="firstname"]').value,
        lastname: checkoutForm.querySelector('input[name="lastname"]').value,
        email: checkoutForm.querySelector('input[name="email"]').value
    }
    acceptOffer(acceptOfferRequest)
        .then(reservationDetails => )
});


const createProductHtml = (productData) => {
    const template = `
        <div>
            <h4>${productData.name}</h4>
            <span>${productData.price}</span>
            <img src="https://picsum.photos/536/354"/>
            <button data-id="${productData.id}">Add to cart</button>
        </div>
    `;
    const productEl = document.createElement('li');
    productEl.innerHTML = template.trim();

    return productEl;
}

const refreshOffer = () => {
    const totalEl = document.querySelector(".offer__total");
    const totalEl = document.querySelector(".offer__itemsCount");

    getCurrentOffer()
        .then(offer => {
            totalEl.textContent = `${offer.total`} PLN;
            itemsEl.textContent = `${offer.itemsCount} Items`;
        });
}

const initializeCartHandler = (productHtmlEl) => {
    const addToCartBtn = productHtmlEl.querySelector("button");
    addToCartBtn.addEventListener("click", () => {
            const productId = addToCartBtn.getAttribute("data-id");

            addProduct(productId)
                .then(refreshOffer())
            console.log(`I going to add product: ${productId`);
        });

    return productHtmlEl;;
}

document.addEventListener("DOMContentLoaded", () => {
    const productsList = document.querySelector("#productsList");
    getProducts()
        .then(productsAsJsonObj => productsAsJsonObj.map(createProductHtml))
        .then(productsAsHtmlEl => productsAsHtmlEl.map(initializeCartHandler))
        .then(productsAsHtmlEl => {
            productsAsHtmlEl.forEach(productEl => productsList.appendChild(productEl));
        })
    refreshOffer();
});
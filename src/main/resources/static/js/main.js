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
    return fetch("/api/accept-offer", {
        method: "POST",
        headers: {
            "Content-Type" : "application/json"
        },
        body: JSON.stringify(acceptOfferRequest)
    }).then(response => response.json());
}

const refreshOffer = (offer) => {
    const offerTotalEl = document.querySelector(".offer__total");
    const offerItemsCountEl = document.querySelector(".offer__itemsCount");

    offerTotalEl.textContent = offer.total;
    offerItemsCountEl.textContent = offer.itemsCount;
}

const createProductHtml = (productData) => {
    let randId = Math.floor(Math.random() * 59);
    const template = `
        <div class="product">
            <div class="product__left">
                <img src="https://picsum.photos/id/${randId}/200/300" alt="">
            </div>
            <div class="product__right">
                <div class="product__name">${productData.name}</div>
                <div class="product__description">${productData.description}</div>
                <div class="product__price">Cena: ${productData.price} PLN</div>
                <div class="product__button">
                    <button data-id="${productData.id}">Add</button>
                </div>
            </div>
        </div>
    `

    const productEl = document.createElement('div');
    productEl.className = "product";
    productEl.innerHTML = template.trim();

    return productEl;
}

const refreshCurrentOffer = () => {
    const totalEl = document.querySelector(".offer__total");
    const itemsCountEl = document.querySelector(".offer__itemsCount");

    getCurrentOffer()
        .then(offer => {
            totalEl.textContent = `${offer.total} PLN`;
            itemsCountEl.textContent = `${offer.itemsCount} Items`;
        })
}

const initializeCartHandler = (productHtmlEl) => {
    const addToCartBtn = productHtmlEl.querySelector("button[data-id]");
    addToCartBtn.addEventListener("click", (event) => {
        const productId = event.target.getAttribute("data-id");

        addProduct(productId)
            .then(() => refreshCurrentOffer());
    });

    return productHtmlEl;
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
        .then(reservationDetails => window.location.href = reservationDetails.paymentUrl);
});

document.addEventListener("DOMContentLoaded", () => {
    const productsList = document.querySelector(".catalog");
    getProducts()
        .then(productsAsJsonObj => productsAsJsonObj.map(createProductHtml))
        .then(productsAsHtmlEl => productsAsHtmlEl.map(initializeCartHandler))
        .then(productsAsHtmlEl => {
            productsAsHtmlEl.forEach(productEl => productsList.appendChild(productEl));
        });

    getCurrentOffer()
            .then(offer => refreshOffer(offer));
});
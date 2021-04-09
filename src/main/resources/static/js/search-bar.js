const offerList = document.getElementById('offerList#1');
const searchBar = document.getElementById('currency');
const allOffers = [];

fetch("http://homerestate.herokuapp.com/currency/api")
    .then(response => response.json())
    .then(data => {
        for (let d of data) {
            allOffers.push(d);
        }
    });

searchBar.addEventListener('change', (e) => {
    const currency = searchBar.value;
    console.log(allOffers.length);
    console.log(currency);
    if (currency==1) {
        displayOffers(allOffers);
    }else {
        if (currency==1.1926) {
            dollarOffers(allOffers);
        } else {
            poundOffers(allOffers);
        }
    }
})

const displayOffers = (offers) => {
    offerList.innerHTML = offers
        .map((a) => {
            return `<div class="col-sm-6 col-lg-4">
                    <a href="/comments/search/${a.id}" class="card ts-item ts-card ts-item__lg">
                        <img class="card-img-top  ts-item__image ts-item-tpp"
                             src="${a.imgUrl}" data-holder-rendered="true"
                             style="height: 225px; width: 100%; display: block;" alt="">
                        <div class="card-body">
                            <div value="${a.price}"
                                 class="ts-item__info-badge">
                                € ${a.price}
                            </div>
                            <figure class="ts-item__info" style="background: linear-gradient(to bottom, rgba(0, 0, 0, 0) 0%, rgba(0, 0, 0, 1) 100%)">
                                <h4>${a.category} for ${a.type}</h4>
                                <aside>
                                    <i class="fa fa-map-marker mr-2">
                                    <i style="font-style: normal; font-weight: normal">${a.city}, ${a.address}</i></i>
                               </aside>
                            </figure>
                            <div class="ts-description-lists">
                                <dl>
                                    <dt>Area</dt>
                                    <dd
                                    >${a.area} m²</dd>
                                </dl>
                                <dl>
                                    <dt>Price per sq.m</dt>
                                    <dd>${a.pricePerSqM} €/m²</dd>                                 
                                </dl>
                            </div>
                        </div>
                        <div class="card-footer"><span class="ts-btn-arrow">Details</span></div>
                    </a>
                </div>
            </div>`
        })
        .join('');
}

const dollarOffers = (offers) => {
    offerList.innerHTML = offers
        .map((a) => {
            return `<div class="col-sm-6 col-lg-4">
                    <a href="/comments/search/${a.id}" class="card ts-item ts-card ts-item__lg">
                        <img class="card-img-top  ts-item__image ts-item-tpp"
                             src="${a.imgUrl}" data-holder-rendered="true"
                             style="height: 225px; width: 100%; display: block;" alt="">
                        <div class="card-body">
                            <div value="${a.price}"
                                 class="ts-item__info-badge" >
                                $ ${(a.price*1.1926).toFixed(2)}
                            </div>
                            <figure class="ts-item__info" style="background: linear-gradient(to bottom, rgba(0, 0, 0, 0) 0%, rgba(0, 0, 0, 1) 100%)">
                                <h4>${a.category} for ${a.type}</h4>
                                <aside>
                                    <i class="fa fa-map-marker mr-2">
                                    <i style="font-style: normal; font-weight: normal">${a.city}, ${a.address}</i>
                                    </i>
                                </aside>
                            </figure>
                            <div class="ts-description-lists">
                                <dl>
                                    <dt>Area</dt>
                                    <dd
                                    >${a.area} m²</dd>
                                </dl>
                                <dl>
                                    <dt>Price per sq.m</dt>
                                    <dd>
                                     ${(a.pricePerSqM*1.1926).toFixed(2)} $/m²</dd>                                 
                                </dl>
                            </div>
                        </div>
                        <div class="card-footer"><span class="ts-btn-arrow">Details</span></div>
                    </a>
                </div>
            </div>`
        })
        .join('');
}

const poundOffers = (offers) => {
    offerList.innerHTML = offers
        .map((a) => {
            return `<div class="col-sm-6 col-lg-4">
                    <a href="/comments/search/${a.id}" class="card ts-item ts-card ts-item__lg">
                        <img class="card-img-top  ts-item__image ts-item-tpp"
                             src="${a.imgUrl}" data-holder-rendered="true"
                             style="height: 225px; width: 100%; display: block;" alt="">
                        <div class="card-body">
                            <div value="${a.price}"
                                 class="ts-item__info-badge">
                                £ ${(a.price*0.8560).toFixed(2)}
                            </div>
                            <figure class="ts-item__info" style="background: linear-gradient(to bottom, rgba(0, 0, 0, 0) 0%, rgba(0, 0, 0, 1) 100%)">
                                <h4>${a.category} for ${a.type}</h4>
                                <aside>
                                    <i class="fa fa-map-marker mr-2">
                                    <i style="font-style: normal; font-weight: normal">${a.city}, ${a.address}</i>
                                    </i>
                                </aside>
                            </figure>
                            <div class="ts-description-lists">
                            <dl>
                                    <dt>Area</dt>
                                    <dd
                                    >${a.area} m²</dd>
                                </dl>
                                <dl>
                                    <dt>Price per sq.m</dt>
                                    <dd>
                                      ${(a.pricePerSqM*0.8560).toFixed(2)} £/m²</dd>                                 
                                </dl>
                            </div>
                        </div>
                        <div class="card-footer"><span class="ts-btn-arrow">Details</span></div>
                    </a>
                </div>
            </div>`
        })
        .join('');
}


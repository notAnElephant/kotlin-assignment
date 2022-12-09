<#-- @ftlvariable name="offer" type="com.example.models.Offer" -->
<#import "_layout.ftl" as layout />
<@layout.header>
    <h3>
        Mentett ajánlataim
    </h3>
    <hr>
    <div class="card-deck mx-5">
        <#list offers?reverse as offer>
            <div class="card mb-3" style="max-width: 540px;">
                <div class="row no-gutters">
                    <div class="col-md-4">
                        <img src="/static/images/${offer.imageName}" class="card-img" alt="image of the offered place">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title">${offer.name}</h5>
                            <p class="card-text">${offer.location}</p>
                            <i class="card-text">
                                ${offer.price} Ft
                            </i>
                            <form action="/saved_offers/${offer.id}" method="post">
                                <button type="submit" class="btn btn-primary" name="_action" value="delete">Törlés
                                </button>
                            </form>
                            <form action="/offers/${offer.id}" method="get">
                                <button type="submit" class="btn btn-primary">Részletek</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</@layout.header>
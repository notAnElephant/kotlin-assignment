<#-- @ftlvariable name="offer" type="com.example.models.Offer" -->
<#import "_layout.ftl" as layout />
<@layout.header>
    <div class="card-deck mx-5">
        <div class="card mb-3">
            <img src="/static/images/${offer.imageName}" class="card-img-left" alt="image of the offered place">
            <div class="card-body">
                <h5 class="card-title">${offer.name}</h5>
                <p class="card-text">${offer.location}</p>
                <i></i>
                <p class="card-text"><small class="text-muted">${offer.price} Ft</small>
                <p class="card-text">Leírás:</p>
                <p class="card-text">${offer.description}</p>
                <div class="ratings">
                    <#list 0..<offer.stars as i>
                        <i class="fa fa-star rating-color"></i>
                    </#list>
                    <#list 5..<offer.stars as i>
                        <i class="fa fa-star"></i>
                    </#list>
                </div>
                <form action="/saved_offers/${offer.id}" method="post">
                    <button class="btn btn-primary" type="submit" name="_action" value="save">
                        Foglalás
                    </button>
                </form>
            </div>
        </div>
    </div>
</@layout.header>
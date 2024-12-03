const product = [
    {
        id: 0,
        image: 'https://i.pinimg.com/564x/6e/5e/2e/6e5e2e058bd7022a05c2049e5c205e01.jpg',
        title: 'Mi primer diseño',
        price: 120,
    },
    {
        id: 1,
        image: 'https://i.pinimg.com/736x/90/03/f6/9003f6e13cbb5c5f499eecf830f574c2.jpg',
        title: 'Prueba',
        price: 60,
    },
    {
        id: 2,
        image: 'https://i.pinimg.com/564x/54/d4/31/54d431fd85169575c999867fc0763e92.jpg',
        title: 'Este si me lo compro',
        price: 230,
    },
    {
        id: 3,
        image: 'https://i.pinimg.com/564x/5d/e2/b4/5de2b48a962edd4f5799dc7ef75d3cd0.jpg',
        title: 'Edicion',
        price: 100,
    }
];
const categories = [...new Set(product.map((item)=>
    {return item}))];

document.getElementById('root').innerHTML = categories.map((item, index)=>
{
    var {image, title, price} = item;
    return(
        `<div class='box'>
            <div class='img-box'>
                <img class='images' src=${image} alt="${title}"></img>
            </div>
        <div class='bottom'>
        <p>${title}</p>
        <h2>$ ${price}.00</h2>`+
        
        `<button class='fabricate-btn' onclick='fabricateItem(${index})'>Fabricar</button>`+
        `</div>
        </div>`
    )
}).join('')

var cart =[];

function editItem(index) {
    console.log("Edit item:", index);
    // Lógica para editar el ítem
}

function fabricateItem(index) {
    addtocart(index);
}

function addtocart(a){
    cart.push({...categories[a]});
    displaycart();
}

function delElement(a){
    cart.splice(a, 1);
    displaycart();
}

function displaycart(){
    let j = 0, total=0;
    document.getElementById("count").innerHTML=cart.length;
    if(cart.length==0){
        document.getElementById('cartItem').innerHTML = "Aun no hay nada en tu carrito";
        document.getElementById("total").innerHTML = "$ "+0+".00";
    }
    else{
        document.getElementById("cartItem").innerHTML = cart.map((items)=>
        {
            var {image, title, price} = items;
            total=total+price;
            document.getElementById("total").innerHTML = "$ "+total+".00";
            return(
                `<div class='cart-item'>
                <div class='row-img'>
                    <img class='rowimg' src=${image}>
                </div>
                <p style='font-size:12px;'>${title}</p>
                <h2 style='font-size: 15px;'>$ ${price}.00</h2>`+
                "<i class='fa-solid fa-trash' onclick='delElement("+ (j++) +")'></i></div>"
            );
        }).join('');
    }
}
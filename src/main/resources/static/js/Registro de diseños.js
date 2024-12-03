document.addEventListener('DOMContentLoaded', () => {
    const pedidoForm = document.getElementById('pedidoForm');
    const pedidosTableBody = document.querySelector('#pedidosTable tbody');

    pedidoForm.addEventListener('submit', (e) => {
        e.preventDefault();
        
        const pedidoId = document.getElementById('pedidoId').value;
        const prenda = document.getElementById('prenda').value;
        const estado = document.getElementById('estado').value;
        const proveedor = document.getElementById('proveedor').value;
        const imagenInput = document.getElementById('imagenPrenda');
        
        if (imagenInput.files.length > 0) {
            const fileReader = new FileReader();
            fileReader.onload = function(e) {
                const imagenURL = e.target.result;

                const nuevoPedido = {
                    id: pedidoId,
                    prenda: prenda,
                    estado: estado,
                    proveedor: proveedor,
                    imagen: imagenURL
                };
                
                agregarPedidoATabla(nuevoPedido);
                limpiarFormulario();
            }
            fileReader.readAsDataURL(imagenInput.files[0]);
        } else {
            alert("Por favor, selecciona una imagen de la prenda.");
        }
    });

    function agregarPedidoATabla(pedido) {
        const row = document.createElement('tr');
        
        row.innerHTML = `
            <td>${pedido.id}</td>
            <td>${pedido.prenda}</td>
            <td>${pedido.estado}</td>
            <td>${pedido.proveedor}</td>
            <td><img src="${pedido.imagen}" alt="Imagen de la Prenda" style="width: 50px; height: auto;"></td>
            <td>
                <button onclick="editarPedido(this)">Editar</button>
                <button onclick="eliminarPedido(this)">Eliminar</button>
            </td>
        `;
        
        pedidosTableBody.appendChild(row);
    }

    function limpiarFormulario() {
        document.getElementById('pedidoId').value = '';
        document.getElementById('prenda').value = '';
        document.getElementById('estado').value = 'Pendiente';
        document.getElementById('proveedor').value = 'Proveedor 1';
        document.getElementById('imagenPrenda').value = '';
    }
});

function editarPedido(button) {
    const row = button.parentNode.parentNode;
    const pedidoId = row.children[0].innerText;
    const prenda = row.children[1].innerText;
    const estado = row.children[2].innerText;
    const proveedor = row.children[3].innerText;
    const imagen = row.children[4].children[0].src;

    document.getElementById('pedidoId').value = pedidoId;
    document.getElementById('prenda').value = prenda;
    document.getElementById('estado').value = estado;
    document.getElementById('proveedor').value = proveedor;

    row.remove();
}

function eliminarPedido(button) {
    const row = button.parentNode.parentNode;
    row.remove();
}


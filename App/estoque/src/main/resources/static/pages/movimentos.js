document.getElementById('tipo').addEventListener('change', function() {

    var tipo = this.value;

    if (tipo === 'Saida') {
        document.getElementById('notaFiscal').setAttribute('disabled', 'disabled');
        document.getElementById('fornecedor').setAttribute('disabled', 'disabled');
    } else if (tipo === 'Entrada') {
        document.getElementById('notaFiscal').removeAttribute('disabled');
        document.getElementById('fornecedor').removeAttribute('disabled');
    }

});

function excluir(idMovimento) {
    Swal.fire({
        title: "Deseja realmente deletar a movimentação?",
        text: "Essa ação não poderá ser desfeita!",
        icon: "warning",
        showCancelButton: true,
        cancelButtonText: "Cancelar",
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Sim, deletar!"
    }).then((result) => {
        if (result.isConfirmed) {
            fetch('/movimentos/' + idMovimento + '/excluir', { method: 'DELETE' })
                .then(response => response.text())
                .then(retorno => {
                    if (retorno == 'OK') {
                        Swal.fire({
                            title: "Sucesso!",
                            text: "Registro excluído com sucesso!",
                            icon: "success"
                        }).then((result) => {
                            window.location.reload();
                        });                        
                    } else {                        
                        Swal.fire({
                            title: "Atenção!",
                            text: "Não foi possível deletar o registro.",
                            icon: "warning"
                        });
                    }
                });
        }
    });
}
window.addEventListener("load", function() {

    $("#datatable").DataTable({
        language: {url: 'https://cdn.datatables.net/plug-ins/1.11.5/i18n/pt-BR.json'},
        processing: true,        
        sAjaxSource: '/fornecedores/jsonDataTable',
        bServerSide: true,
        drawCallback: function( settings ) {		    	
        },
        columns: [
            {data: 'id',},
            {data: 'nome'},
            {data: 'telefone'},
            {data: 'email'},
            {data: 'contato'},
            {data: 'situacao'},
            {data: 'id',
                render: function(data, type, row, meta) {
                       return `

                       <a href="/fornecedores/${data}/editar" class="btn btn-primary btn-sm">
                            <i class="mdi mdi-pencil me-1"></i> Editar
                        </a>
                        
                        <a href="javascript:excluir(${data});" class="btn btn-danger btn-sm">
                            <i class="mdi mdi-delete me-1"></i> Excluir
                        </a>
                       
                       `;
                }        
            },
            
        ],
    });
    $(".dataTables_length select").addClass("form-select form-select-sm");

});

// Função para excluir um fornecedor
function excluir(idFornecedor){

    Swal.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
      }).then((result) => {
        if (result.isConfirmed) {

            fetch("/fornecedores/" + idFornecedor + "/excluir", { method: 'DELETE' })
            .then(response => response.text())
            .then(retorno => {
                if (retorno == 'OK') {
                    Swal.fire({
                        title: "Sucesso!",
                        text: "Seu fornecedor foi deletado com sucesso!",
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
package br.com.digithink.estoque.domain.movimento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import br.com.digithink.estoque.domain.material.Material;
import br.com.digithink.estoque.domain.material.MaterialService;

@Service
public class MovimentoService {

    @Autowired
    private MovimentoRepository movimentoRepository;

    @Autowired
    private MaterialService materialService;
    
    public void salvarMovimento(Movimento movimento) {

        movimentoRepository.save(movimento); // Salva o movimento
        
        Material material = materialService.buscarPorId(movimento.getMaterial().getId()).get();
        
        if (movimento.getTipo().equalsIgnoreCase("entrada")) {
            material.setSaldo(material.getSaldo() + movimento.getQuantidade());
            if (material.getSaldo() > material.getEstoqueMaximo()) {
                System.out.println("Disparo de e-mail: O estoque do material " + material.getNome() + " excedeu o máximo permitido.");                 
            }
            material.setFornecedor(movimento.getFornecedor());

        } else if (movimento.getTipo().equalsIgnoreCase("saida")) {
            material.setSaldo(material.getSaldo() - movimento.getQuantidade());
            if (material.getSaldo() < material.getEstoqueMinimo()) {                
                System.out.println("Disparo de e-mail: O estoque do material " + material.getNome() + " está abaixo do mínimo permitido.");
            }
        }        
        materialService.salvar(material); // Salva as alterações no material

    }   

    public List<Movimento> buscarTodos() {
        return movimentoRepository.findAll();
    }

    public Movimento buscarPorId(Long id) {
        return movimentoRepository.findById(id).orElse(null);
    }

    public void excluir(Long idMovimento) {

        Movimento movimento = buscarPorId(idMovimento);

            Material material = movimento.getMaterial();
            
            if(movimento.getTipo().equals("Entrada")) {
                material.setSaldo(material.getSaldo() - movimento.getQuantidade());
            } else {
                material.setSaldo(material.getSaldo() + movimento.getQuantidade());
            }

            materialService.salvar(material);

        movimentoRepository.deleteById(idMovimento);
    }

    public void validaEntradaMaterial(Movimento movimento, BindingResult result) {
        if (movimento.getFornecedor() == null) {
            result.rejectValue("fornecedor", "", "Fornecedor é obrigatório");
        }
    
        if (movimento.getNotaFiscal() == null || movimento.getNotaFiscal().isEmpty()) {
            result.rejectValue("notaFiscal", "", "Informe o número da nota fiscal");
        }
    }
}
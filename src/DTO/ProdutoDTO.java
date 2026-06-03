package DTO;
import java.math.BigDecimal;

public class ProdutoDTO {
    private int codigo;
    private String descricao;
    private int estoque;
    private String status;
    private BigDecimal preco;
    private int codigoCategoria;

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public int getCodigoCategoria() { return codigoCategoria; }
    public void setCodigoCategoria(int codigoCategoria) { this.codigoCategoria = codigoCategoria; }
}

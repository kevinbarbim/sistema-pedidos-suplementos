package DTO;
import java.math.BigDecimal;

public class ItemPedidoDTO {
    private int nrPedido;
    private int codigoProduto;
    private BigDecimal precoUnitario;
    private int qtdVendida;

    public int getNrPedido() { return nrPedido; }
    public void setNrPedido(int nrPedido) { this.nrPedido = nrPedido; }

    public int getCodigoProduto() { return codigoProduto; }
    public void setCodigoProduto(int codigoProduto) { this.codigoProduto = codigoProduto; }

    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }

    public int getQtdVendida() { return qtdVendida; }
    public void setQtdVendida(int qtdVendida) { this.qtdVendida = qtdVendida; }
}

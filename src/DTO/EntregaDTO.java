package DTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EntregaDTO {
    private int nr;
    private BigDecimal valorEntrega;
    private LocalDateTime dataSaida;
    private String status;
    private int nrPedido;
    private int codigoEntregador;

    public int getNr() { return nr; }
    public void setNr(int nr) { this.nr = nr; }

    public BigDecimal getValorEntrega() { return valorEntrega; }
    public void setValorEntrega(BigDecimal valorEntrega) { this.valorEntrega = valorEntrega; }

    public LocalDateTime getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDateTime dataSaida) { this.dataSaida = dataSaida; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getNrPedido() { return nrPedido; }
    public void setNrPedido(int nrPedido) { this.nrPedido = nrPedido; }

    public int getCodigoEntregador() { return codigoEntregador; }
    public void setCodigoEntregador(int codigoEntregador) { this.codigoEntregador = codigoEntregador; }
}

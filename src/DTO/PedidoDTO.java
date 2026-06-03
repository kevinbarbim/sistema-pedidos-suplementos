package DTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PedidoDTO {
    private int nr;
    private LocalDateTime dataHora;
    private String status;
    private BigDecimal total;
    private int codigoCliente;
    private int codigoFuncionario;

    public int getNr() { return nr; }
    public void setNr(int nr) { this.nr = nr; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public int getCodigoCliente() { return codigoCliente; }
    public void setCodigoCliente(int codigoCliente) { this.codigoCliente = codigoCliente; }

    public int getCodigoFuncionario() { return codigoFuncionario; }
    public void setCodigoFuncionario(int codigoFuncionario) { this.codigoFuncionario = codigoFuncionario; }
}

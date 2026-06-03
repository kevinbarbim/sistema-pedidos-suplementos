package DTO;
import java.math.BigDecimal;

public class FuncionarioDTO extends PessoaDTO {
    private BigDecimal salario;

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
}

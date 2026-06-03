package DTO;

public class EnderecoDTO {
    private int id;
    private String cep;
    private String cidade;
    private String estado;
    private String status;
    private int codigoCliente;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getCodigoCliente() { return codigoCliente; }
    public void setCodigoCliente(int codigoCliente) { this.codigoCliente = codigoCliente; }
}

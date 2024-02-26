package br.com.sanittas.app.mail;

public class EmailEmpresa {
    private String email;
    private String token;

    public EmailEmpresa(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}

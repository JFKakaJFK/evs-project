package at.qe.sepm.skeleton.model;

public class Mail {
    private String email;
    private String subject;
    private String content;

    public Mail(){}

    public Mail(String email, String subject, String content)
    {
        this.email = email;
        this.subject = subject;
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

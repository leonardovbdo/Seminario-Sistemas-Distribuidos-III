public class Processo {

    private int id;
    private String status;

    public Processo(int id) {
        this.id = id;
        this.status = "ativo";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

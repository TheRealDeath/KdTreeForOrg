package DataSciences;

public class Resource {
    String name;
    int value;
    public Resource(String name, int value) {
        this.name = name;
        this.value = value;
    }
    @Override
    public String toString() {
        return name + " " + value;
    }
}

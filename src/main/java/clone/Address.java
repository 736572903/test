package clone;

public class Address implements Cloneable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address() {
    }

    public Address(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Address [name=" + name + "]";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}

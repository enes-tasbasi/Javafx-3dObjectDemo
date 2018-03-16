package playground;

public class Animal {
    boolean walk;
    int age;
    String name;

    public Animal(boolean walk, int age, String name) {
        this.walk = walk;
        this.age = age;
        this.name = name;
    }

    public boolean isWalk() {
        return walk;
    }

    public void setWalk(boolean walk) {
        this.walk = walk;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

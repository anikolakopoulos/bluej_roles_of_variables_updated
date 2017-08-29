package java_program;

public class School {
    public static void main(String[] args) {

        ClassRoom classroom1 = new ClassRoom("A100");
        ClassRoom classroom2 = new ClassRoom("A101");
        ClassRoom classroom3 = new ClassRoom("B05");

        classroom1.addRadio(Input.readInputLine());
        classroom1.addTelev(Input.readInputLine());
        classroom3.addRadio(Input.readInputLine());

        classroom1.removeRadio();

        System.out.println(Device.getCount() + " devices");
    }
}

class ClassRoom {
    String id; //%%id%%fixed value%%
    Device radio, telev;

    ClassRoom (String code) { //%%code%%fixed value%%
        id = code;
    }

    void addRadio(String desc) { //%%desc%%fixed value%%
        radio = new Device(desc);
    }

    void addTelev(String desc) { telev = new Device(desc); }

    void removeRadio () {radio.remove(); radio = null; }

    void removeTelev () {telev.remove(); telev = null; }

}

class Device {
    static int count = 0; //%%count%%stepper%%
    String description; //%%description%%fixed value%%

    Device (String desc) {
        description = desc; count++;
    }

    void remove () {
        System.out.println("Removing " + description);
        count--;
    }

    static int getCount () { return count; }

}

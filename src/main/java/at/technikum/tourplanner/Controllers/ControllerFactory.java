package at.technikum.tourplanner.Controllers;

public class ControllerFactory {
    private static ControllerFactory instance = new ControllerFactory();

    public static ControllerFactory getinstance() {
        return instance;
    }

    public ControllerFactory() {
    }

    public Object create(Class<?> controllerClass) {
        try {
            return controllerClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

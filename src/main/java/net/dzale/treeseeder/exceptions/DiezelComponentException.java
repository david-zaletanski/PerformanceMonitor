package net.dzale.treeseeder.exceptions;

/**
 * @author dzale
 */
public class DiezelComponentException extends DiezelException {

    public static final String COMPONENT_ERR_TXT = "A fatal exception has occurred in the %s component.";

    private ComponentType componentType;

    public DiezelComponentException(ComponentType componentType, Throwable ex) {
        super(ex);
        this.componentType = componentType;
    }

    public DiezelComponentException(ComponentType componentType, String message, Throwable ex) {
        super(message, ex);
        this.componentType = componentType;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }
}

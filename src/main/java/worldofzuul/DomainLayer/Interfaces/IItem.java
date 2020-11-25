package worldofzuul.DomainLayer.Interfaces;

/**
 * Simple interface describing what a gui needs to know about an item in world of zuul
 */
public interface IItem {
    String getName();

    /**
     * Gets a relatively pretty string describing an item
     * @return A description of the item (Name, price etc. )
     */
    String getDescription();
}
